package com.uit.common;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {

    public static long getCurrentTime(LocalDateTime now) {
        return now.atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                .toEpochSecond();
    }
}
