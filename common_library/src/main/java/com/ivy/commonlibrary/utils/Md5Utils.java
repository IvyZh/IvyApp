package com.ivy.commonlibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    /**
     * md5加密的算法
     *
     * @param text
     * @return 出错就返回" " 比如传进来的text==null的情况
     */
    public static String encode(String text) {
        try {
            if (text == null) {
                return " ";
            }
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff + 2018; // 加盐 +1 ;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // can't reach
            return " ";
        }
    }


}
