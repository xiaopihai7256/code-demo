package demo.operate;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * BaseOperateDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/11
 * @Description: TODO
 */
public class BaseOperateDemo {

    /**
     * 使用with可以对日期和时间对象的属性进行修改，产生修改后的副本<br>
     *     LocalTime 或者 LocalDateTime 的方法类似，这里不再举例
     */
    public static void withOperate() {

        System.out.println("========== with Operate ============");
        // 2018-04-01
        LocalDate date = LocalDate.of(2018, 4 , 1);

        // 使用 withAttribute 类型的方法可基于已有对象的属性修改创建得到新的日期对象，原对象不变
        LocalDate date2 = date.withDayOfMonth(12); // 2018-04-12
        System.out.println(date + " => " + date2);
        LocalDate date3 = date2.withYear(2019); // 2019-04-12
        System.out.println(date2 + " => " + date3);

        // 也可以使用通用的with方法来对指定的属性进行修改
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 10); // 2019-10-12
        System.out.println(date3 + " => " + date4);
    }

    /**
     * 声明式操作日期，比如加减
     */
    public static void stateOperate() {

        System.out.println("========== state Operate ============");
        // 2018-04-01
        LocalDate date = LocalDate.of(2018, 4, 1);

        LocalDate date2 = date.plusDays(10); // 2018-04-11
        System.out.println(date + " => " + date2);
        LocalDate date3 = date2.minusMonths(2).plusYears(1); // 2018-02-11 => 2019-02-11
        System.out.println(date2 + " => " + date3);

        // 19年2月为28天，所以四周后为 2019-03-11
        LocalDate date4 = date3.plus(4, ChronoUnit.WEEKS);
        System.out.println(date3 + " => " + date4);
    }


    public static void main(String[] args) {
        withOperate();
        stateOperate();
    }
}
