package iwish.controllers;

import iwish.models.objects.Item;
import iwish.models.objects.Items;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Islam Awad, Kareem Mohamed, Malak Magdi, Sarah Salah
 */
public class ControlPanelController implements Initializable {

    @FXML
    public Text statusText;
    @FXML
    public TableView<Item> itemsTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        TableColumn<Item, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(64);

        TableColumn<Item, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(320);

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setCellFactory(param -> new TableCell<Item, Void>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    try {
                        Item item = getTableView().getItems().get(getIndex());
                        SceneController.showEditItemDialog(item);
                        loadItemsTableContent();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });


        TableColumn<Item, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(param -> new TableCell<Item, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    try {
                        Item item = getTableView().getItems().get(getIndex());
                        boolean confirmationResponse = SceneController.showConfirmationAlert("Are you sure you want to proceed with deleting '" + item.getTitle() + "' item?");
                        if (!confirmationResponse) return;
                        Items.deleteItem(item.getId());
                        SceneController.showSuccessAlert("Deleted '" + item.getTitle() + "' successfully!");
                        loadItemsTableContent();
                    } catch (Exception exception) {
                        SceneController.showErrorAlert(exception);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        itemsTableView.getColumns().addAll(idColumn, titleColumn, priceColumn, editColumn, deleteColumn);
    }

    @FXML
    private void handleAddItemButton() {
        try {
            if (!SocketController.isRunning()) throw new Exception("Please start the server first.");
            SceneController.showAddItemDialog();
            loadItemsTableContent();
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleStartButton() {
        try {
            SocketController.start();
            loadItemsTableContent();
            statusText.setText("Started");
            statusText.setFill(Color.GREEN);
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    @FXML
    private void handleStopButton() {
        try {
            SocketController.stop();
            itemsTableView.getItems().clear();
            statusText.setText("Stopped");
            statusText.setFill(Color.RED);
        } catch (Exception exception) {
            SceneController.showErrorAlert(exception);
        }
    }

    private void loadItemsTableContent() throws Exception {
        
        itemsTableView.getItems().clear();
        // hna ana bgeb el 7aga ml database 3an tare2 getitems eli hya manager w ba7ot el data f items list mn el manager
        List<Item> items = Items.getItems();
        // 2olt ll table view y3red el data eli gat ml DB
        itemsTableView.getItems().addAll(items);
    }
}
