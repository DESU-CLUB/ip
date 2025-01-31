// Ui.java
package taskmanager.ui;

import taskmanager.task.Task;
import java.util.ArrayList;

public class Ui {
    private static final String LOGO = """
            ____  ____
          | __ )| __ )
          |  _ \\|  _ \\
          | |_) | |_) |
          |____/|____/
            """;
    
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String BORDER = "─".repeat(50);
    private static final String FAREWELL = """
        🤖 *beep* *boop* 
        Powering down... Hope to see you again soon! 
        *whirring stops* 🤖
        """;

    public void showWelcome() {
        showMessage(ANSI_CYAN + "Hello! I'm ByteBite" + ANSI_RESET);
        System.out.print(ANSI_CYAN + LOGO + ANSI_RESET);
        showMessage("Type 'help' to see available commands");
    }

    public void showFarewell() {
        showMessage(ANSI_CYAN + FAREWELL + ANSI_RESET);
    }

    public void showMessage(String message) {
        System.out.println(BORDER);
        System.out.println(message);
        System.out.println(BORDER);
    }

    public void showError(String message) {
        showMessage("⚠️ " + message);
    }

    public void showTaskAdded(Task task, int totalTasks) {
        StringBuilder message = new StringBuilder("Got it. I've added this task:\n");
        message.append("  ").append(task).append("\n");
        message.append("Now you have ").append(totalTasks).append(" tasks in the list.");
        showMessage(message.toString());
    }

    public void showTaskList(ArrayList<Task> tasks) {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            list.append(String.format("%d. %s%n", i + 1, tasks.get(i)));
        }
        showMessage(list.toString().trim());
    }

    public void showHelp() {
        String help = """
            Available commands:
            todo <task>
            deadline <task> /by yyyy-MM-dd
            event <name> /from yyyy-MM-dd /to yyyy-MM-dd
            list
            find <yyyy-MM-dd>
            mark <task number>
            unmark <task number>
            delete <task number>
            bye
            
            Date format: yyyy-MM-dd (e.g., 2024-12-31 for Dec 31 2024)
            """;
        showMessage(help);
    }
}
