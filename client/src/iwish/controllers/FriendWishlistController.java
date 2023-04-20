package iwish.controllers;

import iwish.models.objects;
import iwish.models.objects.User;
import iwish.models.requests;
import iwish.models.requests.GetWishlistItemsRequest;
import iwish.models.responses;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static iwish.models.responses.*;

public class FriendWishlistController implements Initializable {
    private final List<Parent> itemCards = new ArrayList<>();
    @FXML
    private FlowPane itemsFlowPane;
    private User friend;
    @FXML
    private Label titleLabel;

    public void setFriend(User friend) {
        this.friend = friend;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            titleLabel.setText(friend.getName()+"'s Wishlist");
            itemCards.clear();
            GetWishlistItemsRequest request = new GetWishlistItemsRequest(friend.getId());
            GetWishlistItemsResponse response = (GetWishlistItemsResponse) SocketController.sendRequest(request);
            if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
            for (objects.WishListItem item : response.getItems()) {
                Parent itemCard = SceneController.getFriendWishlistItemCard(friend, item);
                itemCards.add(itemCard);
            }

            itemsFlowPane.getChildren().setAll(itemCards);
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
            System.exit(1);
        }
    }
}