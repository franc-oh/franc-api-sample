package com.franc.app.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    /**
     * 당일여부 체크 - LocalDateTime
     * @param pDateTime
     * @return
     * @throws Exception
     */
    public static boolean isEqualNowDate(LocalDateTime pDateTime) {
        if(pDateTime == null)
            throw new NullPointerException();

        return isEqualNowDate(pDateTime.toLocalDate());
    }

    /**
     * 당일여부 체크 - LocalDate
     * @param pDate
     * @return
     * @throws Exception
     */
    public static boolean isEqualNowDate(LocalDate pDate) {
        if(pDate == null)
            throw new NullPointerException();

        return LocalDate.now().isEqual(pDate);
    }

    /**
     * LocalDateTime 포맷터 (yyyyMMddHHmmss)
     * @param pDateTime
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime pDateTime) {
        if(pDateTime == null)
            pDateTime = LocalDateTime.now();

        return pDateTime.format((DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }

}
