package sanmianti.com.baselibrary.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author 三面体
 * @description 时间工具类
 * @date 2019/7/18 10:15
 */
public class TimeUtils {


    /**
     * 获取给定时间所处星期星期一的时间戳，精确至周一凌晨00:00:00
     *
     * @param timeStampInMilli 毫秒格式的时间戳
     *
     * @return 周一凌晨时间戳
     */
    public static long getMondayTimeStamp(long timeStampInMilli) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            calendar.add(Calendar.DATE, -6);
        } else {
            calendar.add(Calendar.DATE, -week + 2);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取给定时间所处星期星期日的时间戳，精确至周日23:59:59:999
     *
     * @param timeStampInMilli 毫秒格式的时间戳
     *
     * @return 周日晚23:59:59:999时间戳
     */
    public static long getSundayTimeStamp(long timeStampInMilli) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timeStampInMilli));
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        //周日与今日相差的时间为：7-week, 例如今天是周三(week = 4),则周日的日期为今日+3
        if (week != 1) {
            calendar.add(Calendar.DATE, 7 - week);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}
