package iwish.controllers;

import iwish.models.objects.Item;
import iwish.models.requests.AddItemToWishlistRequest;
import iwish.models.responses.AddItemToWishlistResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeItemCardController implements Initializable {
    @FXML
    public Label cardTitleLabel;
    @FXML
    public Label cardPriceLabel;
    @FXML
    public ImageView imageView;
    @FXML
    private Item item;

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardTitleLabel.setText(item.getTitle());
        cardPriceLabel.setText("E£ " + item.getPrice());
        Image image = new Image(new ByteArrayInputStream(item.getImage()));
        imageView.setImage(image);
    }

    @FXML
    public void handleAddToWishlist() {
        try {
            if (SceneController.getSessionUser() == null)
                SceneController.showErrorAlert(new Exception("Please login first"));
            else {
                int user_id = SceneController.getSessionUser().getId();
                int item_id = item.getId();
                AddItemToWishlistRequest request = new AddItemToWishlistRequest(user_id, item_id);
                AddItemToWishlistResponse response = (AddItemToWishlistResponse) SocketController.sendRequest(request);
                if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
                SceneController.showSuccessAlert(response.getMessage());
                SceneController.loadMainScene();
            }
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
