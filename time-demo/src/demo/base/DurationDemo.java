package demo.base;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * DurationDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/10
 * @Description: TODO
 */
public class DurationDemo {

    public static void createDuration() {

        LocalDateTime from = LocalDateTime.of(2018, 4, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.now();
        Duration duration = Duration.between(from, to);
        System.out.println(duration); // PT953H28M16.279S 代表：953小时28分钟16.279秒
    }

    public static void createDuration2() {

        Duration threeMinutes = Duration.ofMinutes(3); // 3分钟
        Duration fiveMinutes = Duration.of(5, ChronoUnit.MINUTES); // 5分钟
    }

    public static void main(String[] args) {
        createDuration();
    }
}
