
// Updated Deadline.java
package taskmanager.task;

import taskmanager.parser.DateParser;
import java.time.LocalDate;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String description, String by) throws IllegalArgumentException {
        super(description);
        this.by = DateParser.parseDate(by);
    }

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateParser.formatForDisplay(by) + ")";
    }

    public LocalDate getDate() {
        return by;
    }

    // For storage
    public String getStorageDate() {
        return DateParser.formatForStorage(by);
    }
}
