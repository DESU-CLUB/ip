// ListCommand.java
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;

public class ListCommand extends Command {
    public ListCommand() {
        super("");
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        ui.showTaskList(tasks.getTaskList());
    }
}
