
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


public class ByteBite {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

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

    private void handleCorruptedFile(IOException error) {
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

    private void saveTasks() {
        try {
            storage.saveTasksToFile(tasks.getTaskList());
        } catch (IOException e) {
            ui.showError("Error saving tasks: " + e.getMessage());
        }
    }

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

    private void handleCommand(String input) throws ByteBiteException {
        Command command = parser.parseCommand(input);
        command.execute(tasks, ui);
        if (command.requiresSave()) {
            saveTasks();
        }
    }
}
