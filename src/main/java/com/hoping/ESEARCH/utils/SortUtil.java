package com.hoping.ESEARCH.utils;

import java.util.*;

/**
 * Description:
 *
 * @author YKL on 2018/6/12.
 * @version 1.0
 *          spark:梦想开始的地方
 */
public class SortUtil {

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    /**
     * 使用 Map按value进行排序,key含有汉字
     *
     * @param oriMap
     * @return
     */
    public static Map<String, String> sortMapByValueContainsHanZi(Map<String, String> oriMap, int num) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(oriMap.entrySet());
        entryList.sort(new MapValueComparator());

        Iterator<Map.Entry<String, String>> iter = entryList.iterator();
        Map.Entry<String, String> tmpEntry = null;
        int i = 0;
        while (iter.hasNext() && i < num) {
            tmpEntry = iter.next();
            if (isHanZi(tmpEntry.getKey().charAt(0))) {
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
                i++;
            }
        }
        return sortedMap;
    }

    /**
     * 使用 Map按value进行排序,key不含有汉字
     * @param oriMap
     * @param num
     * @return
     */
    public static Map<String, String> sortMapByValue(Map<String, String> oriMap, int num) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(oriMap.entrySet());
        entryList.sort(new MapValueComparator());

        Iterator<Map.Entry<String, String>> iter = entryList.iterator();
        Map.Entry<String, String> tmpEntry = null;
        int i = 0;
        while (iter.hasNext() && i < num) {
            tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
                i++;
        }
        return sortedMap;
    }

    static class MapValueComparator implements Comparator<Map.Entry<String, String>> {

        @Override
        public int compare(Map.Entry<String, String> me1, Map.Entry<String, String> me2) {
            /************************* 降序 **************************/
            return Double.valueOf(me2.getValue()).compareTo(Double.valueOf(me1.getValue()));
        }
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            /************************* 升序 **************************/
            return str1.compareTo(str2);
        }
    }

    public static boolean isHanZi(char ch) {
        // 判断是否汉字
        return (ch >= 0x4E00 && ch <= 0x9FA5);
    }

}

