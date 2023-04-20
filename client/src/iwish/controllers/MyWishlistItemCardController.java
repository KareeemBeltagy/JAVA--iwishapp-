package iwish.controllers;

import iwish.models.objects;
import iwish.models.objects.Item;
import iwish.models.requests.RemoveWishlistItemRequest;
import iwish.models.responses.RemoveWishlistItemResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class MyWishlistItemCardController implements Initializable {
    @FXML
    public Label cardTitleLabel;
    @FXML
    public Label cardPriceLabel;
    @FXML
    public ImageView imageView;
    @FXML
    private objects.WishListItem item;
    @FXML 
    public Button submitbtn;
    
    public void setItem(objects.WishListItem item) {
        this.item = item;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardTitleLabel.setText(item.getTitle());
        cardPriceLabel.setText("E£ " + item.getPrice());
        Image image = new Image(new ByteArrayInputStream(item.getImage()));
        imageView.setImage(image);
        
        if (item.is_complete == true){
        submitbtn.setText("Item Gifted !");
        submitbtn.setStyle("-fx-background-color: #39b35b;");
        }
    }
    @FXML
    public void handleRemoveItem() {
        if (item.is_complete == false){
        try {
            if (SceneController.showConfirmationAlert("Are you want to remove '"+item.getTitle()+"' item?")) {
                int user_id = SceneController.getSessionUser().getId();
                int item_id = item.getId();
                RemoveWishlistItemRequest request = new RemoveWishlistItemRequest(user_id, item_id);
                RemoveWishlistItemResponse response = (RemoveWishlistItemResponse) SocketController.sendRequest(request);
                if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
                SceneController.showSuccessAlert(response.getMessage());
                SceneController.loadMainScene();
                SceneController.switchScene("/iwish/views/MyWishlistView.fxml");
            }
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
   }
}
