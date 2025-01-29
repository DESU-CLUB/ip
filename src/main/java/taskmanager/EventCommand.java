
// EventCommand.java (enhanced with date parsing)
package taskmanager;
import java.time.LocalDate;

public class EventCommand extends Command {
    private static final String FROM_DELIMITER = " /from ";
    private static final String TO_DELIMITER = " /to ";
    
    public EventCommand(String details) {
        super(details);
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new EmptyDescriptionException("event");
        }

        // Parse event description and dates
        String[] eventParts = details.split(FROM_DELIMITER, 2);
        if (eventParts.length != 2 || eventParts[0].trim().isEmpty()) {
            throw new InvalidFormatException("Please use format: event <name> /from <start> /to <end>");
        }

        String description = eventParts[0].trim();
        String[] timeParts = eventParts[1].split(TO_DELIMITER, 2);
        
        if (timeParts.length != 2) {
            throw new InvalidFormatException("Please use format: event <name> /from <start> /to <end>");
        }

        String startDateStr = timeParts[0].trim();
        String endDateStr = timeParts[1].trim();

        try {
            LocalDate startDate = DateParser.parseDate(startDateStr);
            LocalDate endDate = DateParser.parseDate(endDateStr);
            
            // Validate date range
            if (!DateParser.isValidDateRange(startDate, endDate)) {
                throw new InvalidFormatException("End time cannot be before start time");
            }

            Task event = new Event(description, startDate, endDate);
            tasks.addTask(event);
            ui.showTaskAdded(event, tasks.size());
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException(e.getMessage());
        }
    }

    @Override
    public boolean requiresSave() {
        return true;
    }
}
