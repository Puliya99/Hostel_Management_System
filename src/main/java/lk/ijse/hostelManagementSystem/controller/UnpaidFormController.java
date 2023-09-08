package lk.ijse.hostelManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.StudentBo;
import lk.ijse.hostelManagementSystem.dto.StudentDTO;
import lk.ijse.hostelManagementSystem.tm.StudentTm;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UnpaidFormController implements Initializable {
    public TableView<StudentTm> tblStudents;
    public TableColumn<StudentTm, String> colStdId;
    public TableColumn<StudentTm, String> colStdName;
    public TableColumn<StudentTm, String> colStdAddress;
    public TableColumn<StudentTm, String> colMobile;
    public AnchorPane unPaid;
    private StudentBo studentBo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentBo = (StudentBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

        colStdId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colStdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        colStdAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        refreshUnpaidStudentTable();
    }

    private void refreshUnpaidStudentTable() {
        try {
            List<StudentDTO> list = studentBo.getUnpaidStudents();
            ObservableList<StudentTm> stdTmList = FXCollections.observableArrayList();
            for (StudentDTO ele : list) {
                StudentTm studentTm = new StudentTm();
                studentTm.setStudent_id(ele.getStudent_id());
                studentTm.setName(ele.getName());
                studentTm.setAddress(ele.getAddress());
                studentTm.setContact_no(ele.getContact_no());
                stdTmList.add(studentTm);
            }
            tblStudents.setItems(stdTmList);
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.INFORMATION, exception.getMessage()).show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        unPaid.getChildren().clear();
        unPaid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/reservationForm.fxml")));
    }

}
