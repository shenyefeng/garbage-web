package me.anchora.garbage.utils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertySetStrategy;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public class JsonUtil {
    private static final Logger logger = Logger.getLogger(JsonUtil.class);
    private static JsonConfig jsonConfig = new JsonConfig();
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

    static {
        jsonConfig(jsonConfig);
        dateConfig(DEFAULT_DATE_PATTERN);
    }

    public static String toJson(Object obj) {
        String jsonString = null;

        if (obj != null) {
            if (obj instanceof Collection || obj instanceof Object[]) {
                jsonString = JSONArray.fromObject(obj, jsonConfig).toString();
            } else {
                jsonString = JSONObject.fromObject(obj, jsonConfig).toString();
            }
        }
        return jsonString;
    }

    public static Object toObject(String jsonStr, Class<?> clazz) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject(jsonStr, jsonConfig);
        } catch (Exception e) {
            logger.warn(e);
        }
        jsonConfig.setRootClass(clazz);
        return JSONObject.toBean(jsonObject, jsonConfig);
    }

    public static Object toObject(String jsonStr, Class<?> clazz, Map<String, Class<?>> classMap) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject(jsonStr, jsonConfig);
        } catch (Exception e) {
            logger.warn(e);
        }
        return JSONObject.toBean(jsonObject, clazz, classMap);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map getMapFromJson(String jsonString) {
        if(jsonString == null || jsonString.length() == 0) {
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map map = new HashMap();
        for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
            String key = (String) iter.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    public static List<?> toList(String jsonStr, Class<?> clazz) {
        List<Object> list = new ArrayList<Object>();
        try {
            JSONArray array = JSONArray.fromObject(jsonStr);
            jsonConfig.setRootClass(clazz);
            for (Object obj : array.toArray()) {
                list.add(JSONObject.toBean((JSONObject) obj, jsonConfig));
            }
        } catch (Exception e) {
            logger.warn(e);
        }
        return list;
    }

    public static void setDateFormat(String datePattern) {
        try {
            dateFormat = new SimpleDateFormat(datePattern);
        } catch (Exception e) {
            dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        }
        dateConfig(datePattern);
        jsonConfig(jsonConfig);
    }

    public static void setTimeZone(TimeZone tz) {
        dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        dateFormat.setTimeZone(tz);
        dateConfig(DEFAULT_DATE_PATTERN);
        jsonConfig(jsonConfig);
    }

    private static void dateConfig(String pattern) {
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { pattern }), true);
    }

    private static void jsonConfig(JsonConfig jsonConfig) {
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                return process(value);
            }

            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                return process(value);
            }

            private Object process(Object value) {
                Object obj = null;
                if (value != null) {
                    obj = dateFormat.format((Date) value);
                }
                return obj;
            }
        });

        jsonConfig.registerJsonValueProcessor(String.class, new JsonValueProcessor() {
            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                return process(value);
            }

            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                return process(value);
            }

            private Object process(Object value) {
                Object obj = null;
                if (value != null) {
                    obj = ((String) value).trim();
                }
                return obj;
            }
        });

        jsonConfig.registerDefaultValueProcessor(Date.class, new DefaultValueProcessor() {
            @SuppressWarnings("rawtypes")
            public Object getDefaultValue(Class type) {
                return null;
            }
        });

        jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
            @SuppressWarnings("rawtypes")
            public Object getDefaultValue(Class type) {
                return null;
            }
        });

        jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
            @SuppressWarnings("rawtypes")
            public Object getDefaultValue(Class type) {
                return null;
            }
        });

        jsonConfig.registerDefaultValueProcessor(Long.class, new DefaultValueProcessor() {
            @SuppressWarnings("rawtypes")
            public Object getDefaultValue(Class type) {
                return null;
            }
        });

        jsonConfig.registerDefaultValueProcessor(Float.class, new DefaultValueProcessor() {
            @SuppressWarnings("rawtypes")
            public Object getDefaultValue(Class type) {
                return null;
            }
        });

        jsonConfig.registerDefaultValueProcessor(Double.class, new DefaultValueProcessor() {
            @SuppressWarnings("rawtypes")
            public Object getDefaultValue(Class type) {
                return null;
            }
        });

        jsonConfig.registerDefaultValueProcessor(Boolean.class, new DefaultValueProcessor() {
            @SuppressWarnings("rawtypes")
            public Object getDefaultValue(Class type) {
                return null;
            }
        });
        jsonConfig.setPropertySetStrategy(new PropertySetStrategy() {

            @Override
            public void setProperty(Object bean, String key, Object value) throws JSONException {
                setProperty(bean, key, value, new JsonConfig());
            }

            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public void setProperty(Object bean, String key, Object value, JsonConfig jsonConfig) throws JSONException {
                if (bean instanceof Map) {
                    ((Map) bean).put(key, value);
                } else {
                    if (!jsonConfig.isIgnorePublicFields()) {
                        try {
                            Field field = bean.getClass().getField(key);
                            if (field != null)
                                field.set(bean, value);
                        } catch (Exception e) {
                            _setProperty(bean, key, value);
                        }
                    } else {
                        _setProperty(bean, key, value);
                    }
                }
            }

            private void _setProperty(Object bean, String key, Object value) {
                try {
                    if(key != null && key.length() > 0) {
                        key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    }
                    if(value instanceof JSONObject) {
                        JsonConfig jsonConfig = new JsonConfig();
                        jsonConfig(jsonConfig);
                        dateConfig(DEFAULT_DATE_PATTERN);
                        jsonConfig.setRootClass(PropertyUtils.getPropertyType(bean, key));
                        value = JSONObject.toBean((JSONObject)value, jsonConfig);
                    }
                    PropertyUtils.setSimpleProperty(bean, key, value);
                } catch (Exception e) {
                    // ignore
                }
            }
        });
    }
}
