// DateParser.java
package taskmanager.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing and formatting dates in the task management system.
 * Handles conversion between string representations and LocalDate objects.
 */
public class DateParser {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    /**
     * Parses a date string into a LocalDate object.
     * Expects dates in the format yyyy-MM-dd.
     *
     * @param dateStr The date string to parse.
     * @return The parsed LocalDate.
     * @throws IllegalArgumentException If the date string is not in the correct format
     *         or represents an invalid date.
     */
    public static LocalDate parseDate(String dateStr) throws IllegalArgumentException {
        try {
            return LocalDate.parse(dateStr, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Date must be in format: yyyy-MM-dd (e.g., 2024-12-31 for Dec 31 2024)");
        }
    }

    /**
     * Formats a LocalDate for display to users.
     * Uses the format "MMM d yyyy" (e.g., "Dec 31 2024").
     *
     * @param date The date to format.
     * @return A formatted string representation of the date.
     */
    public static String formatForDisplay(LocalDate date) {
        return date.format(OUTPUT_FORMATTER);
    }
    /**
     * Formats a LocalDate for storage.
     * Uses the format "yyyy-MM-dd" for consistent storage format.
     *
     * @param date The date to format.
     * @return A formatted string representation of the date.
     */
    public static String formatForStorage(LocalDate date) {
        return date.format(INPUT_FORMATTER);
    }
    /**
     * Checks if a date range is valid (end date is not before start date).
     *
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return true if the range is valid, false otherwise.
     */
    public static boolean isValidDateRange(LocalDate startDate, LocalDate endDate) {
        return !endDate.isBefore(startDate);
    }
}
