package iwish.controllers;

import iwish.models.objects.Item;
import iwish.models.requests.FindItemsRequest;
import iwish.models.responses.FindItemsResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static iwish.models.objects.User;

public class HomeController implements Initializable {
    private final List<Parent> itemCards = new ArrayList<>();
    @FXML
    public TextField searchField;
    @FXML
    private FlowPane itemsFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // btkon fadya fl awel 3ashan tgeb kol 7aga awel ma afta7 el home
            loadItems("");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
            System.exit(1);
        }
    }

    // load item bta5od search query
    private void loadItems(String searchQuery) throws Exception {
        //To prevent redundancy while searching
        itemCards.clear();
        
        // we made a request 'war2a fadya"
        FindItemsRequest request;
        
       // sessionuser .. the person using the app
        User user = SceneController.getSessionUser();

        
        if (user == null)
            request = new FindItemsRequest(searchQuery);
        else
           // bamla el data bta3t el request " 3ayz el id w da mawgod mn el scene controller byzhar awel ma 23mel login w search query 
            
        { // go to client request .. check the constructor 
            request = new FindItemsRequest(user.getId(), searchQuery);
        }

        // 3malna response fady da el cntainer eli hastlem feh 
        FindItemsResponse response;
       
       // wl container el fady ha7ot feh el response eli haygely mn el socketcontroller.sendrequest w de method gowa el socket controller bta3 el client
      // send request da byraga3 object w anamsh 3ayza keda f bastana response mn no3 finditemrespone eli ana mstnyah
      // ro7 3la el indItemsResponse fl response
      response = (FindItemsResponse) SocketController.sendRequest(request);
       
       
       
       if (response.isSuccess() == true) {
            List<Item> items = response.getItems();
            
            for (Item item : items) {
                Parent itemCard = SceneController.getHomeItemCard(item);
                itemCards.add(itemCard);
            }

            itemsFlowPane.getChildren().setAll(itemCards);
       } else {
                  throw new RuntimeException(response.getMessage());
       }

    }

    @FXML
    private void handleSearchButton() {
        try {
            String q = searchField.getText();
            loadItems(q);
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
