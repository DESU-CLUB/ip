
// ByteBite.java
package taskmanager;

import taskmanager.command.Command;
import taskmanager.parser.Parser;
import taskmanager.storage.Storage;
import taskmanager.task.TaskList;
import taskmanager.ui.Ui;
import taskmanager.utils.ByteBiteException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * The main class for the ByteBite task management application.
 * Handles initialization of components, user input processing,
 * and task persistence.
 */
public class ByteBite {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Creates a new ByteBite application instance.
     * Initializes the UI, storage, and task management components.
     * Attempts to load existing tasks from storage if available.
     */
    public ByteBite() {
        ui = new Ui();
        storage = new Storage("./data", "tasks.txt");
        parser = new Parser();
        try {
            tasks = new TaskList(storage.loadTasksFromFile());
            if (!tasks.isEmpty()) {
                ui.showMessage("Loaded " + tasks.size() + " tasks from storage");
            }
        } catch (IOException e) {
            ui.showError("Error loading tasks from storage: " + e.getMessage());
            handleCorruptedFile(e);
        }
    }

    /**
     * Handles corrupted task file by attempting to remove it and create a new one.
     * Shows appropriate error messages if file operations fail.
     *
     * @param error The IOException that triggered the corrupted file handling.
     */
    public void handleCorruptedFile(IOException error) {
        ui.showError("⚠️ Error detected in tasks file: " + error.getMessage());
        try {
            if (storage.deleteTasksFile()) {
                ui.showMessage("Corrupted tasks file has been removed");
                tasks = new TaskList();
                storage.saveTasksToFile(tasks.getTaskList());
                ui.showMessage("Created new empty tasks file");
            } else {
                ui.showError("❌ Unable to remove corrupted file - please check file permissions");
            }
        } catch (IOException e) {
            ui.showError("❌ Critical error handling corrupted file: " + e.getMessage());
        }
    }

    /**
     * Saves the current task list to storage.
     * Shows an error message if saving fails.
     */
    public void saveTasks() {
        try {
            storage.saveTasksToFile(tasks.getTaskList());
        } catch (IOException e) {
            ui.showError("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Starts the ByteBite application.
     * Shows welcome message and begins processing user input until exit command is received.
     */
    public void start() {
        ui.showWelcome();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = reader.readLine()) != null) {
                if (input.equalsIgnoreCase("bye")) {
                    ui.showFarewell();
                    break;
                }
                try {
                    handleCommand(input.trim());
                } catch (ByteBiteException e) {
                    ui.showError(e.getMessage());
                }
            }
        } catch (Exception e) {
            ui.showError("Error reading input: " + e.getMessage());
        }
    }

    /**
     * Processes a user command by parsing it and executing the corresponding action.
     * Saves task list if the command modifies task data.
     *
     * @param input The user input string to process.
     * @throws ByteBiteException If there is an error processing the command.
     */
    public void handleCommand(String input) throws ByteBiteException {
        Command command = parser.parseCommand(input);
        command.execute(tasks, ui);
        if (command.requiresSave()) {
            saveTasks();
        }
    }
}
