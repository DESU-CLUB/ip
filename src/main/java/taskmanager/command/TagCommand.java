// TagCommand.java
package taskmanager.command;

import taskmanager.task.Task;
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.InvalidFormatException;
import taskmanager.utils.TaskNotFoundException;

/**
 * Represents a command to add or remove tags from a task.
 * Handles both tagging and untagging operations based on the isAdding flag.
 * Tags are added with '#' prefix if not provided.
 */
public class TagCommand extends Command {
    private final boolean isAdding;

    /**
     * Creates a new TagCommand for either adding or removing tags.
     *
     * @param details The command details containing task number and tag.
     * @param isAdding true to add a tag, false to remove a tag.
     */
    public TagCommand(String details, boolean isAdding) {
        super(details);
        this.isAdding = isAdding;
    }

    /**
     * Executes the tag command by adding or removing a tag from the specified task.
     * The command format should be: "tag/untag [task number] [tag]".
     * The tag can be provided with or without the '#' prefix.
     *
     * @param tasks The task list to modify.
     * @param ui The ui to show the results.
     * @throws InvalidFormatException If the command format is invalid or task number
     *         is not a valid number.
     * @throws TaskNotFoundException If the specified task number does not exist.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new InvalidFormatException(
                "Please provide task number and tag in format: "
                + (isAdding ? "tag" : "untag") + " <task number> <tag>");
        }

        String[] parts = details.trim().split("\\s+", 2);
        if (parts.length != 2) {
            throw new InvalidFormatException("Please provide both task number and tag");
        }

        try {
            int taskNumber = Integer.parseInt(parts[0]);
            int index = taskNumber - 1;
            Task task = tasks.getTask(index);
            String tag = parts[1].trim();

            if (isAdding) {
                task.addTag(tag);
                ui.showMessage("Added tag " + tag + " to task:\n  " + task);
            } else {
                task.removeTag(tag);
                ui.showMessage("Removed tag " + tag + " from task:\n  " + task);
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
