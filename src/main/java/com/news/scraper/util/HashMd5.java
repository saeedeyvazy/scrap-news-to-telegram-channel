package com.news.scraper.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashMd5 {
    public static String hash(String text) {

        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(text.getBytes(), 0, text.length());
        return new BigInteger(1, m.digest()).toString(16);
    }
}
