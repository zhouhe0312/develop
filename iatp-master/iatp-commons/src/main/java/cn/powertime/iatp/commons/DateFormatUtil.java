package cn.powertime.iatp.commons;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Date的parse()与format(), 采用Apache Common Lang中线程安全, 性能更佳的FastDateFormat
 *
 * 注意Common Lang版本，3.5版才使用StringBuilder，3.4及以前使用StringBuffer.
 *
 * 1. 常用格式的FastDateFormat定义, 常用格式直接使用这些FastDateFormat
 *
 * 2. 日期格式不固定时的String<->Date 转换函数.
 *
 * 3. 打印时间间隔，如"01:10:10"，以及用户友好的版本，比如"刚刚"，"10分钟前"
 *
 * @see FastDateFormat#parse(String)
 * @see FastDateFormat#format(Date)
 * @see FastDateFormat#format(long)
 */
public class DateFormatUtil {

    /**
     * 没有分隔符的年月日
     */
    public static final String YYYYMMdd = "YYYYMMdd";
    /**
     * 没有分隔符的年月日时分秒毫秒
     */
    public static final String YYYYMMddHHmmssSSS = "YYYYMMddHHmmssSSS";
    public static final String YYYYMMddHHmmss = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    // 以T分隔日期和时间，并带时区信息，符合ISO8601规范
    public static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static final String PATTERN_ISO_ON_SECOND = "yyyy-MM-dd'T'HH:mm:ssZZ";
    public static final String PATTERN_ISO_ON_DATE = "yyyy-MM-dd";
    public static final String YYYYMMddTHHmmssZ_ISO = "yyyyMMdd'T'HHmmss'Z'";

    // 以空格分隔日期和时间，不带时区信息
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DEFAULT_ON_SECOND = "yyyy-MM-dd HH:mm:ss";

    // 使用工厂方法FastDateFormat.getInstance(), 从缓存中获取实例

    // 以T分隔日期和时间，并带时区信息，符合ISO8601规范
    public static final FastDateFormat ISO_FORMAT = FastDateFormat.getInstance(PATTERN_ISO);
    public static final FastDateFormat ISO_ON_SECOND_FORMAT = FastDateFormat.getInstance(PATTERN_ISO_ON_SECOND);
    public static final FastDateFormat ISO_ON_DATE_FORMAT = FastDateFormat.getInstance(PATTERN_ISO_ON_DATE);

    /**
     * 本地格式化日期到世界时间
     * 格式yyyyMMdd'T'HHmmss'Z'
     */
    public static final DateTimeFormatter LOCAL_TO_ISO_FORMAT = DateTimeFormatter.ofPattern(YYYYMMddTHHmmssZ_ISO);

    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_FORMAT = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
    /**
     * 世界时间到本地格式化日期
     * 格式yyyyMMdd'T'HHmmss'Z'
     */
    public static final DateTimeFormatter ISO_TO_LOCAL_FORMAT = DateTimeFormatter.ofPattern(YYYYMMddTHHmmssZ_ISO).withZone(ZoneOffset.UTC);

    // 以空格分隔日期和时间，不带时区信息
    public static final FastDateFormat DEFAULT_FORMAT = FastDateFormat.getInstance(PATTERN_DEFAULT);
    public static final FastDateFormat DEFAULT_ON_SECOND_FORMAT = FastDateFormat.getInstance(PATTERN_DEFAULT_ON_SECOND);

    /**
     * 分析日期字符串, 仅用于pattern不固定的情况.
     *
     * 否则直接使用DateFormats中封装好的FastDateFormat.
     *
     * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
     */
    public static Date parseDate(String pattern, String dateString) throws ParseException {
        return FastDateFormat.getInstance(pattern).parse(dateString);
    }

    /**
     * 格式化日期, 仅用于pattern不固定的情况.
     *
     * 否则直接使用本类中封装好的FastDateFormat.
     *
     * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
     */
    public static String formatDate(String pattern, Date date) {
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * 格式化日期, 仅用于不固定pattern不固定的情况.
     *
     * 否否则直接使用本类中封装好的FastDateFormat.
     *
     * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
     */
    public static String formatDate(String pattern, long date) {
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /////// 格式化间隔时间/////////
    /**
     * 按HH:mm:ss.SSS格式，格式化时间间隔.
     *
     * endDate必须大于startDate，间隔可大于1天，
     *
     * @see DurationFormatUtils
     */
    public static String formatDuration(Date startDate, Date endDate) {
        return DurationFormatUtils.formatDurationHMS(endDate.getTime() - startDate.getTime());
    }

    /**
     * 按HH:mm:ss.SSS格式，格式化时间间隔
     *
     * 单位为毫秒，必须大于0，可大于1天
     *
     * @see DurationFormatUtils
     */
    public static String formatDuration(long durationMillis) {
        return DurationFormatUtils.formatDurationHMS(durationMillis);
    }

    /**
     * 按HH:mm:ss格式，格式化时间间隔
     *
     * endDate必须大于startDate，间隔可大于1天
     *
     * @see DurationFormatUtils
     */
    public static String formatDurationOnSecond(Date startDate, Date endDate) {
        return DurationFormatUtils.formatDuration(endDate.getTime() - startDate.getTime(), "HH:mm:ss");
    }

    /**
     * 按HH:mm:ss格式，格式化时间间隔
     *
     * 单位为毫秒，必须大于0，可大于1天
     *
     * @see DurationFormatUtils
     */
    public static String formatDurationOnSecond(long durationMillis) {
        return DurationFormatUtils.formatDuration(durationMillis, "HH:mm:ss");
    }

    public static String getSeqString() {
        return formatDate(YYYYMMddHHmmss,new Date());
    }

    /**
     * 格式化零时区时间格式是yyyyMMdd'T'HHmmss'Z' 时间是今天
     * @return 返回格式yyyyMMdd'T'HHmmss'Z'
     */
    public static String todayFormatISO(){
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        return now.format(LOCAL_TO_ISO_FORMAT);
    }
    /**
     * 格式化零时区时间格式是yyyyMMdd'T'HHmmss'Z' 时间是昨天
     * @return 返回格式yyyyMMdd'T'HHmmss'Z'
     */
    public static String yesterdayFormatISO() {
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        return now.minusDays(1).format(LOCAL_TO_ISO_FORMAT);
    }

    /**
     * 把yyyyMMdd'T'HHmmss'Z'各式的UTC日期时间格式化成LocalDateTime
     * @param utcDateStr 日期字符串
     * @return 返回LocalDateTime
     */
    public static LocalDateTime utcToLocal(String utcDateStr) {
        ZonedDateTime dateTime = ZonedDateTime.parse(utcDateStr,ISO_TO_LOCAL_FORMAT);
        return LocalDateTime.ofInstant(dateTime.toInstant(),ZoneId.systemDefault());
    }
}
