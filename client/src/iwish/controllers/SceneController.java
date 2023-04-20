package iwish.controllers;

import iwish.models.objects;
import iwish.models.objects.Item;
import iwish.models.objects.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SceneController {
    private static Stage primaryStage;
    private static Pane contentPane;
    private static User sessionUser;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        SceneController.primaryStage = primaryStage;
    }

    public static void setContentPane(Pane contentPane) {
        SceneController.contentPane = contentPane;
    }

    public static User getSessionUser() {
        return sessionUser;
    }

    public static void setSessionUser(User sessionUser) {
        SceneController.sessionUser = sessionUser;
    }

    public static void loadMainScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/MainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Parent getHomeItemCard(Item item) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/HomeItemCard.fxml"));
        HomeItemCardController controller = new HomeItemCardController();
        controller.setItem(item);
        loader.setController(controller);
        return loader.load();
    }

    public static Parent getMyWishlistItemCard(objects.WishListItem item) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/MyWishlistItemCard.fxml"));
        MyWishlistItemCardController controller = new MyWishlistItemCardController();
        controller.setItem(item);
        loader.setController(controller);
        return loader.load();
    }

    public static Parent getFriendWishlistItemCard(User friend, objects.WishListItem item) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/FriendWishlistItemCard.fxml"));
        FriendWishlistItemCardController controller = new FriendWishlistItemCardController();
        controller.setFriend(friend);
        controller.setItem(item);
        loader.setController(controller);
        return loader.load();
    }

    public static Parent getFriendCard(User friend) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/FriendCard.fxml"));
        FriendCardController controller = new FriendCardController();
        controller.setFriend(friend);
        loader.setController(controller);
        return loader.load();
    }

    public static Parent getPersonCard(User friend) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/PersonCard.fxml"));
        PersonCardController controller = new PersonCardController();
        controller.setFriend(friend);
        loader.setController(controller);
        return loader.load();
    }

    public static void switchScene(String fxmlFilePath) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFilePath));
        Parent root = loader.load();
        contentPane.getChildren().setAll(root);
    }

    public static void switchFriendWishListScene(User friend) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/FriendWishlist.fxml"));
        FriendWishlistController controller = new FriendWishlistController();
        controller.setFriend(friend);
        loader.setController(controller);
        Parent root = loader.load();
        contentPane.getChildren().setAll(root);
    }

    public static void showErrorAlert(Exception exception) {
        exception.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(exception.getMessage());
        alert.showAndWait();
    }

    public static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    public static void showContributeDialog(User friend, objects.WishListItem item) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/ContributeDialog.fxml"));
        ContributeDialogController controller = new ContributeDialogController();
        controller.setFriend(friend);
        controller.setItem(item);
        loader.setController(controller);
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(SceneController.primaryStage);
        dialogStage.setScene(new Scene(root));
        dialogStage.showAndWait();
        loadMainScene();
        switchFriendWishListScene(friend);
    }


    public static UnaryOperator<TextFormatter.Change> getDoubleFilter() {
        Pattern pattern = Pattern.compile("\\d*\\.?\\d*");

        return change -> {
            String newText = change.getControlNewText();
            if (pattern.matcher(newText).matches()) {
                return change;
            } else {
                return null;
            }
        };
    }
}
