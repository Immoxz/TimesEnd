package pl.immoxz.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("setTime.fxml"));
        /*BorderPane wholeScene = new BorderPane();

        VBox center =  FXMLLoader.load(getClass().getResource("centerOneScene.fxml"));
        HBox top = new HBox(FXMLLoader.load(getClass().getResource("topScene.fxml")))
                ,bot = new HBox(FXMLLoader.load(getClass().getResource("botScene.fxml")));

        wholeScene.setTop(top);
        wholeScene.setCenter(center);
        wholeScene.setBottom(bot);*/

        primaryStage.setTitle("Time is End");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
