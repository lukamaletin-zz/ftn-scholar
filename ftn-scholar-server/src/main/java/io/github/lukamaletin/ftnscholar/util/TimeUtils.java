package io.github.lukamaletin.ftnscholar.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static boolean isExpired(Date date, int timeoutMinutes) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, timeoutMinutes);

        return calendar.getTime().before(new Date());
    }
}
