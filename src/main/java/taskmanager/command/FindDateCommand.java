// FindDateCommand.java (updated to use DateParser)
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.task.Task;
import taskmanager.ui.Ui;
import taskmanager.parser.DateParser;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.InvalidFormatException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FindDateCommand extends Command {
    public FindDateCommand(String details) {
        super(details);
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new InvalidFormatException("Please provide a date to find tasks");
        }

        try {
            LocalDate targetDate = DateParser.parseDate(details.trim());
            ArrayList<Task> matchingTasks = tasks.findTasksOnDate(targetDate);
            
            StringBuilder results = new StringBuilder("Tasks on " + 
                DateParser.formatForDisplay(targetDate) + ":\n");
                
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
