package taskmanager.task;

import taskmanager.parser.DateParser;
import java.time.LocalDate;

/**
 * Represents an event that occurs over a time period.
 * An event has both a start date and an end date, which define
 * its duration. The end date must not be before the start date.
 */
public class Event extends Task {
    private final LocalDate startTime;
    private final LocalDate endTime;

    /**
     * Creates a new event with the given description and date range.
     *
     * @param description The description of the event.
     * @param start The start date of the event.
     * @param end The end date of the event. Must not be before the start date.
     */
    public Event(String description, LocalDate start, LocalDate end) {
        super(description);
        this.startTime = start;
        this.endTime = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateParser.formatForDisplay(startTime) + 
               " to: " + DateParser.formatForDisplay(endTime) + ")";
    }

    /**
     * Returns the start date of this event.
     *
     * @return The event's start date.
     */   
    public LocalDate getStartDate() {
        return startTime;
    }

    /**
     * Returns the end date of this event.
     *
     * @return The event's end date.
     */
    public LocalDate getEndDate() {
        return endTime;
    }

    /**
     * Returns the start date formatted for storage.
     * Uses the yyyy-MM-dd format.
     *
     * @return The start date formatted as a string.
     */
    public String getStorageStartDate() {
        return DateParser.formatForStorage(startTime);
    }

    /**
     * Returns the end date formatted for storage.
     * Uses the yyyy-MM-dd format.
     *
     * @return The end date formatted as a string.
     */
    public String getStorageEndDate() {
        return DateParser.formatForStorage(endTime);
    }
}
