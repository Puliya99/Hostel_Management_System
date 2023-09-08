package lk.ijse.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.RoomBO;
import lk.ijse.hostelManagementSystem.dto.RoomDTO;
import lk.ijse.hostelManagementSystem.tm.RoomTm;
import lk.ijse.hostelManagementSystem.util.regex.RegExFactory;
import lk.ijse.hostelManagementSystem.util.regex.RegExType;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomFormController implements Initializable {

    public TableView<RoomTm> tblRooms;
    public TableColumn<RoomTm, String> colRoomTypeId;
    public TableColumn<RoomTm, String> colRoomType;
    public TableColumn<RoomTm, Double> colKeyMoney;
    public TableColumn<RoomTm, Integer> colQty;
    public TextField txtId;
    public TextField txtType;
    public TextField txtKeyMoney;
    public TextField txtQty;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public TextField txtSearch;
    private RoomBO roomBO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);

        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        refreshTable();

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void tblRoomsOnMouseClicked(MouseEvent mouseEvent) {
        RoomTm selectedItem = tblRooms.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            txtId.setText(selectedItem.getRoom_type_id());
            txtType.setText(selectedItem.getType());
            txtQty.setText(String.valueOf(selectedItem.getQty()));
            txtKeyMoney.setText(String.valueOf(selectedItem.getKey_money()));
        } else {
            btnAdd.setDisable(false);
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            if (validateData()) {
            RoomDTO roomDto = new RoomDTO();
            roomDto.setRoom_type_id(txtId.getText());
            roomDto.setType(txtType.getText());
            roomDto.setKey_money(Double.valueOf(txtKeyMoney.getText()));
            roomDto.setQty(Integer.valueOf(txtQty.getText()));
            roomBO.save(roomDto);

            new Alert(Alert.AlertType.INFORMATION, "Room Added").show();

            refreshTable();
            clearAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
            }
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            clearAll();
        }
    }

    private boolean validateData() {
        return RegExFactory.getInstance().getPattern(RegExType.DOUBLE).matcher(txtKeyMoney.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtType.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.INTEGER).matcher(txtQty.getText()).matches();
    }

    private void refreshTable() {
        try {
            txtId.setText(roomBO.getLastId());
            List<RoomDTO> all = roomBO.getAll();
            ObservableList<RoomTm> roomObservableList = FXCollections.observableArrayList();
            all.forEach(dto -> roomObservableList.add(new RoomTm(dto.getRoom_type_id(), dto.getType(), dto.getKey_money(), dto.getQty())));
            tblRooms.setItems(roomObservableList);
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            tblRooms.getItems().clear();
        }
    }

    private void clearAll() {
        txtQty.clear();
        txtType.clear();
        txtKeyMoney.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (tblRooms.getSelectionModel().getSelectedItem() != null) {
                if (validateData()) {
                    RoomDTO roomDto = new RoomDTO();
                    roomDto.setRoom_type_id(txtId.getText());
                    roomDto.setType(txtType.getText());
                    roomDto.setKey_money(Double.valueOf(txtKeyMoney.getText()));
                    roomDto.setQty(Integer.valueOf(txtQty.getText()));

                    roomBO.update(roomDto);
                    new Alert(Alert.AlertType.INFORMATION, "Room Updated").show();

                    refreshTable();
                    clearAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
                }
            } else {
                throw new RuntimeException("Select Room First");
            }
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        RoomTm selectedItem = tblRooms.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                roomBO.delete(selectedItem.getRoom_type_id());
                new Alert(Alert.AlertType.INFORMATION, "Room Deleted").show();
                refreshTable();
                clearAll();
            } else {
                throw new RuntimeException("Select Room First");
            }
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            clearAll();
        }
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

}
