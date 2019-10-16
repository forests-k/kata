package kata.ex01.util;

import java.time.LocalDateTime;

/**
 * @author mori
 */
public class DateTimeUtils {

    public static boolean isRange(LocalDateTime from, LocalDateTime to, LocalDateTime dateTime) {
        return (dateTime.isAfter(from) || dateTime.isEqual(from)) &&
                (dateTime.isBefore(to) || dateTime.isEqual(to));
    }
}
