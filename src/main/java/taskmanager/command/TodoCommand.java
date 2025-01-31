// TodoCommand.java
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.task.Task;
import taskmanager.task.Todo;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.EmptyDescriptionException;


public class TodoCommand extends Command {
    public TodoCommand(String details) {
        super(details);
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
        Task todo = new Todo(details);
        tasks.addTask(todo);
        ui.showTaskAdded(todo, tasks.size());
    }

    @Override
    public boolean requiresSave() {
        return true;
    }
}
