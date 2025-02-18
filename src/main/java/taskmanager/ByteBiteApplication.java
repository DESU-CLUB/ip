package taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import taskmanager.ui.gui.MainWindow;

/**
 * The main class for the ByteBite application.
 * This class is the entry point for the application.
 * It is responsible for starting the application and initializing the main window.
 */
public class ByteBiteApplication extends Application {
    private ByteBite byteBite;

    /**
     * Sends a signal to the app components when the application is started.
     * It initializes the main window and sets up the scene.
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an error occurs while starting the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        byteBite = new ByteBite();
        // Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/MainWindow.fxml"));
        // Load the scene
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        // Add CSS
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/dialog-box.css").toExternalForm());
        // Get the controller and inject ByteBite instance
        MainWindow controller = fxmlLoader.getController();
        controller.setByteBite(byteBite);
        // Configure stage
        primaryStage.setTitle("ByteBite Task Manager");
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Sends signal for the application to start.
     * It starts the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--gui")) {
            launch(args);
        } else {
            ByteBite byteBite = new ByteBite();
            byteBite.start();
        }
    }
}
