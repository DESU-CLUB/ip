// ListCommand.java
package taskmanager;

public class ListCommand extends Command {
    public ListCommand() {
        super("");
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        ui.showTaskList(tasks.getTaskList());
    }
}
