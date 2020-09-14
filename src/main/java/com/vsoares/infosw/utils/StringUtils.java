package com.vsoares.infosw.utils;

public class StringUtils {

    public static String normalizeName(String name){
        return name.replaceAll("[^a-zA-Z0-9\\s+]", "")
                .toUpperCase()
                .stripLeading()
                .stripTrailing();
    }
}
