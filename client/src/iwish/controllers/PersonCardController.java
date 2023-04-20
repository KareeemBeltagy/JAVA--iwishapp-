package iwish.controllers;

import iwish.models.objects.User;
import iwish.models.requests;
import iwish.models.requests.RemoveFriendRequest;
import iwish.models.requests.SendFriendRequestRequest;
import iwish.models.responses;
import iwish.models.responses.SendFriendRequestResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static iwish.models.responses.RemoveFriendResponse;

public class PersonCardController implements Initializable {
    @FXML
    public Label cardNameLabel;
    @FXML
    private User friend;

    public void setFriend(User friend) {
        this.friend = friend;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardNameLabel.setText(friend.getName());
    }

    @FXML
    public void handleSendRequestButton() {
        try {
            if (SceneController.getSessionUser() == null)
                SceneController.showErrorAlert(new Exception("Please login first"));
            else {
                int user_id = SceneController.getSessionUser().getId();
                int friend_id = friend.getId();
                SendFriendRequestRequest request = new SendFriendRequestRequest(user_id, friend_id);
                SendFriendRequestResponse response = (SendFriendRequestResponse) SocketController.sendRequest(request);
                if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
                SceneController.showSuccessAlert(response.getMessage());
                SceneController.loadMainScene();
                SceneController.switchScene("/iwish/views/PeopleView.fxml");
            }
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
