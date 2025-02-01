
// DeadlineCommand.java (enhanced with date parsing)
package taskmanager.command;


import java.time.LocalDate;

import taskmanager.parser.DateParser;
import taskmanager.task.Deadline;
import taskmanager.task.Task;
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import taskmanager.utils.EmptyDescriptionException;
import taskmanager.utils.InvalidFormatException;

/**
 * Represents a command to create a new deadline task with a due date.
 * Deadlines must have a description and valid date.
 */
public class DeadlineCommand extends Command {
    private static final String BY_DELIMITER = " /by ";
    /**
     * Creates a new DeadlineCommand with the given task details.
     *
     * @param details The deadline description and date in the format:
     *                "description /by date"
     */
    public DeadlineCommand(String details) {
        super(details);
    }

    /**
     * Creates a new deadline task and adds it to the task list.
     * @throws EmptyDescriptionException If the deadline description is empty.
     * @throws InvalidFormatException If the command format is invalid or date is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }

        String[] parts = details.split(BY_DELIMITER, 2);
        if (parts.length != 2 || parts[0].trim().isEmpty()) {
            throw new InvalidFormatException("Please use format: deadline <task> /by <date>");
        }

        String description = parts[0].trim();
        String dateStr = parts[1].trim();

        try {
            LocalDate date = DateParser.parseDate(dateStr);
            Task deadline = new Deadline(description, date);
            tasks.addTask(deadline);
            ui.showTaskAdded(deadline, tasks.size());
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException(e.getMessage());
        }
    }

    @Override
    public boolean requiresSave() {
        return true;
    }
}
