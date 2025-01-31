
// Updated Deadline.java
package taskmanager.task;

import taskmanager.parser.DateParser;
import java.time.LocalDate;

/**
 * Represents a task with a specific deadline date.
 * A deadline task extends a basic task by adding a due date
 * that must be completed by.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Creates a new deadline task from a description and date string.
     *
     * @param description The description of the deadline task.
     * @param by The deadline date in yyyy-MM-dd format.
     * @throws IllegalArgumentException If the date string is not in the correct format
     *                                  or represents an invalid date.
     */
    public Deadline(String description, String by) throws IllegalArgumentException {
        super(description);
        this.by = DateParser.parseDate(by);
    }

    /**
     * Creates a new deadline task from a description and LocalDate.
     *
     * @param description The description of the deadline task.
     * @param by The deadline date.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateParser.formatForDisplay(by) + ")";
    }

    /**
     * Returns the deadline date for this task.
     *
     * @return The date by which this task must be completed.
     */
    public LocalDate getDate() {
        return by;
    }

    /**
     * Returns the deadline date formatted for storage.
     * Uses the yyyy-MM-dd format.
     *
     * @return The deadline date formatted as a string.
     */
    public String getStorageDate() {
        return DateParser.formatForStorage(by);
    }
}
