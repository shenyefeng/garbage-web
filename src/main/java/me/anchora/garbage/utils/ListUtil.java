package me.anchora.garbage.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<?> getSeveralData(List<?> list, Long number) {
        List result = new ArrayList();
        if (list.size() < number) {
            return list;
        } else {
            int step = Math.round(list.size() / number);
            for (int i = list.size() - 1; i >= 0; i -= step) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<?> getSeveralData(String[] strs, Long number) {
        List result = new ArrayList();
        if (strs.length < number) {
            return Arrays.asList(strs);
        } else {
            int step = Math.round(strs.length / number);
            for (int i = strs.length - 1; i >= 0; i -= step) {
                result.add(strs[i]);
            }
        }
        return result;
    }
}
