package iwish.controllers;

import iwish.models.objects;
import iwish.models.objects.Item;
import iwish.models.requests;
import iwish.models.requests.RemoveWishlistItemRequest;
import iwish.models.responses;
import iwish.models.responses.RemoveWishlistItemResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static iwish.models.objects.*;
import javafx.scene.control.Button;

public class FriendWishlistItemCardController implements Initializable {
    @FXML
    public Label cardTitleLabel;
    @FXML
    public Label cardPriceLabel;
    @FXML
    public ImageView imageView;
    @FXML
    private WishListItem item;
    @FXML
    private User friend;
@FXML 
    public Button submitbtn;

    public void setItem(WishListItem item) {
        this.item = item;
    }

    public void setFriend(User friend) {
        this.friend = friend;
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
    public void handleContributeButton() {
        if (item.is_complete == false){
        try {
            SceneController.showContributeDialog(friend, item);
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
  }
}
    
