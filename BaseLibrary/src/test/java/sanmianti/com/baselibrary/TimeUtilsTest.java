package sanmianti.com.baselibrary;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

import sanmianti.com.baselibrary.utils.ZTimeUtils;

/**
 * @author sanmianti
 * @description {@link ZTimeUtils}单元测试
 * @date 2019/7/18 10:27
 */
public class TimeUtilsTest {

    @Test
    public void testGetMondayTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间:" + sdf.format(1563416820000L));
        long mondayTimeStamp = ZTimeUtils.getMondayTimeStamp(1563416820000L);
        System.out.println("周一时间:" + sdf.format(mondayTimeStamp));
        Assert.assertEquals("2019-07-15 00:00:00", sdf.format(mondayTimeStamp));
    }

    @Test
    public void testGetSundayTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间:" + sdf.format(1563416820000L));
        long mondayTimeStamp = ZTimeUtils.getSundayTimeStamp(1563416820000L);
        System.out.println("周日时间:" + sdf.format(mondayTimeStamp));
        Assert.assertEquals("2019-07-20 23:59:59", sdf.format(mondayTimeStamp));
    }

}
