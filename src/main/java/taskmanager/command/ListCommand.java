// ListCommand.java
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;


/**
 * Represents a command to list all tasks in the task list.
 * Shows the current tasks in a formatted display to the user.
 */
public class ListCommand extends Command {
    /**
     * Creates a new ListCommand with no additional details needed.
     */
    public ListCommand() {
        super("");
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        ui.showTaskList(tasks.getTaskList());
    }
}
