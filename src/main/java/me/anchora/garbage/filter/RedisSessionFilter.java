package me.anchora.garbage.filter;

import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.ser.SerializableUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisSessionFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(RedisSessionFilter.class);

	private static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> currentResponse = new ThreadLocal<HttpServletResponse>();
	private static ThreadLocal<RedisSessionFilter> currentFilter = new ThreadLocal<RedisSessionFilter>();

	private boolean cacheFlag = false;
	private JedisPool jedisPool;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			String flag = filterConfig.getInitParameter("cacheFlag");
			logger.debug("RedisSessionFilter param: cacheFlag=" + flag);
			cacheFlag = Boolean.valueOf(flag);
			if (cacheFlag) {
				GenericObjectPoolConfig config = new GenericObjectPoolConfig();
				String uri = filterConfig.getInitParameter("uri");
				logger.debug("RedisSessionFilter param: uri=" + uri);
				jedisPool = new JedisPool(config, new URI(uri));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest && cacheFlag) {
			currentFilter.set(this);
			currentRequest.set((HttpServletRequest) request);
			currentResponse.set((HttpServletResponse) response);
			handle((HttpServletRequest) request, (HttpServletResponse) response, chain);
			currentFilter.set(null);
			currentRequest.set(null);
			currentResponse.set(null);
		} else {
			chain.doFilter(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private void handle(final HttpServletRequest request, final HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		Jedis jedis = jedisPool.getResource();
		String sessionId = request.getSession().getId();
		Map<String, Object> map;
		if(jedis.exists(sessionId.getBytes())) {
			try {
				map = (HashMap<String, Object>)SerializableUtils.fromByteArray(jedis.get(sessionId.getBytes()));
				for(String key : map.keySet()) {
					request.getSession().setAttribute(key, map.get(key));
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else {
			map = new HashMap<String, Object>();
			Enumeration<?> e = request.getSession().getAttributeNames();
			String key;
			while(e.hasMoreElements()) {
				key = (String)e.nextElement();
				if(request.getSession().getAttribute(key) != null) {
					map.put(key, request.getSession().getAttribute(key));
				}
			}
			if(map.size() > 0) {
				jedis.set(sessionId.getBytes(), SerializableUtils.toByteArray(map));
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		jedisPool.destroy();
	}
}