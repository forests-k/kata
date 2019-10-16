package kata.ex01.model.discount;

import kata.ex01.model.HighwayDrive;
import kata.ex01.util.DateTimeUtils;
import kata.ex01.util.HolidayUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author mori
 */
public class Weekday extends Discount {

    private static final List<CountPerMonth> COUNT_PER_MONTH_LIST =
            Arrays.asList(
                    new CountPerMonth(5, 9, 30),
                    new CountPerMonth(10, Integer.MAX_VALUE, 50)
            );

    public Weekday(HighwayDrive drive) {
        super(drive);
    }

    /**
     * 平日朝夕割引の割引率を取得します。
     *
     * @return 割引率
     */
    @Override
    public long getDiscountRate() {
        return getDiscountRateByCountPerMonth();
    }

    /**
     * 平日朝夕割引の適応可否取得します。
     *
     * @return 割引適用可否
     */
    @Override
    public boolean isDiscount() {
        return usedByWeekDay();
    }

    /**
     * 平日朝夕割引適用可否を検証します。
     *
     * @return 割引適用可否
     */
    private boolean usedByWeekDay() {
        return (usedTimeAM() || usedTimePM()) && containCountPerMonth();
    }

    /**
     * 平日朝夕割引のうち、平日朝の割引適用可否を検証します。
     *
     * <p>「走行記録は、24時間を超えないもの」を前提として実装</p>
     *
     * @return 平日朝時間帯割引適用可否
     */
    private boolean usedTimeAM() {
        boolean enteredAtIsEnable = !HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) &&
                isEnableAMTime(drive.getEnteredAt());
        boolean exitedAtIsEnable = !HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate()) &&
                isEnableAMTime(drive.getExitedAt());
        return enteredAtIsEnable || exitedAtIsEnable;
    }

    /**
     * 対象時間帯が朝(6:00-9:00)の時間帯かを検証します。
     *
     * @param date 対象時間
     * @return {@code true}の場合、対象時間帯が朝(6:00-9:00)
     */
    private boolean isEnableAMTime(LocalDateTime date) {
        return DateTimeUtils.isRange(
                LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 6, 0),
                LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 9, 0),
                date);
    }

    /**
     * 平日朝夕割引のうち、平日夕の割引適用可否を検証します。
     *
     * <p>「走行記録は、24時間を超えないもの」を前提として実装</p>
     *
     * @return 平日夕時間帯割引適用可否
     */
    private boolean usedTimePM() {
        boolean enteredAtIsEnable = HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) &&
                isEnablePMTime(drive.getEnteredAt());
        boolean exitedAtIsEnable = HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate()) &&
                isEnablePMTime(drive.getExitedAt());
        return enteredAtIsEnable || exitedAtIsEnable;
    }

    /**
     * 対象時間帯が夕(17:00-21:00)の時間帯かを検証します。
     *
     * @param date 対象時間
     * @return {@code true}の場合、対象時間帯が夕(17:00-21:00)
     */
    private boolean isEnablePMTime(LocalDateTime date) {

        return DateTimeUtils.isRange(
                LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 7, 0),
                LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 21, 0),
                date);
    }

    /**
     * 最低利用回数を満たしているか検証します。
     *
     * @return {@code true}の場合、最低利用回数を満たしている
     */
    private boolean containCountPerMonth() {
        return COUNT_PER_MONTH_LIST.stream()
                .anyMatch(weekDayCount ->
                        weekDayCount.getFromCount() <= drive.getDriver().getCountPerMonth() &&
                                weekDayCount.getToCount() >= drive.getDriver().getCountPerMonth());
    }

    /**
     * 利用回数に応じた割引率を取得します。
     *
     * @return 割引率
     */
    private long getDiscountRateByCountPerMonth() {
        return COUNT_PER_MONTH_LIST.stream()
                .filter(weekDayCount ->
                        weekDayCount.getFromCount() <= drive.getDriver().getCountPerMonth() &&
                                weekDayCount.getToCount() >= drive.getDriver().getCountPerMonth()
                )
                .map(CountPerMonth::getDiscountRate)
                .findFirst()
                .orElse(0L);
    }
}

class CountPerMonth {

    private int fromCount;

    private int toCount;

    private long discountRate;

    public CountPerMonth(int fromCount, int toCount, long discountRate) {
        this.fromCount = fromCount;
        this.toCount = toCount;
        this.discountRate = discountRate;
    }

    public int getFromCount() {
        return this.fromCount;
    }

    public int getToCount() {
        return this.toCount;
    }

    public long getDiscountRate() {
        return this.discountRate;
    }
}
