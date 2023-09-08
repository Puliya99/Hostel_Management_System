package lk.ijse.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.UserBo;
import lk.ijse.hostelManagementSystem.dto.UserDTO;
import lk.ijse.hostelManagementSystem.util.regex.RegExFactory;
import lk.ijse.hostelManagementSystem.util.regex.RegExType;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {

    public JFXButton btnRegister;
    public TextField txtUsername;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public AnchorPane sign;
    private UserBo userBo;
    private RegExFactory regExFactory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userBo= (UserBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.User);
        regExFactory = RegExFactory.getInstance();
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        Stage window = (Stage) sign.getScene().getWindow();
        try {
            window.setAlwaysOnTop(false);
            if (checkRegEx()) {
                UserDTO userDto = new UserDTO(txtUsername.getText(), txtPassword.getText());
                userBo.save(userDto);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration Success! ");
                alert.showAndWait();
                clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Pattern not match!").showAndWait();
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
        window.setAlwaysOnTop(true);
    }

    private boolean checkRegEx() throws RuntimeException {
        return regExFactory.getPattern(RegExType.NAME).matcher(txtUsername.getText()).matches() && regExFactory.getPattern(RegExType.PASSWORD).matcher(txtPassword.getText()).matches() && txtPassword.getText().equals(txtConfirmPassword.getText());
    }

    private void clear() {
        txtUsername.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
    }

    public void txtUsernameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        txtConfirmPassword.requestFocus();
    }

    public void txtConfirmPasswordOnAction(ActionEvent actionEvent) {
        btnRegister.fire();
    }

}
