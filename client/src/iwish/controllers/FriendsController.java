package iwish.controllers;

import iwish.models.objects;
import iwish.models.requests;
import iwish.models.requests.GetFriendsRequest;
import iwish.models.responses;
import iwish.models.responses.GetFriendsResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static iwish.models.objects.*;

public class FriendsController implements Initializable {
    @FXML
    public FlowPane productFlowPane;
    private final List<Parent> friendCards = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            friendCards.clear();
            User user = SceneController.getSessionUser();
            GetFriendsRequest request = new GetFriendsRequest(user.getId());
            GetFriendsResponse response = (GetFriendsResponse) SocketController.sendRequest(request);
            if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
            for (User friend : response.getFriends()) {
                Parent friendCard = SceneController.getFriendCard(friend);
                friendCards.add(friendCard);
            }

            productFlowPane.getChildren().setAll(friendCards);
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
            System.exit(1);
        }
    }
}
