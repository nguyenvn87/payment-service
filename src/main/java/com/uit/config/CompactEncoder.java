package com.uit.config;

import com.uit.common.exceptions.PaymentError;
import com.uit.common.exceptions.PaymentException;

import java.nio.ByteBuffer;
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
        byte[] bytes = DECODER.decode(encoded);

        if (bytes.length != 24) {
            throw new PaymentException(PaymentError.DECODE_DATA_FAIL);
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        long most = buffer.getLong();
        long least = buffer.getLong();
        int extra = buffer.getInt();

        return new DecodedData(new UUID(most, least), extra);
    }

    /**
     * DTO chứa dữ liệu sau khi decode
     */
    public record DecodedData(UUID uuid, int extraData) {}
}
