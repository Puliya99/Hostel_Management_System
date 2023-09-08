package lk.ijse.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.hostelManagementSystem.bo.BOFactory;
import lk.ijse.hostelManagementSystem.bo.custom.UserBo;
import lk.ijse.hostelManagementSystem.dto.UserDTO;
import lk.ijse.hostelManagementSystem.util.regex.RegExFactory;
import lk.ijse.hostelManagementSystem.util.regex.RegExType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormConttoller implements Initializable {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public JFXButton btnLogin;
    public JFXButton btnSignUp;
    public RadioButton btnShowPassword;
    public Label lblPassword;
    public AnchorPane login;
    private UserBo userBo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userBo = (UserBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.User);
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        btnLogin.fire();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (checkRegEx()) {
            try {
                UserDTO dto = new UserDTO(txtUserName.getText(), txtPassword.getText());
                UserDTO user = userBo.view(dto.getUserName());
                if (user.getUserName().equals(txtUserName.getText()) && user.getPassword().equals(txtPassword.getText())) {
                    Stage window = (Stage) login.getScene().getWindow();
                    window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
                }
            } catch (RuntimeException | IOException exception) {
                new Alert(Alert.AlertType.INFORMATION, exception.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Invalid Input!").show();
        }
    }

    private boolean checkRegEx() throws RuntimeException {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtUserName.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.PASSWORD).matcher(txtPassword.getText()).matches();
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) throws IOException {
        login.setFocusTraversable(false);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();
        stage.requestFocus();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/signUp_form.fxml"))));
        stage.setTitle("Signup");
        stage.showAndWait();
        login.setFocusTraversable(true);
        login.setDisable(false);
    }

    public void btnForgetOnAction(ActionEvent actionEvent) throws IOException {
        login.setFocusTraversable(false);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();
        stage.requestFocus();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/forget_Password_form.fxml"))));
        stage.setTitle("Signup");
        stage.showAndWait();
        login.setFocusTraversable(true);
        login.setDisable(false);
    }

    public void BtnOnMouseClicked(MouseEvent mouseEvent) {
        lblPassword.setText(txtPassword.getText());
        lblPassword.setVisible(btnShowPassword.isSelected());
    }
}
