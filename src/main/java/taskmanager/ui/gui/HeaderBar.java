package taskmanager.ui.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

/**
 * Represents the header bar at the top of the application.
 * Displays the application name and potentially other UI controls.
 */
public class HeaderBar extends HBox {
    /**
     * Creates a new header bar.
     */
    public HeaderBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/HeaderBar.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
