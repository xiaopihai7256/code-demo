package demo.base;

import java.time.LocalDate;
import java.time.Period;

/**
 * PeriodDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/10
 * @Description: TODO
 */
public class PeriodDemo {

    public static void createPeriod() {

        LocalDate from = LocalDate.of(2018, 4, 1);
        LocalDate to = LocalDate.of(2018, 5, 2);
        Period period = Period.between(from, to);
        System.out.println(period); // P1M1D  表示：1个月零2天
    }

    public static void createPeriod2() {

        Period threeDays = Period.ofDays(3); //三天
        Period twoWeeks = Period.ofWeeks(2); // 两周
        Period oneYear = Period.ofYears(1); // 一年
        Period fiveMonth = Period.ofMonths(5); //五个月
        Period towYearsOneMonthTenDays = Period.of(2, 1, 10); // 两年一个月零十天

    }

    public static void main(String[] args) {
        createPeriod();
    }
}
