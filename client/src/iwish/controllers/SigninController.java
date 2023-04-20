package iwish.controllers;

import iwish.models.requests.LoginUserRequest;
import iwish.models.responses.LoginUserResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SigninController implements Initializable {
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleSigninButton() {
        try {
            String email = emailField.getText();
            String password = passwordField.getText();
            LoginUserRequest request = new LoginUserRequest(email, password);
            LoginUserResponse response = (LoginUserResponse) SocketController.sendRequest(request);
            if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
            SceneController.showSuccessAlert(response.getMessage());
            SceneController.setSessionUser(response.getUser());
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/HomeView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    public void handleCreateAnAccountButton() {
        try {
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/CreateAnAccountView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
