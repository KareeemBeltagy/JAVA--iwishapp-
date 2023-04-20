package iwish.controllers;

import iwish.models.objects.User;
import iwish.models.requests.RemoveFriendRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static iwish.models.responses.RemoveFriendResponse;

public class FriendCardController implements Initializable {
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
    public void handleViewWishlistButton() {
        try {
            SceneController.loadMainScene();
            SceneController.switchFriendWishListScene(friend);
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    public void handleRemoveFriendButton() {
        try {
            if (SceneController.showConfirmationAlert("Are you want to remove '" + friend.getName() + "' friend?")) {
                int user_id = SceneController.getSessionUser().getId();
                int friend_id = friend.getId();
                RemoveFriendRequest request = new RemoveFriendRequest(user_id, friend_id);
                RemoveFriendResponse response = (RemoveFriendResponse) SocketController.sendRequest(request);
                if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
                SceneController.showSuccessAlert(response.getMessage());
                SceneController.loadMainScene();
                SceneController.switchScene("/iwish/views/FriendsView.fxml");
            }
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
