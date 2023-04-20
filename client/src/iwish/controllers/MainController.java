package iwish.controllers;

import iwish.models.objects;
import iwish.models.objects.Notification;
import iwish.models.objects.User;
import iwish.models.requests.*;
import iwish.models.responses.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Islam Awad, Kareem Mohamed, Malak Magdi, Sarah Salah
 */
public class MainController implements Initializable {
    private final List<User> friendRequesters = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();

    @FXML
    private Pane contentPane;
    @FXML
    private HBox headBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadHeadBar();
            SceneController.setContentPane(contentPane);
            SceneController.switchScene("/iwish/views/HomeView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    public void loadHeadBar() throws Exception {
        if (SceneController.getSessionUser() == null) {
            Button createAccountButton = new Button("Create an account");
            createAccountButton.setAlignment(Pos.CENTER_RIGHT);
            createAccountButton.setStyle("-fx-background-color: #00000000;");
            createAccountButton.setTextFill(Color.WHITE);
            createAccountButton.setOnAction(event -> handleCreateAnAccountButton());

            Separator separator = new Separator();
            separator.setOrientation(Orientation.VERTICAL);
            separator.setPadding(new Insets(8.0));

            Button signInButton = new Button("Sign in");
            signInButton.setAlignment(Pos.CENTER_RIGHT);
            signInButton.setStyle("-fx-background-color: #00000000;");
            signInButton.setTextFill(Color.WHITE);
            signInButton.setOnAction(event -> handleSigninButton());

            headBar.getChildren().clear();
            headBar.getChildren().setAll(createAccountButton, separator, signInButton);
        } else {
            // User Menu
            MenuButton userMenuButton = new MenuButton(SceneController.getSessionUser().getName());
            userMenuButton.setStyle("-fx-background-color: #00000000;");
            userMenuButton.setTextFill(Color.WHITE);

            MenuItem wishlistMenuItem = new MenuItem("Wishlist");
            wishlistMenuItem.setOnAction(event -> handleWishlistButton());

            MenuItem friendsMenuItem = new MenuItem("Friends");
            friendsMenuItem.setOnAction(event -> handleFriendsButton());

            MenuItem logoutMenuItem = new MenuItem("Logout");
            logoutMenuItem.setOnAction(event -> handleLogoutButton());

            userMenuButton.getItems().addAll(wishlistMenuItem, friendsMenuItem, logoutMenuItem);

            // Friend Requests Menu
            headBar.getChildren().clear();

            User user = SceneController.getSessionUser();

            GetFriendRequestsRequest getFriendRequestsRequest = new GetFriendRequestsRequest(user.getId());
            GetFriendRequestsResponse getFriendRequestsResponse = (GetFriendRequestsResponse) SocketController.sendRequest(getFriendRequestsRequest);

            if (!getFriendRequestsResponse.isSuccess()) throw new RuntimeException(getFriendRequestsResponse.getMessage());
            friendRequesters.addAll(getFriendRequestsResponse.getRequesters());

            if (!friendRequesters.isEmpty()) {
                MenuButton friendMenuButton = new MenuButton("Friend Requests ("+friendRequesters.size()+")");
                friendMenuButton.setStyle("-fx-background-color: #00000000;");
                friendMenuButton.setTextFill(Color.WHITE);

                for (User friendRequester : friendRequesters) {
                    MenuItem menuItem = new MenuItem(friendRequester.getName());
                    menuItem.setOnAction(event -> handleFriendRequest(friendRequester));
                    friendMenuButton.getItems().add(menuItem);
                }

                headBar.getChildren().add(friendMenuButton);
            }

            GetNotificationsRequest getNotificationsRequest = new GetNotificationsRequest(user.getId());
            GetNotificationsResponse getNotificationsResponse = (GetNotificationsResponse) SocketController.sendRequest(getNotificationsRequest);

            if (!getNotificationsResponse.isSuccess()) throw new RuntimeException(getNotificationsResponse.getMessage());
            notifications.addAll(getNotificationsResponse.getNotifications());

            if (!notifications.isEmpty()) {
                MenuButton notificationsButton = new MenuButton("Notifications ("+notifications.size()+")");
                notificationsButton.setStyle("-fx-background-color: #00000000;");
                notificationsButton.setTextFill(Color.WHITE);

                for (Notification notification : notifications) {
                    MenuItem menuItem = new MenuItem(notification.getText());
                    notificationsButton.getItems().add(menuItem);
                }

                headBar.getChildren().add(notificationsButton);
            }

            headBar.getChildren().add(userMenuButton);
        }
    }

    @FXML
    private void handleFriendRequest(User friendRequester) {
        try {
            User user = SceneController.getSessionUser();

            if (SceneController.showConfirmationAlert("Would you like to accept "+friendRequester.getName()+" friend request?")) {
                AcceptFriendRequestRequest request = new AcceptFriendRequestRequest(user.getId(), friendRequester.getId());
                AcceptFriendRequestResponse response = (AcceptFriendRequestResponse) SocketController.sendRequest(request);

                if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
                else SceneController.showSuccessAlert(response.getMessage());
                SceneController.loadMainScene();
            } else {
                DeclineFriendRequestRequest request= new DeclineFriendRequestRequest(user.getId(), friendRequester.getId());
                DeclineFriendRequestResponse response = (DeclineFriendRequestResponse) SocketController.sendRequest(request);

                if (!response.isSuccess()) throw new RuntimeException(response.getMessage());
                else SceneController.showSuccessAlert(response.getMessage());
                SceneController.loadMainScene();
            }

        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleFriendsButton() {
        try {
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/FriendsView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleWishlistButton() {
        try {
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/MyWishlistView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleHomeButton() {
        try {
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/HomeView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handlePeopleButton() {
        try {
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/PeopleView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleCreateAnAccountButton() {
        try {
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/CreateAnAccountView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleSigninButton() {
        try {
            SceneController.loadMainScene();
            SceneController.switchScene("/iwish/views/SigninView.fxml");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleLogoutButton() {
        try {
            if (SceneController.showConfirmationAlert("Are you sure you want to logout?")) {
                SceneController.setSessionUser(null);
                SceneController.loadMainScene();
                SceneController.switchScene("/iwish/views/HomeView.fxml");
                loadHeadBar();
            }
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }
}
