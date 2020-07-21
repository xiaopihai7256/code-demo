package demo.base;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

/**
 * LocalTimeDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/10
 * @Description: TODO
 */
public class LocalTimeDemo {

    /**
     * 创建LocalTime
     *
     * 输出：
     * 10:39:44.951
     * 12:12:12.000100
     */
    public static void createLocalTime() {

        // 通过工厂方法now创建
        LocalTime now = LocalTime.now();
        System.out.println(now);

        // 通过制定参数of 方法创建
        LocalTime ofTime = LocalTime.of(12, 12, 12,100000);
        System.out.println(ofTime);
    }

    /**
     * 读取LocalTime的值
     *
     * 输出：
     * 10:50:54
     * 10:50:54
     */
    public static void getLocalTimeField() {

        LocalTime nowTime = LocalTime.now();

        int hour = nowTime.getHour();
        int minute = nowTime.getMinute();
        int second = nowTime.getSecond();
        System.out.println(hour + ":" + minute + ":" + second);

        // 同样，LocalTime 也可以通过Temporal来获取指定属性的值
        int tempOfHour = nowTime.get(ChronoField.HOUR_OF_DAY);
        int tempOfMinute = nowTime.get(ChronoField.MINUTE_OF_HOUR);
        int tempOfSecond = nowTime.get(ChronoField.SECOND_OF_MINUTE);
        System.out.println(tempOfHour + ":" + tempOfMinute + ":" + tempOfSecond);

    }


    public static void main(String[] args) {

        createLocalTime();

        System.out.println("------------------------------------");
        getLocalTimeField();
    }
}
