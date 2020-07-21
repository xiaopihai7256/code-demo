package demo.operate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Objects;

import static java.time.temporal.TemporalAdjusters.*;

/**
 * TemporalAdjusterDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/11
 * @Description: TODO
 */
public class TemporalAdjusterDemo {

    public static void adjustDate() {

        // 假设当前是：2018-04-02
        LocalDate date = LocalDate.of(2018, 4, 2);

        // 当前日期的下一个周日
        LocalDate date2 = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date2); // 2018-04-08

        // 五月的第二个周四
        LocalDate date3 = date.plusMonths(1).withDayOfMonth(1) // 先修改日期至5月1日
                .with(nextOrSame(DayOfWeek.THURSDAY)) //如果5月1日为周四，则不往后，所以这里用nextOrSame
                .with(next(DayOfWeek.THURSDAY)); // 第二次，这个日期肯定是周四，所以强制往后，使用next
        System.out.println(date3); // 2018-05-10

        // 当月的最后一天的日期
        LocalDate date4 = date.with(lastDayOfMonth());
        System.out.println(date4); // 2018-04-30

        // 明年的第一天是周几
        DayOfWeek date5 = date.with(firstDayOfNextYear()).getDayOfWeek();
        System.out.println(date5.getValue()); // 2 周二
    }

    /**
     * 下一个工作的实现的demo
     */
    public static void nextWorkingDayMethod() {

        // 下一个工作日的实现
        TemporalAdjuster nextWorkingDay = (Temporal temporal) -> {
            Objects.requireNonNull(temporal);
            int dayOfWeek = temporal.get(ChronoField.DAY_OF_WEEK);
            if (dayOfWeek >= 1 && dayOfWeek <= 4) {
                return temporal.plus(1L, ChronoUnit.DAYS);
            }
            return temporal.with(next(DayOfWeek.MONDAY));
        };

        // 测试
        LocalDate date = LocalDate.of(2018, 4, 1);
        System.out.println(date.with(nextWorkingDay)); // 2018-04-02
        LocalDate date2 = date.plusMonths(3).plusDays(2);
        System.out.println(date2.with(nextWorkingDay)); // 2018-07-04
    }


    public static void main(String[] args) {
        adjustDate();

        nextWorkingDayMethod();
    }
}
