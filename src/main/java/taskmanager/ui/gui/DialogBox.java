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

public class DialogBox extends HBox {
    @FXML
    private VBox dialogContainer;
    @FXML
    private ImageView displayPicture;
    
    private static final Random random = new Random();
    private static final Pattern TAG_PATTERN = Pattern.compile("#\\w+|[^#]+");
    
    private static final Color[] TAG_COLORS = {
        Color.rgb(29, 161, 242),   // Twitter Blue
        Color.rgb(67, 160, 71),    // Green
        Color.rgb(245, 124, 0),    // Orange
        Color.rgb(142, 36, 170),   // Purple
        Color.rgb(230, 81, 0),     // Deep Orange
        Color.rgb(0, 121, 107),    // Teal
        Color.rgb(194, 40, 120)    // Pink
    };

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

            // Set the image and make sure it's sized correctly
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

    private void processText(String text) {
        // Process each line separately
        String[] lines = text.split("\n");
        for (String line : lines) {
            HBox lineBox = new HBox(5);
            lineBox.setAlignment(Pos.CENTER_LEFT);
            
            // Use regex matcher to properly split text and tags
            Matcher matcher = TAG_PATTERN.matcher(line);
            while (matcher.find()) {
                String part = matcher.group();
                if (part.startsWith("#")) {
                    addTagLabel(lineBox, part);
                } else {
                    addTextLabel(lineBox, part);
                }
            }
            
            dialogContainer.getChildren().add(lineBox);
        }
    }

    private void addTextLabel(HBox container, String text) {
        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        container.getChildren().add(textLabel);
    }

    private void addTagLabel(HBox container, String tag) {
        Label tagLabel = new Label(tag);
        Color tagColor = TAG_COLORS[random.nextInt(TAG_COLORS.length)];
        String backgroundColor = String.format("rgba(%d, %d, %d, 0.1)", 
            (int)(tagColor.getRed() * 255),
            (int)(tagColor.getGreen() * 255),
            (int)(tagColor.getBlue() * 255));
        
        tagLabel.setStyle(String.format(
            "-fx-background-color: %s; " +
            "-fx-text-fill: rgb(%d, %d, %d); " +
            "-fx-border-color: rgb(%d, %d, %d); " +
            "-fx-background-radius: 12px; " +
            "-fx-border-radius: 12px; " +
            "-fx-padding: 2px 8px;",
            backgroundColor,
            (int)(tagColor.getRed() * 255),
            (int)(tagColor.getGreen() * 255),
            (int)(tagColor.getBlue() * 255),
            (int)(tagColor.getRed() * 255),
            (int)(tagColor.getGreen() * 255),
            (int)(tagColor.getBlue() * 255)));
        
        container.getChildren().add(tagLabel);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);

        // Adjust margins after flip for user messages
        HBox.setMargin(dialogContainer, new Insets(0, 5, 0, 0));  // right margin
        HBox.setMargin(displayPicture, new Insets(0, 0, 0, 15));  // left margin
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        // Adjust margins for user messages
        HBox.setMargin(db.dialogContainer, new Insets(0, 0, 0, 15));  // left margin
        HBox.setMargin(db.displayPicture, new Insets(0, 15, 0, 0));  // right margin
        db.setAlignment(Pos.CENTER_RIGHT);
        return db;
    }

    public static DialogBox getBotDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
