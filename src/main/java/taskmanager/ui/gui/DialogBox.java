package taskmanager.ui.gui;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Represents a dialog box in the GUI chat interface.
 * Supports displaying messages with hashtags formatting and profile pictures.
 * The dialog box can be configured for both user and bot messages, with
 * different alignment and styling for each.
 */
public class DialogBox extends HBox {
    private static final Color[] TAG_COLORS = {
        Color.rgb(29, 161, 242), // Twitter Blue
        Color.rgb(67, 160, 71), // Green
        Color.rgb(245, 124, 0), // Orange
        Color.rgb(142, 36, 170), // Purple
        Color.rgb(230, 81, 0), // Deep Orange
        Color.rgb(0, 121, 107), // Teal
        Color.rgb(194, 40, 120) // Pink
    };
    private static final Random random = new Random();

    @FXML
    private VBox dialogContainer;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a new dialog box with the specified text and profile image.
     * Loads the FXML layout and initializes the dialog components.
     *
     * @param text The message text to display.
     * @param img The profile image to show.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            displayPicture.setImage(img);
            displayPicture.setFitHeight(128.0);
            displayPicture.setFitWidth(128.0);
            displayPicture.setPreserveRatio(true);
            displayPicture.setSmooth(true);
            processText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes the input text to format hashtags and create the dialog content.
     * Splits the text into lines and processes each line separately,
     * identifying and formatting hashtags with colored backgrounds.
     *
     * @param text The text to process and display.
     */
    private void processText(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            HBox lineBox = new HBox(5);
            lineBox.setAlignment(Pos.CENTER_LEFT);
            // Find all tags in the text
            Pattern tagPattern = Pattern.compile("#\\w+");
            Matcher tagMatcher = tagPattern.matcher(line);
            // Split the text into parts while preserving tags
            int lastEnd = 0;
            while (tagMatcher.find()) {
                // Add text before the tag
                String beforeTag = line.substring(lastEnd, tagMatcher.start()).trim();
                if (!beforeTag.isEmpty()) {
                    addTextLabel(lineBox, beforeTag);
                }
                // Add the tag
                addTagLabel(lineBox, tagMatcher.group());
                lastEnd = tagMatcher.end();
            }
            // Add remaining text after last tag
            String remaining = line.substring(lastEnd).trim();
            if (!remaining.isEmpty()) {
                addTextLabel(lineBox, remaining);
            }
            if (!lineBox.getChildren().isEmpty()) {
                dialogContainer.getChildren().add(lineBox);
            }
        }
    }

    /**
     * Adds a regular text label to the container.
     *
     * @param container The container to add the label to.
     * @param text The text content for the label.
     */
    private void addTextLabel(HBox container, String text) {
        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        container.getChildren().add(textLabel);
    }

    /**
     * Adds a formatted hashtag label to the container.
     * The tag gets a random color from the predefined color set,
     * with a matching background and border.
     *
     * @param container The container to add the tag label to.
     * @param tag The hashtag text to format.
     */
    private void addTagLabel(HBox container, String tag) {
        Label tagLabel = new Label(tag);
        Color tagColor = TAG_COLORS[random.nextInt(TAG_COLORS.length)];
        String backgroundColor = String.format("rgba(%d, %d, %d, 0.1)",
            (int) (tagColor.getRed() * 255),
            (int) (tagColor.getGreen() * 255),
            (int) (tagColor.getBlue() * 255));
        tagLabel.setStyle(String.format(
            "-fx-background-color: %s; "
            + "-fx-text-fill: rgb(%d, %d, %d); "
            + "-fx-border-color: rgb(%d, %d, %d); "
            + "-fx-background-radius: 12px; "
            + "-fx-border-radius: 12px; "
            + "-fx-padding: 2px 8px;",
            backgroundColor,
            (int) (tagColor.getRed() * 255),
            (int) (tagColor.getGreen() * 255),
            (int) (tagColor.getBlue() * 255),
            (int) (tagColor.getRed() * 255),
            (int) (tagColor.getGreen() * 255),
            (int) (tagColor.getBlue() * 255)));
        container.getChildren().add(tagLabel);
    }

    /**
     * Flips the dialog box layout for bot messages.
     * Reverses the order of components and adjusts margins.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        HBox.setMargin(dialogContainer, new Insets(0, 15, 0, 0));
        HBox.setMargin(displayPicture, new Insets(10, 0, 0, 15));
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param text The message text.
     * @param img The user's profile image.
     * @return A dialog box configured for user messages.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        HBox.setMargin(db.dialogContainer, new Insets(0, 0, 0, 15));
        HBox.setMargin(db.displayPicture, new Insets(0, 15, 0, 0));
        return db;
    }

    /**
     * Creates a dialog box for bot messages.
     * The layout is flipped compared to user messages.
     *
     * @param text The message text.
     * @param img The bot's profile image.
     * @return A dialog box configured for bot messages.
     */
    public static DialogBox getBotDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
