package taskmanager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import taskmanager.task.Task;
import taskmanager.ui.GuiUi;
import taskmanager.utils.ByteBiteException;

/**
 * Controller for the main view of the application.
 */
public class MainViewController {
    private ByteBite byteBite;
    @FXML private TextField commandInput;
    @FXML private ListView<Task> taskListView;
    @FXML private TextArea outputArea;
    @FXML private VBox mainContainer;
    private ObservableList<Task> taskItems;
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/bot.png"));

    /**
     * Initializes the controller with the ByteBite instance.
     * @param byteBite The ByteBite instance to use.
     */
    public void initialize(ByteBite byteBite) {
        this.byteBite = byteBite;
        taskItems = FXCollections.observableArrayList();
        taskListView.setItems(taskItems);
        // Custom cell factory for task display
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                } else {
                    setText(task.toString());
                }
            }
        });
        // Handle command input with Enter key
        commandInput.setOnAction(event -> handleCommand());
        // Initialize with current tasks
        refreshTaskList();
    }
    /**
     * Handles the command input by the user.
     */
    @FXML
    private void handleCommand() {
        String command = commandInput.getText().trim();
        if (!command.isEmpty()) {
            try {
                // Create a temporary VBox for the chat-style output
                VBox chatContainer = new VBox(10); // 10 is spacing between messages
                GuiUi guiUi = new GuiUi(chatContainer, botImage);
                byteBite.handleCommandWithUi(command, guiUi);
                // Add the chat output to the TextArea
                outputArea.appendText(command + "\n");
                for (String line : chatContainer.getChildren().toString().split("\n")) {
                    outputArea.appendText(line + "\n");
                }
                refreshTaskList();
            } catch (ByteBiteException e) {
                outputArea.appendText("Error: " + e.getMessage() + "\n");
            }
            commandInput.clear();
        }
    }
    /**
     * Refreshes the task list displayed in the main view.
     */
    private void refreshTaskList() {
        taskItems.clear();
        taskItems.addAll(byteBite.getTasks().getTaskList());
    }
}
