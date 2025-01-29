package taskmanager;

import java.time.LocalDate;

public class Event extends Task {
    private final LocalDate startTime;
    private final LocalDate endTime;

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

    public LocalDate getStartDate() {
        return startTime;
    }

    public LocalDate getEndDate() {
        return endTime;
    }

    public String getStorageStartDate() {
        return DateParser.formatForStorage(startTime);
    }

    public String getStorageEndDate() {
        return DateParser.formatForStorage(endTime);
    }
}
