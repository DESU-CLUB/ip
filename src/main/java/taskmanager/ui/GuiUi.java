package taskmanager.ui;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import taskmanager.task.Task;
import taskmanager.ui.gui.DialogBox;

/**
 * Represents the user interface for the GUI.
 */
public class GuiUi extends Ui {
    private final VBox dialogContainer;
    private final Image botImage;
    /**
     * Constructs a new GuiUi object.
     * @param dialogContainer The container for the dialog.
     * @param botImage The image of the bot.
     */
    public GuiUi(VBox dialogContainer, Image botImage) {
        this.dialogContainer = dialogContainer;
        this.botImage = botImage;
    }

    /**
     * Shows a message in the dialog.
     * @param message The message to show.
     */
    @Override
    public void showMessage(String message) {
        dialogContainer.getChildren().add(
            DialogBox.getBotDialog(message, botImage)
        );
    }

    /**
     * Shows an error message in the dialog.
     * @param message The error message to show.
     */
    @Override
    public void showError(String message) {
        dialogContainer.getChildren().add(
            DialogBox.getBotDialog("⚠️ " + message, botImage)
        );
    }
    /**
     * Shows a welcome message in the dialog.
     */
    @Override
    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            showMessage("No tasks in the list!");
            return;
        }
        StringBuilder list = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            list.append(String.format("%d. %s%n", i + 1, tasks.get(i)));
        }
        showMessage(list.toString().trim());
    }

    /**
     * Shows a message when a task is marked as done.
     * @param task The task that was marked as done.
    * @param totalTasks The total number of tasks in the list.
     */
    @Override
    public void showTaskAdded(Task task, int totalTasks) {
        StringBuilder message = new StringBuilder("Got it. I've added this task:\n");
        message.append("  ").append(task).append("\n");
        message.append("Now you have ").append(totalTasks)
               .append(totalTasks == 1 ? " task" : " tasks").append(" in the list.");
        showMessage(message.toString());
    }

    /**
     * Shows the help message in the dialog.
     */
    @Override
    public void showHelp() {
        String help = """
            Here's what I can do:
            • todo <task> - Add a todo task
            • deadline <task> /by yyyy-MM-dd - Add a deadline
            • event <name> /from yyyy-MM-dd /to yyyy-MM-dd - Add an event
            • list - Show all tasks
            • find <keyword> - Search for tasks
            • finddate yyyy-MM-dd - Find tasks on a date
            • mark <task number> - Mark a task as done
            • unmark <task number> - Mark a task as not done
            • delete <task number> - Delete a task
            • bye - Exit the program
            """;
        showMessage(help);
    }
}
