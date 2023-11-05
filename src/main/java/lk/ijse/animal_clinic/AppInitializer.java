package lk.ijse.animal_clinic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class AppInitializer {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"))));
        stage.setTitle("Customer Form");
        stage.centerOnScreen();

        stage.show();
    }
}
