// HelpCommand.java
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("");
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        ui.showHelp();
    }
}
