// MarkCommand.java
package taskmanager.command;


import taskmanager.task.Task;
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.InvalidFormatException;
import taskmanager.utils.TaskNotFoundException;

/**
 * Represents a command to mark or unmark a task as done.
 * Tasks are identified by their number in the list (1-based indexing).
 */
public class MarkCommand extends Command {
    private final boolean isDone;

    /**
     * Creates a new MarkCommand with the given task number and mark status.
     *
     * @param details The task number to mark/unmark (as a string).
     * @param isDone true to mark as done, false to mark as not done.
     */
    public MarkCommand(String details, boolean isDone) {
        super(details);
        this.isDone = isDone;
    }

    /**
     * Marks or unmarks the specified task as done.
     * @throws InvalidFormatException If the task number is not provided or invalid.
     * @throws TaskNotFoundException If the task number does not exist in the list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new InvalidFormatException("Please provide a task number to "
                + (isDone ? "mark" : "unmark"));
        }

        try {
            int taskNumber = Integer.parseInt(details.trim());
            int index = taskNumber - 1;
            Task task = tasks.getTask(index);
            if (isDone) {
                task.markAsDone();
                ui.showMessage("Nice! I've marked this task as done:\n  " + task);
            } else {
                task.unmark();
                ui.showMessage("OK, I've marked this task as not done yet:\n  " + task);
            }
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
