package me.anchora.garbage.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import me.anchora.garbage.utils.IpUtils;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.bind.annotation.RequestMapping;

public class ControllerLogger {

    private Logger logger = Logger.getLogger(getClass());
    private String path;
    private String userIp;

    public void doAfter(JoinPoint jp) {
        logger.info(jp.getTarget().getClass().getSimpleName() + "." + jp.getSignature().getName() + "(" + getControllerPath(jp) + ") is ended from " + getUserIp(jp));
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();

        try {
            return pjp.proceed();
        } finally {
            time = System.currentTimeMillis() - time;
            logger.info(pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + " invocation time: " + time + " ms. Called by " + getUserIp(pjp));
        }
    }

    public void doBefore(JoinPoint jp) {
        logger.info(jp.getTarget().getClass().getSimpleName() + "." + jp.getSignature().getName() + "(" + getControllerPath(jp) + ") is called by " + getUserIp(jp));
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        System.out.println("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
        System.out.println(ex.getMessage());
    }

    private String getUserIp(JoinPoint jp) {
        for (Object obj : jp.getArgs()) {
            if (obj instanceof HttpServletRequest) {
                userIp = IpUtils.getRemoteIpAddr((HttpServletRequest) obj);
                break;
            }
        }

        return userIp;
    }

    private String getControllerPath(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        for (Method method : jp.getTarget().getClass().getDeclaredMethods()) {
            if (methodName.equals(method.getName())) {
                for (Object obj : method.getAnnotations()) {
                    if (obj instanceof RequestMapping) {
                        path = ((RequestMapping) obj).value()[0];
                        break;
                    }
                }
                break;
            }
        }

        return path;
    }

}