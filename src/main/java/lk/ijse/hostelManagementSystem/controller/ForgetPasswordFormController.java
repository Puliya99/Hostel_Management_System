package lk.ijse.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.UserBo;
import lk.ijse.hostelManagementSystem.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForgetPasswordFormController implements Initializable {
    public JFXButton btnReset;
    public TextField txtUsername;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    private Boolean confirmPasswordVisible;

    private UserBo userBo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userBo = (UserBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.User);
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
//        String username = txtUsername.getText();
//        String password = txtPassword.getText();
//        String confirmPassword = txtConfirmPassword.getText();
//
//        if (!password.equals(confirmPassword)) {
//            new Alert(Alert.AlertType.WARNING, "Passwords do not match!").show();
//            return;
//        }
//
////        if (Regex.validateUsername(username)&& Regex.validatePassword(password)) {
//            try {
//                UserDTO user = userBo.view(dto.getUserName());
//                if (isUserVerified) {
//                    var user = new UserDTO(username, password);
//                    boolean isUpdated = forgotPasswordBo.updatePassword(new UserDTO(username, password));
//                    if (isUpdated) {
//                        new Alert(Alert.AlertType.INFORMATION, "Password reset successful!").show();
//                        Stage window = (Stage) root.getScene().getWindow();
//                        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
//                    } else {
//                        new Alert(Alert.AlertType.ERROR, "Oops something went wrong while updating password!").show();
//                    }
//                } else {
//                    new Alert(Alert.AlertType.WARNING, "User Not Verified!").show();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                new Alert(Alert.AlertType.ERROR, "Oops something wrong!").show();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }else {
//            new Alert(Alert.AlertType.INFORMATION, "Hint : [aA-zZ0-9@]{8,20}").show();
//        }
    }

    public void txtUsernameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        txtConfirmPassword.requestFocus();
    }

    public void txtConfirmPasswordOnAction(ActionEvent actionEvent) {
        btnReset.fire();
    }
}
