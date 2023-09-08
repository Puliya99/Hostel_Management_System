package lk.ijse.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.ReservationBo;
import lk.ijse.hostelManagementSystem.bo.custom.RoomBO;
import lk.ijse.hostelManagementSystem.bo.custom.StudentBo;
import lk.ijse.hostelManagementSystem.dto.ReservationDTO;
import lk.ijse.hostelManagementSystem.dto.RoomDTO;
import lk.ijse.hostelManagementSystem.dto.StudentDTO;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddReservationFormController implements Initializable {
    public ComboBox<String> cmbRoomId;
    public ComboBox<String> cmbStdId;
    public Label txtRoomAvailableQty;
    public JFXDatePicker txtDate;
    public JFXButton btnPayNow;
    public JFXButton btnBook;
    public Label lblRoomPrice;
    public Label lblReservationId;
    public AnchorPane addReservation;
    private StudentBo studentBo;
    private RoomBO roomBo;
    private ReservationBo reservationBo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentBo = (StudentBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
        roomBo = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
        reservationBo = (ReservationBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

        setReservationId();
        setStudentList();
        setRoomList();
    }

    private void setReservationId() {
        lblReservationId.setText(reservationBo.getLastId());
    }

    private void setStudentList() {
        try {
            List<StudentDTO> studentDtoList = studentBo.getAll();
            ObservableList<String> studentIdObservableList = FXCollections.observableArrayList();
            studentDtoList.forEach(studentDto -> studentIdObservableList.add(studentDto.getStudent_id()));
            cmbStdId.setItems(studentIdObservableList);
        } catch (RuntimeException ignored) {
        }
    }

    private void setRoomList() {
        try {
            List<RoomDTO> roomDtoList = roomBo.getAll();
            ObservableList<String> roomIdObservableList = FXCollections.observableArrayList();
            roomDtoList.forEach(roomDto -> roomIdObservableList.add(roomDto.getRoom_type_id()));
            cmbRoomId.setItems(roomIdObservableList);
        } catch (RuntimeException ignored) {
        }
    }
    public void cmbRoomIdOnAction(ActionEvent actionEvent) {
        try {
            String selectedItem = cmbRoomId.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                RoomDTO room = roomBo.view(selectedItem);
                lblRoomPrice.setText(String.valueOf(room.getKey_money()));
                txtRoomAvailableQty.setText(String.valueOf(room.getQty()));

                if (room.getQty() != 0) {
                    btnPayNow.setDisable(false);
                    btnBook.setDisable(false);
                } else {
                    btnPayNow.setDisable(true);
                    btnBook.setDisable(true);
                    throw new RuntimeException("Room not available at the moment!");
                }
            } else {
                lblRoomPrice.setText("0");
                txtRoomAvailableQty.setText("0");
            }
        } catch (RuntimeException exception) {
            Stage stage = (Stage) addReservation.getScene().getWindow();
            stage.setAlwaysOnTop(false);
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
            stage.setAlwaysOnTop(false);
        }
    }

    public void cmbStudentIdOnAction(ActionEvent actionEvent) {
    }

    private void saveReservation(String status) {
        try {
            if (validateData()) {
                ReservationDTO reservationDto = new ReservationDTO();
                reservationDto.setRes_id(lblReservationId.getText());
                reservationDto.setStatus(status);
                reservationDto.setDate(Date.valueOf(txtDate.getValue()));

                StudentDTO studentDto = studentBo.view(cmbStdId.getValue());
                reservationDto.setStudentDto(studentDto);

                RoomDTO dto = roomBo.view(cmbRoomId.getValue());
                dto.setQty(dto.getQty() - 1);
                reservationDto.setRoomDto(dto);

                reservationBo.save(reservationDto);

                Stage stage = (Stage) addReservation.getScene().getWindow();
                stage.setAlwaysOnTop(false);
                new Alert(Alert.AlertType.INFORMATION, "Added!").showAndWait();
                stage.setAlwaysOnTop(true);
                stage.close();
            }
        } catch (RuntimeException exception) {
            Stage stage = (Stage) addReservation.getScene().getWindow();
            stage.setAlwaysOnTop(false);
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
            stage.setAlwaysOnTop(false);
        }
    }

    private boolean validateData() throws RuntimeException {
        if (cmbStdId.getSelectionModel().getSelectedItem() != null) {
            if (cmbRoomId.getSelectionModel().getSelectedItem() != null) {
                if (txtDate.getValue() != null) {
                    if (!txtDate.getValue().isBefore(LocalDate.now())) {
                        System.out.println("Validation Done");
                        return true;
                    }
                    throw new RuntimeException("Date must be after today");
                }
                throw new RuntimeException("Select Date");
            }
            throw new RuntimeException("Select Room");
        }
        throw new RuntimeException("Select Student");
    }

    public void btnPayNowOnAction(ActionEvent actionEvent) {
        saveReservation("paid");
    }

    public void btnBookOnAction(ActionEvent actionEvent) {
        saveReservation("un-paid");
    }

}
