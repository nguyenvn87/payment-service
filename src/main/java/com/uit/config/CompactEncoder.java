package com.uit.config;

import com.uit.common.constant.ServiceTypeEnums;
import com.uit.common.exceptions.PaymentError;
import com.uit.common.exceptions.PaymentException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class CompactEncoder {

    private static final Base64.Encoder ENCODER =
            Base64.getUrlEncoder().withoutPadding();

    private static final Base64.Decoder DECODER =
            Base64.getUrlDecoder();

    /**
     * Encode UUID + extra data thành string ngắn gọn
     */
    public static String encode(String uid, long extraData) {
        try {
            UUID uuid = UUID.fromString(uid);
            ByteBuffer buffer = ByteBuffer.allocate(24);

            buffer.putLong(uuid.getMostSignificantBits());
            buffer.putLong(uuid.getLeastSignificantBits());
            buffer.putLong(extraData);

            return ENCODER.encodeToString(buffer.array());
        }catch(Exception e) {
            throw new PaymentException(PaymentError.ENCODE_DATA_FAIL);
        }
    }

    /**
     * Decode string về UUID + extra data
     */
    public static DecodedData decode(String encoded) {
        try {
            byte[] bytes = DECODER.decode(encoded);
            if (bytes.length != 24) {
                throw new PaymentException(PaymentError.DECODE_DATA_FAIL);
            }
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            long most = buffer.getLong();
            long least = buffer.getLong();
            long extra = buffer.getLong();

            return new DecodedData(new UUID(most, least), extra);
        } catch (Exception e) {
            throw new PaymentException(PaymentError.DECODE_DATA_FAIL);
        }
    }

    /**
     * DTO chứa dữ liệu sau khi decode
     */
    public record DecodedData(UUID uuid, long extraData) {}

    public static String encodeExtend(String userId, String courseId, ServiceTypeEnums serviceType) {
        try {
            byte[] userBytes = userId.getBytes(StandardCharsets.UTF_8);
            byte[] courseBytes = courseId.getBytes(StandardCharsets.UTF_8);
            String enumName = serviceType.name();
            byte[] enumBytes = enumName.getBytes(StandardCharsets.UTF_8);

            ByteBuffer buffer = ByteBuffer.allocate(
                    4 + userBytes.length +
                            4 + courseBytes.length +
                            4 + enumBytes.length
            );

            // userId
            buffer.putInt(userBytes.length);
            buffer.put(userBytes);

            // courseId
            buffer.putInt(courseBytes.length);
            buffer.put(courseBytes);

            // enum
            buffer.putInt(enumBytes.length);
            buffer.put(enumBytes);

            return ENCODER.encodeToString(buffer.array());
        } catch (Exception e) {
            throw new PaymentException(PaymentError.ENCODE_DATA_FAIL);
        }
    }

    public static DecodeResult decodeExtend(String encoded) {
        try {
            byte[] bytes = DECODER.decode(encoded);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);

            // userId
            int userLength = buffer.getInt();
            byte[] userBytes = new byte[userLength];
            buffer.get(userBytes);
            String userId = new String(userBytes, StandardCharsets.UTF_8);

            // courseId
            int courseLength = buffer.getInt();
            byte[] courseBytes = new byte[courseLength];
            buffer.get(courseBytes);
            String courseId = new String(courseBytes, StandardCharsets.UTF_8);

            // enum (FIX CHỖ NÀY)
            int enumLength = buffer.getInt();
            byte[] enumBytes = new byte[enumLength];
            buffer.get(enumBytes);
            String name = new String(enumBytes, StandardCharsets.UTF_8);

            ServiceTypeEnums serviceType = ServiceTypeEnums.valueOf(name);

            return new DecodeResult(userId, courseId, serviceType);
        } catch (Exception e) {
            throw new PaymentException(PaymentError.DECODE_DATA_FAIL);
        }
    }


    public record DecodeResult (String userId, String courseId,ServiceTypeEnums serviceType) {}
}
