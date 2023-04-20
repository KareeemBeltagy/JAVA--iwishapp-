package iwish.controllers;

import iwish.models.objects.Item;
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

public class EditItemDialogController implements Initializable {
    private Item item;
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
        dialogTitleLabel.setText("Edit '" + item.getTitle() + "' item");

        titleField.setText(item.getTitle());

        UnaryOperator<Change> filter = SceneController.getDoubleFilter();
        priceField.setTextFormatter(new TextFormatter<>(filter));
        priceField.setText("" + item.getPrice());

        submitButton.setText("Save");
    }

    @FXML
    private void handleSubmitButton() {
        try {
            int id = item.getId();
            String title = titleField.getText();
            double price = Double.parseDouble(priceField.getText());
            Items.editItem(id, title, price, imageBytes);
            SceneController.showSuccessAlert("Successfully modified item.");
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
