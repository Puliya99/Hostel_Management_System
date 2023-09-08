package lk.ijse.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.StudentBo;
import lk.ijse.hostelManagementSystem.dto.StudentDTO;
import lk.ijse.hostelManagementSystem.tm.StudentTm;
import lk.ijse.hostelManagementSystem.util.regex.RegExFactory;
import lk.ijse.hostelManagementSystem.util.regex.RegExType;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentFormController implements Initializable {
    public TableView<StudentTm> tblStudents;
    public TableColumn<StudentTm, String> colStudentId;
    public TableColumn<StudentTm, String> colStudentName;
    public TableColumn<StudentTm, String> colAddress;
    public TableColumn<StudentTm, String> colContact;
    public TableColumn<StudentTm, Date> colDob;
    public TableColumn<StudentTm, String> colGender;
    public TextField txtId;
    public TextField txtName;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public TextField txtSearch;
    public TextField txtAddress;
    public TextField txtContact;
    public DatePicker cmbDob;
    public RadioButton rBtnMale;
    public ToggleGroup gender;
    public RadioButton rBtnFemale;
    private StudentBo studentBo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            studentBo = (StudentBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
            colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
            colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
            colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            refreshTable();
            txtSearch.textProperty().addListener((observableValue, previous, current) -> {
                if (!Objects.equals(previous, current)) {
                    tblStudents.getItems().clear();
                    List<StudentTm> collect = studentBo.searchStudentByText(current).stream().map(this::toStudentTm).collect(Collectors.toList());
                    tblStudents.setItems(FXCollections.observableArrayList(collect));
                }
            });
        }catch (RuntimeException exception){
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
    }

    private StudentTm toStudentTm(StudentDTO studentDto) {
        StudentTm studentTm = new StudentTm();
        studentTm.setStudent_id(studentDto.getStudent_id());
        studentTm.setName(studentDto.getName());
        studentTm.setDob((Date) studentDto.getDob());
        studentTm.setGender(studentDto.getGender());
        studentTm.setAddress(studentDto.getAddress());
        return studentTm;
    }

    public void tblStudentOnMouseClicked(MouseEvent mouseEvent) {
        StudentTm selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                btnAdd.setDisable(true);
                txtId.setText(selectedItem.getStudent_id());
                txtName.setText(selectedItem.getName());
                txtAddress.setText(selectedItem.getAddress());
                if (selectedItem.getGender().equals("Male")) {
                    rBtnMale.setSelected(true);
                } else {
                    rBtnFemale.setSelected(true);
                }
                txtContact.setText(selectedItem.getContact_no());
                cmbDob.setValue(selectedItem.getDob().toLocalDate());
            } else {
                btnUpdate.setDisable(true);
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            if (validation()) {
                StudentDTO studentDto = new StudentDTO();
                studentDto.setStudent_id(txtId.getText());
                studentDto.setName(txtName.getText());
                studentDto.setAddress(txtAddress.getText());
                studentDto.setGender((rBtnMale.isSelected()) ? "Male" : "Female");
                studentDto.setContact_no(txtContact.getText());
                studentDto.setDob(Date.valueOf(cmbDob.getValue()));
                studentBo.save(studentDto);
                new Alert(Alert.AlertType.INFORMATION, "Student Added").show();
                clearAll();
                refreshTable();
            } else {
                throw new RuntimeException("invalid input data in fields!");
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validation() {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtName.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.CITY).matcher(txtAddress.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.MOBILE).matcher(txtContact.getText()).matches() && cmbDob.getValue() != null;
    }

    private void clearAll() {
        txtContact.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.clear();
    }

    private void refreshTable() {
        try {
            generateStudentId();
            List<StudentDTO> all = studentBo.getAll();
            ObservableList<StudentTm> studentDtoObservableList = FXCollections.observableArrayList();
            all.forEach(dto -> studentDtoObservableList.add(new StudentTm(dto.getStudent_id(), dto.getName(), dto.getAddress(), dto.getContact_no(), (Date) dto.getDob(), dto.getGender())));
            tblStudents.setItems(studentDtoObservableList);
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            tblStudents.getItems().clear();
        }
    }

    private void generateStudentId() {
        txtId.setText(studentBo.getLastId());
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (validation()) {
                StudentDTO studentDto = new StudentDTO();

                studentDto.setStudent_id(txtId.getText());
                studentDto.setName(txtName.getText());
                studentDto.setAddress(txtAddress.getText());
                studentDto.setGender((rBtnMale.isSelected()) ? "Male" : "Female");
                studentDto.setContact_no(txtContact.getText());
                studentDto.setDob(Date.valueOf(cmbDob.getValue()));
                studentBo.update(studentDto);
                new Alert(Alert.AlertType.INFORMATION, "Student Updated").show();
                clearAll();
                refreshTable();
            } else {
                throw new RuntimeException("invalid input data in fields!");
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        btnUpdate.setDisable(true);
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        StudentTm selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                studentBo.delete(selectedItem.getStudent_id());
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted").show();
                refreshTable();
                clearAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Student first!").show();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        btnUpdate.setDisable(true);
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        if (RegExFactory.getInstance().getPattern(RegExType.STUDENT_ID).matcher(txtSearch.getText()).matches()) {
            StudentDTO studentDto = new StudentDTO();
            studentDto.setStudent_id(txtSearch.getText());
        }
    }

}
