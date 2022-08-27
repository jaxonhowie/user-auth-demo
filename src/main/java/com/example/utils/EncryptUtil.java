package com.example.utils;

/**
 * desc:
 * author: Hongyi Zheng
 * date: 2022/8/25
 */
public class EncryptUtil {

    private static final String PRIVATE_KEY = "A&Sample&Sequence&Witch&" +
            "Could=Be=Used=As=A=Private=Key";

    /**
     * byte ^ 一次异或加密  两次恢复原始密码
     *
     * @param origin 原始字符串
     * @return 返回加密后的字符串
     */
    public static String encrypt(String origin) {
        byte[] bytes = origin.getBytes();
        char salt = PRIVATE_KEY.charAt(5);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ (int) salt);
        }
        return new String(bytes, 0, bytes.length);
    }
}
