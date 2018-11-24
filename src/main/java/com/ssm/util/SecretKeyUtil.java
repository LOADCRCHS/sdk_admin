package com.ssm.util;

import java.util.Random;

public class SecretKeyUtil {
    public static String getSecretKey() {
        String src = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(src.charAt(random.nextInt(61)));
        }
        return sb.toString();
    }
}
