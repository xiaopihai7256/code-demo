package demo.base;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * LocalDateTimeDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/10
 * @Description: TODO
 */
public class LocalDateTimeDemo {

    /**
     * String 转换为 日期和时间对象
     * 如果String不合法导致转换失败，会扔出RunTime异常
     *
     * 输出：
     * 2018-12-12 12:12:12
     */
    public static void parseString() {

        LocalDate date = LocalDate.parse("2018-12-12");
        LocalTime time = LocalTime.parse("12:12:12");

        System.out.println(date + " " + time);// 2018-12-12 12:12:12
    }

    /**
     * 创建LocalDateTime 对象
     *
     * 指定时间为：2018-5-11T12:12:12
     */
    public static void createLocalDateTime() {

        // 通过of 方法直接创建
        LocalDateTime dateTime = LocalDateTime.of(2018, 5, 11, 12, 12, 12);

        // 通过LocalDate和Time 合并实现
        LocalDate date = LocalDate.of(2018, 5, 11);
        LocalTime time = LocalTime.of(12, 12, 12);
        LocalDateTime dateTime2 = LocalDateTime.of(date, time);

        // 通过LocalDate的 atTime 创建
        LocalDateTime dateTime3 = date.atTime(time);
        LocalDateTime dateTime4 = date.atTime(12, 12, 12);

        // 通过LocalTime的 atDate 创建
        LocalDateTime dateTime5 = time.atDate(date);

    }

    /**
     * LocalDateTime 是LocalDate 和LocalTime的组合对象
     * 因此也可以从LocalDateTime中获取LocalDate和LocalTime
     */
    public static void getDateAndTime() {

        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime time = now.toLocalTime();
    }

    public static void main(String[] args) {
        parseString();
        createLocalDateTime();
        getDateAndTime();
    }
}
