
package com.example;

import java.util.Calendar;

public class CalendarUtils {

    /**
     * 休日であるか判定する。
     * 
     * 対象日(targetDate)が休日の場合、trueを返す。
     * 
     * @param targetDate      対象日
     * @param containSaturday 休日に土曜を含むか
     * @return booleanを返す
     * @throws IllegalArgumentException 対象日(targetDate)がnullの場合
     * @see "Test: com.example.CalendarUtilsTest.IsHolidayTest"
     */
    public static boolean isHoliday(Calendar targetDate, boolean containSaturday) throws IllegalArgumentException {
        if (targetDate == null) {
            throw new IllegalArgumentException("TargetDate is null.");
        }

        int dayOfWeek = targetDate.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SUNDAY)
            return true;
        if (containSaturday && dayOfWeek == Calendar.SATURDAY)
            return true;

        return false;
    }

}