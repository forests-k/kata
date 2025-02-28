package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.discount.Discount;
import kata.ex01.model.discount.Holiday;
import kata.ex01.model.discount.Midnight;
import kata.ex01.model.discount.Weekday;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {

    @Override
    public long calc(HighwayDrive drive) {
        return Arrays.asList(new Holiday(drive), new Weekday(drive), new Midnight(drive))
                .stream()
                .filter(Discount::discounted)
                .map(Discount::discountRate)
                .min(Comparator.naturalOrder())
                .orElse(0L);
    }
}
