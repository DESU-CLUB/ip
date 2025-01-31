// Command.java (Abstract class)
package taskmanager.command;

import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;

/**
 * Represents an abstract command in the task management system.
 * All specific command types must extend this class and implement
 * the execute method with their specific behavior.
 */
public abstract class Command {
    protected String details;

    /**
     * Creates a new Command with the given details.
     *
     * @param details The command details or arguments provided by the user.
     */
    public Command(String details) {
        this.details = details;
    }

    /**
     * Executes the command with the given task list and UI.
     *
     * @param tasks The task list to operate on.
     * @param ui The UI to show output to the user.
     * @throws ByteBiteException If there is an error executing the command.
     */
    public abstract void execute(TaskList tasks, Ui ui) throws ByteBiteException;

    /**
     * Returns whether the command modifies the task list and requires saving.
     *
     * @return true if the task list should be saved after this command, false otherwise.
     */
    public boolean requiresSave() {
        return false;
    }
}
