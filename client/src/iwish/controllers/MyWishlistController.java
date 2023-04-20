package iwish.controllers;

import iwish.models.objects;
import iwish.models.objects.User;
import iwish.models.requests.GetWishlistItemsRequest;
import iwish.models.responses.GetWishlistItemsResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyWishlistController implements Initializable {
    private final List<Parent> itemCards = new ArrayList<>();
    @FXML
    private FlowPane itemsFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            itemCards.clear();
            User user = SceneController.getSessionUser();
            GetWishlistItemsRequest request = new GetWishlistItemsRequest(user.getId());
            GetWishlistItemsResponse response = (GetWishlistItemsResponse) SocketController.sendRequest(request);
            if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
            for (objects.WishListItem item : response.getItems()) {
                Parent itemCard = SceneController.getMyWishlistItemCard(item);
                itemCards.add(itemCard);
            }

            itemsFlowPane.getChildren().setAll(itemCards);
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
            System.exit(1);
        }
    }
}
