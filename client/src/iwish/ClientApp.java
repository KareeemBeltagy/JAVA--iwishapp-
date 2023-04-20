/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwish;

import iwish.controllers.SceneController;
import iwish.controllers.SocketController;
import javafx.application.Application;
import javafx.stage.Stage;


public class ClientApp extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            
            SocketController.connect();

            SceneController.setPrimaryStage(primaryStage);
            SceneController.getPrimaryStage().setTitle("iWish");
            SceneController.loadMainScene();
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
