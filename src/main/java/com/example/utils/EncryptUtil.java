package com.example.utils;

/**
 * desc:
 * author: Hongyi Zheng
 * date: 2022/8/25
 */
public class EncryptUtil {

    private static final String PRIVATE_KEY = "A&Sample&Sequence&Witch&" +
            "Could=Be=Used=As=A=Private=Key";

    public static String encrypt(String origin) {
        byte[] bytes = origin.getBytes();
        char salt = PRIVATE_KEY.charAt(5);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ (int) salt);
        }
        return new String(bytes, 0, bytes.length);
    }
}
