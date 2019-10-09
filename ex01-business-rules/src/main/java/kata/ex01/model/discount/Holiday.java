package kata.ex01.model.discount;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;
import kata.ex01.util.HolidayUtils;

public class Holiday extends Discount {

    private static long DISCOUNT_RATE = 30;

    public Holiday(HighwayDrive drive) {
        super(drive);
    }

    /**
     * 休日割引の割引可否を取得します。
     *
     * @return {@code true}の場合、割引対象
     */
    @Override
    protected boolean isDiscount() {
        return useByHoliday() && enableVehicleFamily() && enableArea();
    }

    /**
     * 休日割引の割引率を取得します。
     *
     * @return 割引率
     */
    @Override
    protected long getDiscountRate() {
        return DISCOUNT_RATE;
    }

    /**
     * 休日割引適用対象かを検証します。
     *
     * @return {@code true}の場合、休日割引対象の日付利用
     */
    private boolean useByHoliday() {
        // 利用開始時間または利用終了時間が休日または祝日の場合、割引適用とする。
        boolean enableEnteredAt = HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate());
        boolean enableExitedAt = HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate());

        return (enableEnteredAt || enableExitedAt);
    }

    /**
     * 割引適用対象の車種の利用可否を検証します。
     *
     * @return {@code true}の場合休日割引対象の車種利用
     */
    private boolean enableVehicleFamily() {
        return VehicleFamily.STANDARD.equals(drive.getVehicleFamily());
    }

    /**
     * 割引適用対象のエリア利用可否かを検証します。
     *
     * @return {@code true}の場合休日割引対象のエリア利用
     */
    private boolean enableArea() {
        return RouteType.RURAL.equals(drive.getRouteType());
    }
}
