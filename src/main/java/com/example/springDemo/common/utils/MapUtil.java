package com.example.springDemo.common.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    /**
     * map字符串解析为map
     * @param str
     * @return
     */
    public static Map<String,Object> mapStringToMap(String str){
        str=str.substring(1, str.length()-1);

        String[] strs=str.split(", ");
        Map<String,Object> map = new HashMap<String, Object>();
        for (String string : strs) {
            String key=string.split("=")[0];
            String value=string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}
