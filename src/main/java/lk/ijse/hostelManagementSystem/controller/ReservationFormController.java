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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.ReservationBo;
import lk.ijse.hostelManagementSystem.bo.custom.RoomBO;
import lk.ijse.hostelManagementSystem.dto.ReservationDTO;
import lk.ijse.hostelManagementSystem.dto.RoomDTO;
import lk.ijse.hostelManagementSystem.tm.ReservationTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReservationFormController implements Initializable {
    public TableView<ReservationTm> tblReservations;
    public TableColumn <ReservationTm, String>colReservationId;
    public TableColumn<ReservationTm, String> colStudentId;
    public TableColumn<ReservationTm, Date> colDate;
    public TableColumn<ReservationTm, String> colRoomType;
    public TableColumn<ReservationTm, String> colStatus;
    public TableView tblRoom;
    public TableColumn colRoomID;
    public TableColumn colType;
    public TableColumn colKeyMoney;
    public TableColumn colQty;
    public JFXButton btnViewUnpaidStudent;
    public JFXButton btnAddReservation;
    public JFXButton btnMarkAsPaid;
    public JFXButton btnDelete;
    public AnchorPane reservations;
    private RoomBO roomBO;
    private ReservationBo reservationBo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
            reservationBo = (ReservationBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

            colRoomID.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

            colReservationId.setCellValueFactory(new PropertyValueFactory<>("res_id"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
            colRoomType.setCellValueFactory(new PropertyValueFactory<>("room_id"));

            refreshRoomTable();
            refreshReservationTable();
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
    }
    public void tblReservationsOnMouseClicked(MouseEvent mouseEvent) {
        ReservationTm reservationTm = tblReservations.getSelectionModel().getSelectedItem();
        if (reservationTm != null) btnDelete.setDisable(false);
    }

    public void btnViewUnpaidStudentOnAction(ActionEvent actionEvent) throws IOException {
        reservations.getChildren().clear();
        reservations.getChildren().add(FXMLLoader.load(getClass().getResource("/view/unpaid_form.fxml")));
    }

    public void btnAddReservationOnAction(ActionEvent actionEvent) throws IOException {
        try {
        reservations.setDisable(true);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.requestFocus();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add_reservation_form.fxml"))));
        stage.setTitle("Booking");
        stage.showAndWait();
        reservations.setDisable(false);
            refreshRoomTable();
            refreshReservationTable();
        }catch (RuntimeException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage()).show();
        }
    }

    private void refreshRoomTable() throws RuntimeException {
        List<RoomDTO> all = roomBO.getAll();
        ObservableList<RoomDTO> roomObservableList = FXCollections.observableArrayList();
        roomObservableList.addAll(all);
        tblRoom.setItems(roomObservableList);
    }

    private void refreshReservationTable() throws RuntimeException {
        List<ReservationDTO> all = reservationBo.getAll();
        ObservableList<ReservationTm> observableList = FXCollections.observableArrayList();
        all.stream().map(dto -> observableList.add(new ReservationTm(dto.getRes_id(), dto.getDate(), dto.getStatus(), dto.getStudentDto().getStudent_id(), dto.getRoomDto().getRoom_type_id()))).collect(Collectors.toList());
        tblReservations.setItems(observableList);
    }

    public void btnMarkAsPaidOnAction(ActionEvent actionEvent) {
        try {
            ReservationTm selectedItem = tblReservations.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                ReservationDTO dto = reservationBo.view(selectedItem.getRes_id());
                dto.setStatus("paid");
                reservationBo.update(dto);
                new Alert(Alert.AlertType.INFORMATION, "Payment updated").show();
                refreshReservationTable();
                refreshRoomTable();
            }
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            ReservationTm reservationTm = tblReservations.getSelectionModel().getSelectedItem();
            if (reservationTm != null) {
                btnDelete.setDisable(false);
                reservationBo.delete(reservationTm.getRes_id());
                new Alert(Alert.AlertType.ERROR, "Reservation Deleted : " + reservationTm.getRes_id()).show();
                refreshRoomTable();
                refreshReservationTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Item First").show();
            }
            btnDelete.setDisable(true);
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
    }
}
