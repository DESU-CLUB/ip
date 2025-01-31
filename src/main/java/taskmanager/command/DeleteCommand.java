// DeleteCommand.java
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.task.Task;
import taskmanager.ui.Ui;
import taskmanager.parser.DateParser;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.InvalidFormatException;
import taskmanager.utils.TaskNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a command to delete a task from the task list.
 * Tasks are identified by their number in the list (1-based indexing).
 */
public class DeleteCommand extends Command {
    /**
     * Creates a new DeleteCommand with the given task number.
     *
     * @param details The task number to delete (as a string).
     */
    public DeleteCommand(String details) {
        super(details);
    }

    /**
     * Deletes the specified task from the task list.
     * 
     * @throws InvalidFormatException If the task number is not provided or invalid.
     * @throws TaskNotFoundException If the task number does not exist in the list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new InvalidFormatException("Please provide a task number to delete");
        }

        try {
            int taskNumber = Integer.parseInt(details.trim());
            int index = taskNumber - 1;
            
            Task deletedTask = tasks.deleteTask(index);
            String message = "Noted. I've removed this task:\n  " + deletedTask + 
                           "\nNow you have " + tasks.size() + " tasks in the list.";
            ui.showMessage(message);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Please provide a valid task number");
        } catch (TaskNotFoundException e) {
            throw e;
        }
    }

    @Override
    public boolean requiresSave() {
        return true;
    }
}
