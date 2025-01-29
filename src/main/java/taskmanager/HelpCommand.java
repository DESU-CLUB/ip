// HelpCommand.java
package taskmanager;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("");
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws ByteBiteException {
        ui.showHelp();
    }
}
