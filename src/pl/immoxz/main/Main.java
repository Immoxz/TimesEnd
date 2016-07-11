package pl.immoxz.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("setTime.fxml"));

        VBox center = FXMLLoader.load(getClass().getResource("centerOneScene.fxml"));

        root.setCenter(center);

        primaryStage.setTitle("Time is End");
        primaryStage.setScene(new Scene(root, 600, 230));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
