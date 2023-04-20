package iwish.controllers;

import iwish.models.objects;
import iwish.models.objects.User;
import iwish.models.requests;
import iwish.models.requests.FindPeopleRequest;
import iwish.models.responses;
import iwish.models.responses.FindPeopleResponse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PeopleController implements Initializable {
    private final List<Parent> peopleCards = new ArrayList<>();
    @FXML
    public TextField searchField;
    @FXML
    public FlowPane peopleFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadItems("");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
            System.exit(1);
        }
    }

    private void loadItems(String searchQuery) throws Exception {
        peopleCards.clear();

        FindPeopleRequest request;
        User user = SceneController.getSessionUser();

        if (user == null) {
            request = new FindPeopleRequest(searchQuery);
        } else
            request = new FindPeopleRequest(user.getId(), searchQuery);

        FindPeopleResponse response = (FindPeopleResponse) SocketController.sendRequest(request);
        if (!response.isSuccess()) throw new RuntimeException(response.getMessage());

        for (User person : response.getPeople()) {
            Parent personCard = SceneController.getPersonCard(person);
            peopleCards.add(personCard);
        }

        peopleFlowPane.getChildren().setAll(peopleCards);
    }

    @FXML
    private void handleSearchButton() {
        try {
            loadItems(searchField.getText());
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

}
