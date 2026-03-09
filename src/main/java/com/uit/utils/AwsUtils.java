package com.uit.utils;


public class AwsUtils {

    public static String extractKey(String s3Url) {
        return s3Url.substring(s3Url.indexOf(".amazonaws.com/") + 15);
    }

}
