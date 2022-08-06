package com.xxxx.seckilldemo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5UTil {
    private  static final String salt = "1a2b3c4d";

    public static String inputPassToFromPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass
                +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    public static String fromPassToDBPass(String fromPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + fromPass +salt.charAt(5)
                + salt.charAt(4);
        return md5(str);
    }
    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("8fd07a09645afd90cb59951d03fca4a0"));
    }
}
