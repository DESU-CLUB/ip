
// DeadlineCommand.java (enhanced with date parsing)
package taskmanager;
import java.time.LocalDate;

public class DeadlineCommand extends Command {
    private static final String BY_DELIMITER = " /by ";
    
    public DeadlineCommand(String details) {
        super(details);
    }

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
