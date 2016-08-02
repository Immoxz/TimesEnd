package pl.immoxz.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by AdminIT on 2016-07-08.
 */
public class AlertBox {
    public void displey(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinHeight(150);
        window.setMinWidth(300);
        boolean answer;

        Label label = new Label(message);
        Button okButton = new Button("OK");

        okButton.setOnAction(event -> {
            window.close();
        });

        //ustaiwnaie sceny
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().addAll(label,okButton);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        //wyswietli i zatrzyma tutaj uzytkownika! ważne
        window.showAndWait();
    }
}
