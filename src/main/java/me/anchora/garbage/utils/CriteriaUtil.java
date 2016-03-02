package me.anchora.garbage.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

public class CriteriaUtil {
    private static final Logger logger = Logger.getLogger(CriteriaUtil.class);
    public static Object createCriteria(Object obj, Class<?> clazz) {
        Object criteria = null;
        String method;
        Object objResult;
        String fieldFirstUpcase;
        try {
            criteria = clazz.newInstance();
            BeanUtils.copyProperties(criteria, obj);
            Object internalCriteria = criteria.getClass().getMethod("createCriteria").invoke(criteria);
            for(Field field : obj.getClass().getDeclaredFields()) {
                fieldFirstUpcase = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                method = "get" + fieldFirstUpcase;
                if (field.getName() != null && "serialVersionUID".equals(field.getName())) {
                    continue;
                }
                
                objResult = obj.getClass().getMethod(method).invoke(obj);
                if (objResult == null) {
                    continue;
                }
                for(Method m : internalCriteria.getClass().getMethods()) {
                    if(objResult instanceof String && !"".equals(objResult) && ("and" + fieldFirstUpcase + "LikeInsensitive").equals(m.getName())) {
                        m.invoke(internalCriteria, objResult);
                        break;
                    } else if (!(objResult instanceof String) &&("and" + fieldFirstUpcase + "EqualTo").equals(m.getName())) {
                        m.invoke(internalCriteria, objResult);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.exception(logger, e);
        }
        
        return criteria;
    }
}
