package demo.operate;

import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * DateTimeFormatterDemo
 *
 * @author huifei.liu@hand-china.com
 * @date 2018/5/11
 * @Description: TODO
 */
public class DateTimeFormatterDemo {

    /**
     * 基于内置的formatter对象格式化和解析日期时间
     */
    public static void baseFormatter() {

        // 2018-04-01
        LocalDate date = LocalDate.of(2018, 4, 1);

        // 格式化输出
        String basicIsoDateStr = date.format(DateTimeFormatter.BASIC_ISO_DATE); // 20180401
        String isoLocalDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2018-04-01
        System.out.println("格式化输出：\n" + basicIsoDateStr + "\n" + isoLocalDate);

        // 解析
        LocalDate date2 = LocalDate.parse(basicIsoDateStr, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate date3 = LocalDate.parse(isoLocalDate, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("解析输出：\n" + date2 + "\n" + date3);
    }

    /**
     * 创建自定义的格式化字符串
     */
    public static void customFormatter() {

        System.out.println("======== 自定义格式化格式 ========");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDate date = LocalDate.of(2018, 4, 1);

        String dateStr = date.format(formatter);
        System.out.println(dateStr);

        LocalDate date2 = LocalDate.parse(dateStr, formatter);
        System.out.println(date2);
    }

    /**
     * 本地化 解析日期
     */
    public static void localFormatter() {

        System.out.println("============== 本地化解析日期 ===================");

        LocalDate date = LocalDate.of(2018, 4, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy年 MMMM d", Locale.CHINA);

        String dateStr = date.format(formatter);
        System.out.println(dateStr); // 18年 四月 1
        LocalDate date2 = LocalDate.parse(dateStr, formatter);
        System.out.println(date2); // 2018-04-01
    }

    public static void builderFormatter() {

        System.out.println("========= builder model ================");

        LocalDate date = LocalDate.of(2018, 04, 01);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.YEAR)
                .appendLiteral("年")
                .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL)
                .appendText(ChronoField.DAY_OF_MONTH, TextStyle.FULL_STANDALONE)
                .appendLiteral("日 ")
                .appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL)
                .parseCaseInsensitive()
                .toFormatter(Locale.CHINESE);

        String dateStr = date.format(formatter);
        System.out.println(dateStr); // 2018年四月1日 星期日
        LocalDate date2 = LocalDate.parse(dateStr, formatter);
        System.out.println(date2); // 2018-04-01
    }

    public static void main(String[] args) {
        baseFormatter();
        customFormatter();
        localFormatter();
        builderFormatter();
    }
}
