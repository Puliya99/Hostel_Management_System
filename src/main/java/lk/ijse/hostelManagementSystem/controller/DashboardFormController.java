package lk.ijse.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    public AnchorPane sidePane;
    public JFXButton btnLogout;
    public JFXButton btnStudents;
    public JFXButton btnRooms;
    public JFXButton btnReservation;
    public JFXButton btnUser;
    public Label lblTime;
    public AnchorPane dash;
    public AnchorPane dashBoard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> lblTime.setText("" + new SimpleDateFormat("EEEE - MMM-dd-yyyy  HH:mm:ss").format(Calendar.getInstance().getTime()))), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage)  dash.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
    }

    public void btnStudentsOnAction(ActionEvent actionEvent) throws IOException {
        dashBoard.getChildren().clear();
        dashBoard.getChildren().add(FXMLLoader.load(getClass().getResource("/view/student_form.fxml")));
    }

    public void btnRoomsOnAction(ActionEvent actionEvent) throws IOException {
        dashBoard.getChildren().clear();
        dashBoard.getChildren().add(FXMLLoader.load(getClass().getResource("/view/room_form.fxml")));
    }

    public void btnReservationOnAction(ActionEvent actionEvent) throws IOException {
        dashBoard.getChildren().clear();
        dashBoard.getChildren().add(FXMLLoader.load(getClass().getResource("/view/reservationForm.fxml")));
    }

    public void btnUserOnAction(ActionEvent actionEvent) throws IOException {
        dashBoard.getChildren().clear();
        dashBoard.getChildren().add(FXMLLoader.load(getClass().getResource("/view/user_form.fxml")));
    }
}
