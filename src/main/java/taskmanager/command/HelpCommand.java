// HelpCommand.java
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;

/**
 * Represents a command to display help information about using the application.
 * Shows usage instructions and available commands to the user.
 */
public class HelpCommand extends Command {
    /**
     * Creates a new HelpCommand with no additional details needed.
     */
    public HelpCommand() {
        super("");
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        ui.showHelp();
    }
}
