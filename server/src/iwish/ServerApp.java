/**
 * The ServerApp class is the entry point for the server-side of the i-Wish application.
 * It extends the JavaFX Application class to provide a graphical user interface (GUI) for the server.
 * The main method is called when the application starts, launching the JavaFX application.
 * The start method sets up the primary stage and loads the main scene for the application.
 * If any exceptions are thrown, an error alert is displayed.
 *
 * @author Islam Awad, Kareem Mohamed, Malak Magdi, Sarah Salah
 */
package iwish;

import iwish.controllers.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerApp extends Application {
    /**
     * The main method is called when the application starts, launching the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method sets up the primary stage and loads the main scene for the application.
     * If any exceptions are thrown, an error alert is displayed.
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if an error occurs while setting up the primary stage or loading the main scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Set the primary stage for the scene controller
            SceneController.setPrimaryStage(primaryStage);

            // Set the title of the primary stage
            SceneController.getPrimaryStage().setTitle("iWish");

            // Load the main scene for the application
            SceneController.loadMainScene();
        } catch (Exception exception) {
            // If an exception is thrown, display an error alert
            SceneController.showErrorAlert(exception);
        }
    }
}