package demo.base;

import java.time.Instant;
import java.time.temporal.ChronoField;

/**
 * InstantDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/10
 * @Description: TODO
 */
public class InstantDemo {


    /**
     * 创建Instant
     */
    public static void createInstant() {

        // 通过工厂方法now创建
        Instant instant = Instant.now();

        // 通过工厂方法ofEpochSecond创建
        Long timestamp = instant.getEpochSecond();
        Instant instant1 = Instant.ofEpochSecond(timestamp);

        // 增强版本，可以传递一个纳秒，
        Instant instant2 = Instant.ofEpochSecond(1000);
        Instant instant3 = Instant.ofEpochSecond(1000, 0L);
        Instant instant4 = Instant.ofEpochSecond(999, 1_000_000_000L);
        Instant instant5 = Instant.ofEpochSecond(998, 2_000_000_000L);
        Instant instant6 = Instant.ofEpochSecond(1001, -1_000_000_000L);
    }

    /**
     * 一个错误的示范
     */
    public static void getFieldError() {

        // 会扔出运行时异常
        int day = Instant.now().get(ChronoField.DAY_OF_MONTH);
    }

    public static void main(String[] args) {
        getFieldError();
    }
}
