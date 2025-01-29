// MarkCommand.java
package taskmanager;

public class MarkCommand extends Command {
    private final boolean markAsDone;

    public MarkCommand(String details, boolean markAsDone) {
        super(details);
        this.markAsDone = markAsDone;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        if (details.isEmpty()) {
            throw new InvalidFormatException("Please provide a task number to " + 
                (markAsDone ? "mark" : "unmark"));
        }

        try {
            int taskNumber = Integer.parseInt(details.trim());
            int index = taskNumber - 1;
            Task task = tasks.getTask(index);
            
            if (markAsDone) {
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
