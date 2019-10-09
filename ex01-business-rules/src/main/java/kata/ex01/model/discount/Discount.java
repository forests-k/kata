package kata.ex01.model.discount;

import kata.ex01.model.HighwayDrive;

/**
 * @author mori
 */
public abstract class Discount {

    protected HighwayDrive drive;

    private boolean discounted;

    private Long discountRate;

    protected Discount(HighwayDrive drive) {
        this.drive = drive;
        this.discounted = this.isDiscount();
        this.discountRate = this.getDiscountRate();
    }

    protected abstract long getDiscountRate();

    protected abstract boolean isDiscount();

    public boolean discounted() {
        return this.discounted;
    }

    public long discountRate() {
        return this.discountRate;
    }
}
