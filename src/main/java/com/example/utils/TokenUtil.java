package com.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.model.User;

import java.util.Calendar;
import java.util.Date;

/**
 * desc:
 * author: Hongyi Zheng
 * date: 2022/8/26
 */
public class TokenUtil {



    /**
     * 哈希签名
     */
    private static final String SECRETE = "A-SECRETE-TO-SIGN";

    public static String genToken(User user) {
        //有效时间2H
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.HOUR, 2);
        final String token = JWT.create()
                .withClaim("aud", user.getUsername())
                .withClaim("pwd", user.getPassword())
                .withExpiresAt(exp.getTime())
                .sign(Algorithm.HMAC256(SECRETE));
        System.out.printf("user = { %s, %s }, token = %s%n", user.getUsername(), user.getPassword(), token);
        return token;
    }

    public static User getTokenUser(String token) throws JWTVerificationException{
        DecodedJWT decode = JWT.decode(token);
        return new User(decode.getAudience().get(0), decode.getClaim("pwd").toString());
    }

    public static boolean isExpiry(String token) {
        DecodedJWT decode = JWT.decode(token);
        Date expiresAt = decode.getExpiresAt();
        Date currentTime = new Date();
        System.out.printf("Token will expire at : %s, current time :%s%n", expiresAt, currentTime);
        return currentTime.after(expiresAt);
    }

    public static String genTokenWithExpiry(User user, Date expiresAt) {
        //有效时间2H
        final String token = JWT.create()
                .withClaim("aud", user.getUsername())
                .withClaim("pwd", user.getPassword())
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(SECRETE));
        System.out.printf("user = { %s, %s }, token = %s%n", user.getUsername(), user.getPassword(), token);
        return token;
    }
}
