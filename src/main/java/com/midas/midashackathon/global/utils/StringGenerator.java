package com.midas.midashackathon.global.utils;

public class StringGenerator {
    public static String generateUpper(int length) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            builder.append('A' + Math.random() * 26);
        }

        return builder.toString();
    }
}
