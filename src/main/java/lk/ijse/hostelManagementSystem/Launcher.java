package lk.ijse.hostelManagementSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("/view/main_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Fake Hostel");
        stage.setScene(scene);
        stage.show();
    }
}
