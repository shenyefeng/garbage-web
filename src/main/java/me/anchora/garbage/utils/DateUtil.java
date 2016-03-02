package me.anchora.garbage.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateUtil {
    private static Logger logger = Logger.getLogger(DateUtil.class);
    protected final static TimeZone s_gmtTimeZone = TimeZone.getTimeZone("GMT");
    
    private static String pattern = "[dd/MM/yyyy:HH:mm:ss Z]";
    
    public static Date format(String str, String pattern) {
        
        try {
            return new SimpleDateFormat(pattern, Locale.US).parse(str);
        } catch (ParseException e) {
            LogUtil.exception(logger, e);
            return null;
        }
    }
    
    public static Date format(String str) {
        return format(str, pattern);
    }

    public static Date afterDays(Date date, Long days) {
        Long time = date.getTime();
        time += days * 24 * 60 * 60 * 1000;
        return new Date(time);
    }

    public static Date beforeDays(Date date, Long days) {
        Long time = date.getTime();
        time -= days * 24 * 60 * 60 * 1000;
        return new Date(time);
    }

    public static Date afterHours(Date date, Long hours) {
        Long time = date.getTime();
        time += hours * 60 * 60 * 1000;
        return new Date(time);
    }

    public static Date beforeHours(Date date, Long hours) {
        Long time = date.getTime();
        time -= hours * 60 * 60 * 1000;
        return new Date(time);
    }

    public static Date afterMinutes(Date date, Long minutes) {
        Long time = date.getTime();
        time += minutes * 60 * 1000;
        return new Date(time);
    }

    public static Date afterSeconds(Date date, Long seconds) {
        Long time = date.getTime();
        time += seconds * 1000;
        return new Date(time);
    }

    public static Date beforeMinutes(Date date, Long minutes) {
        Long time = date.getTime();
        time -= minutes * 60 * 1000;
        return new Date(time);
    }

    public static Date endOfDay(Date date) {
        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date) + " 23:59:59";
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date startOfDay(Date date) {
        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date) + " 00:00:00";
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date endOfHour(Date date) {
        String strDate = new SimpleDateFormat("yyyy-MM-dd HH:").format(date) + "59:59";
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date startOfHour(Date date) {
        String strDate = new SimpleDateFormat("yyyy-MM-dd HH:").format(date) + "00:00";
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Map<String, Integer> getTime(Double time) throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            double dday = time / (3600 * 24);
            int day = (int) (time / (3600 * 24));
            double dhour = dday - day;
            int hour = (int) (dhour * 24);
            dhour = dhour * 24;
            double dminute = dhour - hour;
            int minute = (int) (dminute * 60);
            dminute = dminute * 60;
            double dsecond = dminute - minute;
            int second = (int) (dsecond * 60);

            map.put("day", day);
            map.put("hour", hour);
            map.put("minute", minute);
            map.put("second", second);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return map;
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String formatDateHbase(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    public static Date toDate(String date, String pattern) {
        Date result = null;
        try {
            result = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            logger.warn(e);
        }
        return result;
    }

    public static Date toDate(String date) {
        return toDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toLocalDateStr(String dateStr, String tz) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        String result = null;
        try {
            date = sdf.parse(dateStr);
            sdf.setTimeZone(TimeZone.getTimeZone(tz));
            result = sdf.format(date);
        } catch (ParseException e) {
            logger.warn(e);
        }
        return result;
    }

	public static String getDateDisplayString(String tz, Date time) {
		return getDateDisplayString(tz, time, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getDateDisplayString(String tz, Date time, String formatString) {
		DateFormat df = new SimpleDateFormat(formatString);
		df.setTimeZone(TimeZone.getTimeZone(tz));
		
		return df.format(time);
	}
	public static Date latestMinute(Date date) {
		Date dateTmp = startOfHour(date);
		for(;dateTmp.before(date) || dateTmp.equals(date);) {
			dateTmp = afterMinutes(dateTmp, 3L);
		}
		return beforeMinutes(dateTmp, 3L);
	}
	public static Date latestHours(Date date) {
		Date dateTmp = startOfDay(date);
		for(;dateTmp.before(date) || dateTmp.equals(date);) {
			dateTmp = afterHours(dateTmp, 1L);
		}
		return beforeHours(dateTmp, 1L);
	}
}
