package taskmanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final LocalDate by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Deadline(String description, String by) throws IllegalArgumentException {
        super(description);
        try {
            this.by = LocalDate.parse(by, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Date must be in format: yyyy-MM-dd (e.g., 2024-12-31  for Dec 31 2024)");
        }
    }

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMATTER) + ")";
    }

    public LocalDate getDate() {
        return by;
    }

    // For storage
    public String getStorageDate() {
        return by.format(INPUT_FORMATTER);
    }
}
