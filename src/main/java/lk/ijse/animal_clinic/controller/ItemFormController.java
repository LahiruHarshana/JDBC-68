package lk.ijse.animal_clinic.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.animal_clinic.dto.ItemDto;
import lk.ijse.animal_clinic.dto.tm.ItemTm;
import lk.ijse.animal_clinic.model.ItemModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ItemFormController {
    @FXML
    private TableView<ItemTm> tblItem;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    private final ItemModel itemModel = new ItemModel();

    public void initialize() {
        setCellValueFactory();
        loadAllItems();
        setListener();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadAllItems() {
        try {
            List<ItemDto> dtoList = itemModel.loadAllItems();

            ObservableList<ItemTm> obList = FXCollections.observableArrayList();

            for(ItemDto dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblItem.getSelectionModel().getSelectedIndex();
                        String code = (String) colCode.getCellData(selectedIndex);

                        deleteItem(code);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tblItem.refresh();
                    }
                });

                var tm = new ItemTm(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQtyOnHand(),
                        btn
                );
                obList.add(tm);
            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String code) {
        try {
            boolean isDeleted = itemModel.deleteItem(code);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        var itemDto = new ItemDto(code, description, unitPrice, qtyOnHand);

        try {
            boolean isSaved = itemModel.saveItem(itemDto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "item saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String code = txtCode.getText();

        try {
            boolean isDeleted = itemModel.deleteItem(code);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

//        var model = new ItemModel();
        try {
            boolean isUpdated = itemModel.updateItem(new ItemDto(code, description, unitPrice, qtyOnHand));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "item updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void codeSearchOnAction(ActionEvent event) {
        String code = txtCode.getText();

        try {
            ItemDto dto = itemModel.searchItem(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "item not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void setListener() {
        tblItem.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new ItemDto(
                            newValue.getCode(),
                            newValue.getDescription(),
                            newValue.getUnitPrice(),
                            newValue.getQtyOnHand()
                    );
                    setFields(dto);
                });
    }

    private void setFields(ItemDto dto) {
        txtCode.setText(dto.getCode());
        txtDescription.setText(dto.getDescription());
        txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
    }

    private void clearFields() {
        txtCode.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }
}
