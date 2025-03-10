
// FindCommand.java - New keyword search command
package taskmanager.command;


import java.util.ArrayList;

import taskmanager.task.Task;
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.InvalidFormatException;

/**
 * Represents a command to find tasks by searching for keywords in their descriptions.
 */
public class FindCommand extends Command {
    /**
     * Creates a new FindCommand for keyword search.
     *
     * @param details The keyword to search for in task descriptions.
     */
    public FindCommand(String details) {
        super(details);
    }

    /**
     * Finds and displays all tasks scheduled for the specified date.
     * @throws ByteBiteException If the t is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new InvalidFormatException("Please provide a keyword to search for");
        }
        String keyword = details.trim();
        ArrayList<Task> matchingTasks = tasks.findTasksByKeyword(keyword);
        StringBuilder results = new StringBuilder("Here are the matching tasks in your list:\n");
        if (matchingTasks.isEmpty()) {
            results = new StringBuilder("No matching tasks found.");
        } else {
            for (int i = 0; i < matchingTasks.size(); i++) {
                results.append(String.format("%d.%s%n", i + 1, matchingTasks.get(i)));
            }
        }
        ui.showMessage(results.toString().trim());
    }
}
