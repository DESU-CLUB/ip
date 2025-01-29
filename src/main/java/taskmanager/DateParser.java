// DateParser.java
package taskmanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    
    public static LocalDate parseDate(String dateStr) throws IllegalArgumentException {
        try {
            return LocalDate.parse(dateStr, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Date must be in format: yyyy-MM-dd (e.g., 2024-12-31 for Dec 31 2024)");
        }
    }
    
    public static String formatForDisplay(LocalDate date) {
        return date.format(OUTPUT_FORMATTER);
    }
    
    public static String formatForStorage(LocalDate date) {
        return date.format(INPUT_FORMATTER);
    }
    
    public static boolean isValidDateRange(LocalDate startDate, LocalDate endDate) {
        return !endDate.isBefore(startDate);
    }
}
