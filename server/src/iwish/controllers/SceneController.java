package iwish.controllers;

import iwish.models.objects.Item;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static javafx.scene.control.TextFormatter.Change;

public class SceneController {
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        SceneController.primaryStage = primaryStage;
    }

    public static void loadMainScene() throws Exception {
        switchScene("/iwish/views/ControlPanelView.fxml");
    }

    public static void switchScene(String fxmlFilePath) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFilePath));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void showAddItemDialog() throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/ItemDialog.fxml"));
        AddItemDialogController controller = new AddItemDialogController();
        loader.setController(controller);
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(SceneController.primaryStage);
        dialogStage.setScene(new Scene(root));
        dialogStage.showAndWait();
    }


    public static void showEditItemDialog(Item item) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/iwish/views/ItemDialog.fxml"));
        EditItemDialogController controller = new EditItemDialogController();
        controller.setItem(item);
        loader.setController(controller);
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(SceneController.primaryStage);
        dialogStage.setScene(new Scene(root));
        dialogStage.showAndWait();
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

    public static UnaryOperator<Change> getDoubleFilter() {
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
