// Command.java (Abstract class)
package taskmanager;
import java.util.ArrayList;

class TodoCommand extends Command {
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
