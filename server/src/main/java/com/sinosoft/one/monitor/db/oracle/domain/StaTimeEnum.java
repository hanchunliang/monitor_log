package com.sinosoft.one.monitor.db.oracle.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午8:54
 */
public enum StaTimeEnum {
    TODAY,
    LAST_24HOUR,
    YESTERDAY,
    THIS_WEEK,
    LAST_7DAY,
    LAST_WEEK,
    THIS_MONTH,
    LAST_30DAY,
    LSAT_MONTH,
    THIS_SEASON,
    THIS_YEAR,
    LAST_1YEAR;

    public static Date getTime(StaTimeEnum staTimeEnum, Date currTime) {
        Date returnTime = currTime;
        switch (staTimeEnum) {
            case TODAY: {
                Calendar calendar = Calendar.getInstance();
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.clear();
                System.out.println(newCalendar);
                calendar.setTime(currTime);
                newCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                returnTime = newCalendar.getTime();
                break;
            }
            case LAST_24HOUR: {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currTime);
                calendar.add(Calendar.DATE, -1);
                returnTime = calendar.getTime();
                break;
            }
            case YESTERDAY: {
                Calendar calendar = Calendar.getInstance();
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.clear();
                calendar.setTime(currTime);
                calendar.add(Calendar.DATE, -1);
                newCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                returnTime = newCalendar.getTime();
                break;
            }
            case THIS_WEEK: {
                Calendar calendar = Calendar.getInstance();
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.clear();
                calendar.setTime(currTime);
                int gone = Calendar.DAY_OF_WEEK;
                //计算一周的第几天
                gone = (gone == 1) ? 7 : ((gone + 6) % 7);
                System.out.println(gone);
                calendar.add(Calendar.DATE, -gone + 1);
                newCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                returnTime = newCalendar.getTime();
                break;
            }
            case LAST_7DAY: {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currTime);
                calendar.add(Calendar.DATE, -7);
                returnTime = calendar.getTime();
                break;
            }
            case LAST_WEEK: {
                break;
            }
            case THIS_MONTH: {
                break;
            }
            case LAST_30DAY: {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currTime);
                calendar.add(Calendar.DATE, -30);
                returnTime = calendar.getTime();
                break;
            }
            case LSAT_MONTH: {
                break;
            }
            case THIS_SEASON: {
                break;
            }
            case THIS_YEAR: {
                break;
            }
            case LAST_1YEAR: {
                break;
            }
        }
        return returnTime;
    }

    public static void main(String[] arr) {
        System.out.print(getTime(LAST_24HOUR, new Date()));
    }
}
