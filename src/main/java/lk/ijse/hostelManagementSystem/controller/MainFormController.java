package lk.ijse.hostelManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainFormController {

    @FXML
    private Button btnStart;

    @FXML
    private AnchorPane main;

    public void btnStartOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) main.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
    }
}
