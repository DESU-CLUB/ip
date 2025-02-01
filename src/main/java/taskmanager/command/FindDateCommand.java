// FindDateCommand.java (updated to use DateParser)
package taskmanager.command;

import java.time.LocalDate;
import java.util.ArrayList;

import taskmanager.parser.DateParser;
import taskmanager.task.Task;
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.InvalidFormatException;

/**
 * Represents a command to find tasks scheduled on a specific date.
 * Searches through deadlines and events to find tasks that occur on
 * the target date. For events, matches if the date falls within
 * the event's duration.
 */
public class FindDateCommand extends Command {
    /**
     * Creates a new FindDateCommand with the given date string.
     * @param details The date to search for in yyyy-MM-dd format.
     */
    public FindDateCommand(String details) {
        super(details);
    }

    /**
     * Executes the find date command. Shows a list of all tasks scheduled
     * for the specified date. For events, shows those that are ongoing
     * on the target date.
     * @param tasks The task list to search through.
     * @param ui The ui to show the results.
     * @throws InvalidFormatException If the date string is empty or has invalid format.
     *                               Date must be in yyyy-MM-dd format.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new InvalidFormatException("Please provide a date to find tasks");
        }
        try {
            LocalDate targetDate = DateParser.parseDate(details.trim());
            ArrayList<Task> matchingTasks = tasks.findTasksOnDate(targetDate);
            StringBuilder results = new StringBuilder("Tasks on "
                + DateParser.formatForDisplay(targetDate) + ":\n");
            if (matchingTasks.isEmpty()) {
                results.append("No tasks found on this date.");
            } else {
                for (int i = 0; i < matchingTasks.size(); i++) {
                    results.append(String.format("%d. %s%n", i + 1, matchingTasks.get(i)));
                }
            }
            ui.showMessage(results.toString().trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException("Please use the format yyyy-MM-dd (e.g., 2024-12-31)");
        }
    }
}
