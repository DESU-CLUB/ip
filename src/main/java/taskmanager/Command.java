// Command.java (Abstract class)
package taskmanager;

public abstract class Command {
    protected String details;

    public Command(String details) {
        this.details = details;
    }

    public abstract void execute(TaskList tasks, Ui ui) throws ByteBiteException;

    public boolean requiresSave() {
        return false;
    }
}
