package lk.ijse.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.UserBo;
import lk.ijse.hostelManagementSystem.dto.UserDTO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {
    public TableView <UserDTO> tblUsers;
    public TableColumn <UserDTO,String> colId;
    public TableColumn <UserDTO,String> colPassword;
    public TextField txtId;
    public TextField txtPassword;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    public AnchorPane User;
    private UserBo userBo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userBo = (UserBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.User);

        colId.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        refreshTable();
    }

    public void tblUsersOnMouseClicked(MouseEvent mouseEvent) {
        UserDTO selectedItem = tblUsers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            txtId.setText(selectedItem.getUserName());
            txtPassword.setText(selectedItem.getPassword());
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        User.setDisable(true);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.requestFocus();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/signUp_form.fxml"))));
        stage.setTitle("Sign-up");
        stage.showAndWait();
        refreshTable();
        User.setFocusTraversable(true);
        User.setDisable(false);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            UserDTO userDto = new UserDTO();
            userDto.setUserName(txtId.getText());
            userDto.setPassword(txtPassword.getText());
            userBo.update(userDto);
            new Alert(Alert.AlertType.INFORMATION, "User Updated").showAndWait();
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
        tblUsers.getItems().clear();
        btnAdd.setDisable(false);
        refreshTable();
        clear();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            UserDTO selectedItem = tblUsers.getSelectionModel().getSelectedItem();
            userBo.delete(selectedItem.getUserName());
            new Alert(Alert.AlertType.ERROR, "User Deleted").showAndWait();
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
        tblUsers.getItems().clear();
        btnAdd.setDisable(false);
        refreshTable();
        clear();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void refreshTable() {
        try {
            List<UserDTO> allUsers = userBo.getAll();
            ObservableList<UserDTO> observableList = FXCollections.observableArrayList();
            observableList.addAll(allUsers);
            tblUsers.setItems(observableList);
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clear(){
        txtId.clear();
        txtPassword.clear();
    }

}
