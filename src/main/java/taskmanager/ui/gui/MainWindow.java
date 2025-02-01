package taskmanager.ui.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import taskmanager.ByteBite;
import taskmanager.ui.GuiUi;
import taskmanager.utils.ByteBiteException;

/**
 * Controller for the main window of the GUI.
 */
public class MainWindow {
    private ByteBite byteBite;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;
    @FXML private ToggleButton darkModeToggle;
    private Image userImage;
    private Image botImage;

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        try {
            // Load images
            userImage = new Image(getClass().getResourceAsStream("/images/user.png"));
            botImage = new Image(getClass().getResourceAsStream("/images/bot.png"));
            // Make scroll pane automatically scroll to bottom
            scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
            // Add welcome message
            dialogContainer.getChildren().add(
                DialogBox.getBotDialog("Hello! I'm ByteBite. Type 'help' to see what I can do!", botImage)
            );
            darkModeToggle.setText("dark");

            // Setup dark mode toggle behavior
            darkModeToggle.setOnAction(event -> {
                Scene scene = dialogContainer.getScene();
                if (scene != null) {
                    if (darkModeToggle.isSelected()) {
                        scene.getRoot().getStyleClass().add("dark");
                        darkModeToggle.setText("light");

                    } else {
                        scene.getRoot().getStyleClass().remove("dark");
                        darkModeToggle.setText("dark");
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Error initializing MainWindow: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setByteBite(ByteBite bb) {
        byteBite = bb;
    }

    /**
     * Handles user input and displays the appropriate response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (!input.isEmpty()) {
            try {
                // Add user message
                dialogContainer.getChildren().add(
                    DialogBox.getUserDialog(input, userImage)
                );

                if (input.equalsIgnoreCase("bye")) {
                    DialogBox farewellBox = DialogBox.getBotDialog(
                           "🤖 *beep*  *boop* Powering down... Hope to see you again soon! *whirring stops* 🤖",
                            botImage);
                    dialogContainer.getChildren().add(farewellBox);
                    // Wait for 3 seconds
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> {
                        // Exit the application
                        Platform.exit();
                    });
                    delay.play();
                } else {
                    // Create custom UI for this command
                    GuiUi guiUi = new CustomGuiUi(dialogContainer, botImage);
                    // Handle the command
                    byteBite.handleCommandWithUi(input, guiUi);
                    // Clear input
                    userInput.clear();
                }
            } catch (ByteBiteException e) {
                DialogBox errorBox = DialogBox.getBotDialog("Error: " + e.getMessage(), botImage);
                errorBox.getStyleClass().add("error-message");
                dialogContainer.getChildren().add(errorBox);
            }
        }
    }
    /**
     * Custom GUI UI for ByteBite.
     */
    private class CustomGuiUi extends GuiUi {
        public CustomGuiUi(VBox dialogContainer, Image botImage) {
            super(dialogContainer, botImage);
        }
        @Override
        public void showMessage(String message) {
            DialogBox box = DialogBox.getBotDialog(message, botImage);
            String commandType = getCommandType(userInput.getText().trim().split(" ")[0]);
            box.getStyleClass().add(commandType);
            dialogContainer.getChildren().add(box);
        }
        @Override
        public void showError(String message) {
            DialogBox box = DialogBox.getBotDialog("⚠️ " + message, botImage);
            box.getStyleClass().add("error-message");
            dialogContainer.getChildren().add(box);
        }
    }

    /**
     * Returns the type of command based on the first word of the input.
     * @param command The command input by the user.
     * @return The type of command.
     */
    private String getCommandType(String command) {
        return switch (command.toLowerCase()) {
        case "todo" -> "todo-command";
        case "deadline" -> "deadline-command";
        case "event" -> "event-command";
        case "list" -> "list-command";
        case "delete" -> "delete-command";
        case "mark", "unmark" -> "mark-command";
        default -> "bot-dialog";
        };
    }
}
