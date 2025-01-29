package taskmanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final LocalDate startTime;
    private final LocalDate endTime;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Event(String description, String start, String end) throws IllegalArgumentException {
        super(description);
        try {
            this.startTime = LocalDate.parse(start, INPUT_FORMATTER);
            this.endTime = LocalDate.parse(end, INPUT_FORMATTER);
            if (endTime.isBefore(startTime)) {
                throw new IllegalArgumentException("End time cannot be before start time");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Dates must be in format: yyyy-MM-dd (e.g., 2024-12-31 for Dec 31 2024)");
        }
    }

    public Event(String description, LocalDate start, LocalDate end) {
        super(description);
        this.startTime = start;
        this.endTime = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime.format(OUTPUT_FORMATTER) + 
               " to: " + endTime.format(OUTPUT_FORMATTER) + ")";
    }

    public LocalDate getStartDate() {
        return startTime;
    }

    public LocalDate getEndDate() {
        return endTime;
    }

    // For storage
    public String getStorageStartDate() {
        return startTime.format(INPUT_FORMATTER);
    }

    public String getStorageEndDate() {
        return endTime.format(INPUT_FORMATTER);
    }
}
