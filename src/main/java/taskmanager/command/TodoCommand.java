// TodoCommand.java
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.task.Task;
import taskmanager.task.Todo;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.EmptyDescriptionException;

/**
 * Represents a command to create a new todo task.
 * Todo tasks require only a description with no date.
 */
public class TodoCommand extends Command {
    /**
     * Creates a new TodoCommand with the given task details.
     *
     * @param details The todo task description.
     */
    public TodoCommand(String details) {
        super(details);
    }

    /**
     * Creates a new todo task and adds it to the task list.
     * 
     * @throws EmptyDescriptionException If the todo description is empty.
     */
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
