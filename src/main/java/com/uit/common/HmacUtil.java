package com.uit.common;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
public class HmacUtil {

    public static String generateSignature(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key =
                    new SecretKeySpec(secret.getBytes(), "HmacSHA256");

            mac.init(key);
            byte[] rawHmac = mac.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(rawHmac);

        } catch (Exception e) {
            throw new RuntimeException("Error while generating HMAC");
        }
    }

    public static boolean verify(String data, String secret, String signature) {
        String newSign = generateSignature(data, secret);
        return newSign.equals(signature);
    }
}
