package iwish.controllers;

import iwish.models.objects;
import iwish.models.objects.Item;
import iwish.models.objects.User;
import iwish.models.requests;
import iwish.models.requests.ContributeRequest;
import iwish.models.responses;
import iwish.models.responses.ContributeResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class ContributeDialogController implements Initializable {
    @FXML
    private TextField itemField;
    @FXML
    private TextField friendField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField amountField;

    private objects.WishListItem item;
    private User friend;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UnaryOperator<TextFormatter.Change> filter = SceneController.getDoubleFilter();
        amountField.setTextFormatter(new TextFormatter<>(filter));

        itemField.setText(item.getTitle());
        friendField.setText(friend.getName());
        priceField.setText(""+item.getPrice());
    }

    @FXML
    private void handleSendButton() {
        try {
            int contributor_id = SceneController.getSessionUser().getId();
            int receiver_id = friend.getId();
            int item_id = item.getId();
            double amount = Double.parseDouble(amountField.getText());

            ContributeRequest request = new ContributeRequest(contributor_id, receiver_id, item_id, amount);
            ContributeResponse response = (ContributeResponse) SocketController.sendRequest(request);
            if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
            SceneController.showSuccessAlert(response.getMessage());
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    public objects.WishListItem getItem() {
        return item;
    }

    public void setItem(objects.WishListItem item) {
        this.item = item;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
