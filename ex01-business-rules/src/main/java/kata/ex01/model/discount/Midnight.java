package kata.ex01.model.discount;

import kata.ex01.model.HighwayDrive;
import kata.ex01.util.DateTimeUtils;

import java.time.LocalDateTime;

/**
 * @author mori
 */
public class Midnight extends Discount {

    private static long DISCOUNT_RATE = 30;

    public Midnight(HighwayDrive drive) {
        super(drive);
    }

    /**
     * 深夜割引の割引可否を取得します。
     *
     * @return {@code true}の場合、割引対象
     */
    @Override
    protected boolean isDiscount() {
        boolean enteredAtIsEnable = isEnableMidnight(drive.getEnteredAt());
        boolean exitedAtIsEnable = isEnableMidnight(drive.getExitedAt());
        return enteredAtIsEnable || exitedAtIsEnable;
    }

    /**
     * 深夜割引の割引率を取得します。
     *
     * @return 割引率
     */
    @Override
    protected long getDiscountRate() {
        return DISCOUNT_RATE;
    }


    /**
     * 深夜割引の適用可否を検証します。
     *
     * @param date 日付
     * @return 割引可否
     */
    private boolean isEnableMidnight(LocalDateTime date) {
        return DateTimeUtils.isRange(
                LocalDateTime.of(
                        date.getYear(),
                        date.getMonth(),
                        date.getDayOfMonth(),
                        0,
                        0),
                LocalDateTime.of(
                        date.getYear(),
                        date.getMonth(),
                        date.getDayOfMonth(),
                        4,
                        0),
                date);
    }
}
