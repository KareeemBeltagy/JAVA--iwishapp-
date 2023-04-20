package iwish.controllers;

import iwish.models.requests.CreateUserRequest;
import iwish.models.responses.CreateUserResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAnAccountController implements Initializable {
    @FXML
    public TextField nameField;
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
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/SigninView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    public void handleSignupButton() {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            CreateUserRequest request = new CreateUserRequest(name, email, password);
            CreateUserResponse response = (CreateUserResponse) SocketController.sendRequest(request);
            if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
            SceneController.showSuccessAlert(response.getMessage());
            SceneController.setSessionUser(response.getUser());
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/HomeView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
