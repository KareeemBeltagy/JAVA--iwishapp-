package iwish.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import static iwish.models.objects.Items;
import static javafx.scene.control.TextFormatter.Change;

public class AddItemDialogController implements Initializable {
    @FXML
    private Label dialogTitleLabel;
    @FXML
    private TextField titleField;
    @FXML
    private TextField priceField;
    @FXML
    private Button submitButton;
    @FXML
    private TextField imageURLField;
    private byte[] imageBytes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialogTitleLabel.setText("Add new item");

        UnaryOperator<Change> filter = SceneController.getDoubleFilter();
        priceField.setTextFormatter(new TextFormatter<>(filter));

        submitButton.setText("Add");
    }

    @FXML
    private void handleSubmitButton() {
        try {
            String title = titleField.getText();
            double price = Double.parseDouble(priceField.getText());
            Items.addItem(title, price, imageBytes);
            SceneController.showSuccessAlert("Successfully add item.");
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleBrowseButton() {
        try {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(SceneController.getPrimaryStage());

            if (selectedFile == null) return;

            imageURLField.setText(selectedFile.getAbsolutePath());
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            imageBytes = new byte[(int) selectedFile.length()];
            fileInputStream.read(imageBytes);
            fileInputStream.close();
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

}
