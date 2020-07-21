package demo.base;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

/**
 * LocalDateDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/9
 * @Description: TODO
 */
public class LocalDateDemo {

    /**
     * 创建LocalDate对象的几种方式
     *
     * 1. of 方式
     * 2. now 方式
     *
     * 输出：
     * 2018-04-20
     * 2018-05-09
     */
    public static void createLocalDateDemo() {

        // 通过of方法创建LocalDate对象
        int year = 2018;
        Month month = Month.of(4);
        // month是内置的枚举，直接通过具体的值指定月份也可以
        Month month2 = Month.APRIL;
        int day = 20;
        LocalDate ofDate = LocalDate.of(year, month, day);
        System.out.println(ofDate.toString());


        // 通过静态方法now 创建，当前日期
        LocalDate nowDate = LocalDate.now();
        System.out.println(nowDate);
    }

    /**
     * 读取localDate的属性
     *
     * 输出：
     * year:2018  month:5  day-of-month:9
     */
    public static void getLocalDateField() {

        LocalDate now = LocalDate.now();

        int year = now.getYear();
        Month month = now.getMonth();
        int day = now.getDayOfMonth();

        System.out.print("year:" + year + "  ");
        System.out.print("month:" + month.getValue() + "  ");
        System.out.println("day-of-month:" + day);

        // 除了简单的年月日之外，LocalDate还记录了一些十分有用的和日期相关的信息
        boolean isLeapYear = now.isLeapYear(); // 是否是闰年
        int lengthOfMonth = now.lengthOfMonth(); // 当前月份有多少天
        DayOfWeek dow = now.getDayOfWeek(); // 当前是周几
    }

    /**
     * 通过Temporal接口获取LocalDate的属性
     *
     * 输出：
     * 通过Temporal访问LocalDate的属性：2018-5-9
     */
    public static void getFieldByTemporal() {

        LocalDate now = LocalDate.now();

        int year = now.get(ChronoField.YEAR);
        int month = now.get(ChronoField.MONTH_OF_YEAR);
        // 如果想把int类型的month转为枚举，可以使用of 方法
        Month month2 = Month.of(month);
        int day = now.get(ChronoField.DAY_OF_MONTH);

        System.out.println("通过Temporal访问LocalDate的属性：" + year + "-" +  month + "-" + day);
    }

    public static void main(String[] args) {

        createLocalDateDemo();

        getLocalDateField();

        getFieldByTemporal();

    }
}
