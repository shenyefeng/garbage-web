package me.anchora.garbage.aop;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.anchora.garbage.dao.OperationLogsMapper;
import me.anchora.garbage.entry.OperationLogs;
import me.anchora.garbage.entry.base.BaseEntry;
import me.anchora.garbage.utils.JsonUtil;
import me.anchora.garbage.utils.LogUtil;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceLogger {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private OperationLogsMapper operationLogsMapper;

    public void doAfter(JoinPoint jp) {
        logger.info(jp.getTarget().getClass().getSimpleName() + "." + jp.getSignature().getName() + " is ended.");
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        try {
            Object obj = pjp.proceed();
            
            if(pjp.getSignature().getName().contains("create") || pjp.getSignature().getName().contains("insert")
                    || pjp.getSignature().getName().contains("update") || pjp.getSignature().getName().contains("delete")
                    || pjp.getSignature().getName().contains("login") || pjp.getSignature().getName().contains("logout")) {
                OperationLogs operationLogs = new OperationLogs();
                setUserIdIpAndParams(operationLogs, pjp);
                operationLogs.setClassMethod(pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName());
                operationLogs.setCreatedAt(new Date());
                if(operationLogs.getUserId() == null) {
                    operationLogs.setUserId(-1L);
                }
                operationLogsMapper.insert(operationLogs);
            }
            
            return obj;
        } finally {
            time = System.currentTimeMillis() - time;
            logger.info(pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + " invocation time: " + time + " ms");
            
        }
    }

    private void setUserIdIpAndParams(OperationLogs operationLogs, JoinPoint pjp) {
        Long userId;
        String userIp;
        String param;
        for (Object obj : pjp.getArgs()) {
            if (obj instanceof BaseEntry) {
                userId = ((BaseEntry) obj).getAdminId();
                userIp = ((BaseEntry) obj).getIp();
                param = getParam(obj);
                if(userId == null) {
                    userId = -1L;
                }
                operationLogs.setUserId(userId);
                operationLogs.setIpAddress(userIp);
                operationLogs.setParams(param);
                break;
            }
        }
    }

    private String getParam(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        String fieldFirstUpcase;
        String method;
        Object objResult;
        for(Field field : obj.getClass().getDeclaredFields()) {
            if (field.getName() != null && "serialVersionUID".equals(field.getName())) {
                continue;
            }
            
            fieldFirstUpcase = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            method = "get" + fieldFirstUpcase;
            
            try {
                objResult = obj.getClass().getMethod(method).invoke(obj);
                if (objResult == null) {
                    continue;
                }
                map.put(field.getName(), objResult);
            } catch (Exception e) {
                LogUtil.exception(logger, e);
            }
            
        }
        return JsonUtil.toJson(map);
    }

    public void doBefore(JoinPoint jp) {
        logger.info(jp.getTarget().getClass().getSimpleName() + "." + jp.getSignature().getName() + " is start.");
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        logger.info(jp.getTarget().getClass().getSimpleName() + "." + jp.getSignature().getName() + " throw exception. " + ex.getMessage());
        OperationLogs operationLogs = new OperationLogs();
        setUserIdIpAndParams(operationLogs, jp);
        operationLogs.setClassMethod(jp.getTarget().getClass().getSimpleName() + "." + jp.getSignature().getName());
        operationLogs.setCreatedAt(new Date());
        if(operationLogs.getUserId() == null) {
            operationLogs.setUserId(-1L);
        }
        operationLogs.setExceptionMsg(ex.getMessage());
        operationLogsMapper.insert(operationLogs);
    }

}