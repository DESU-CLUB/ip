// EventCommand.java (enhanced with date parsing)
package taskmanager.command;


import java.time.LocalDate;

import taskmanager.parser.DateParser;
import taskmanager.task.Event;
import taskmanager.task.Task;
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.EmptyDescriptionException;
import taskmanager.utils.InvalidFormatException;

/**
 * Creates a new ListCommand with no additional details needed.
 */
public class EventCommand extends Command {
    private static final String FROM_DELIMITER = " /from ";
    private static final String TO_DELIMITER = " /to ";
    /**
     * Creates a new EventCommand with the given event details.
     *
     * @param details The event description and date range in the format:
     *                "description /from start-date /to end-date"
     */
    public EventCommand(String details) {
        super(details);
    }

    /**
     * Creates a new event task and adds it to the task list.
     * @throws EmptyDescriptionException If the event description is empty.
     * @throws InvalidFormatException If the command format is invalid or dates are invalid.
     */
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
