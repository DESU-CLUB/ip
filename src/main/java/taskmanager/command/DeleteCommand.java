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

public class DeleteCommand extends Command {
    public DeleteCommand(String details) {
        super(details);
    }

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
