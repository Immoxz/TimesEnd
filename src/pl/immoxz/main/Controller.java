package pl.immoxz.main;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    BetterTimeCounter betterTimeCounter = new BetterTimeCounter();

    long finalTime = 0;
    private ConfirmBox confirmBox = new ConfirmBox();
    private AlertBox alertBox = new AlertBox();
    private PropertiesReader propertiesReader = new PropertiesReader();

    final FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();
    private Stage stage = new Stage();


    @FXML
    private TextField afterHouSet, afterMinSet, afterSecSet, hSet = new TextField(), mSet = new TextField(), sSet = new TextField(), filePathText = new TextField();
    @FXML
    private Label curTime = new Label(), curBotTime = new Label();
    @FXML
    private RadioButton toggleOnTime = new RadioButton(), toggleAfterTime = new RadioButton();
    @FXML
    private Button onTimeButton = new Button(), buttonAfterTime = new Button(), setPathButton;
    @FXML
    private BorderPane root = new BorderPane();
    @FXML
    private VBox movieVbox, mainVbox;


    //TODO stwoprzyc alertbox na elsa gdy nie ustawiono godziny
    @FXML
    public void okButtonClicked() {
        System.out.println("ok button");
        if (finalTime >= 60) {
            System.out.println("zamyka za 60++");
//            execCommand("shutdown /f /s /t " + this.finalTime);
        } else {
            if (confirmBox.displey("Wykryto brak wprowadzonego czasu zamknięcia", "Czy chcesz zamknąc system teraz?")) {
                alertBox.displey("Zamykanie Systemu", "Za 60 sekund system zostanie zamknięty!");
//                execCommand("shutdown /f /s /t " + 60);
            }
        }
        System.out.println(this.finalTime);
    }

    @FXML
    public void onToggleButtonOn() {
        hSet.setDisable(true);
        mSet.setDisable(true);
        sSet.setDisable(true);
        onTimeButton.setDisable(true);
        afterHouSet.setDisable(false);
        afterMinSet.setDisable(false);
        afterSecSet.setDisable(false);
        buttonAfterTime.setDisable(false);
    }

    @FXML
    public void onToggleButtonOff() {
        afterHouSet.setDisable(true);
        afterMinSet.setDisable(true);
        afterSecSet.setDisable(true);
        buttonAfterTime.setDisable(true);

        hSet.setText(Integer.toString(1 + (Integer.parseInt(betterTimeCounter.takeCurrentHour()))));
        mSet.setText(betterTimeCounter.takeCurrentMinute());
        sSet.setText(betterTimeCounter.takeCurrentSec());

        hSet.setDisable(false);
        mSet.setDisable(false);
        sSet.setDisable(false);
        onTimeButton.setDisable(false);
    }

    @FXML
    public void cancelButtonClicked() {
        System.out.println("cancel button");
        execCommand("shutdown -a");
    }

    @FXML
    private void setTimeScene() {
        VBox center = null;
        try {
            center = FXMLLoader.load(getClass().getResource("centerOneScene.fxml"));
            root.setCenter(null);
            root.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setMovieScene() {
        VBox center = null;
        try {
            center = FXMLLoader.load(getClass().getResource("centerTwoScene.fxml"));
            root.setCenter(null);
            root.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void setTime() {
        int H = 0, M = 0, S = 0;
        if (!afterHouSet.getText().isEmpty()) {
            H = Integer.parseInt(afterHouSet.getText());
        }
        if (!afterMinSet.getText().isEmpty()) {
            M = Integer.parseInt(afterMinSet.getText());
        }
        if (!afterSecSet.getText().isEmpty()) {
            S = Integer.parseInt(afterSecSet.getText());
        }
        curTime.setText(H + ":" + M + ":" + S);
        this.finalTime = betterTimeCounter.countTime(H, M, S);
    }

    @FXML
    public void setOnTime() {
        long[] differentsInTimeL = betterTimeCounter.countDiff(hSet.getText(), mSet.getText(), sSet.getText());
        curTime.setText(differentsInTimeL[0] + ":" + differentsInTimeL[1] + ":" + differentsInTimeL[2]);
        this.finalTime = betterTimeCounter.countTime(differentsInTimeL[0], differentsInTimeL[1], differentsInTimeL[2]);
    }


    @FXML
    private void setPathToMovie() {

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            filePathText.setText(file.getPath());
        }

    }


    public void testRead() {
//        File file = fileChooser.showOpenDialog(stage);
//        if (file != null) {
            propertiesReader.VideoInfo("C:/Projekts/svid.mp4");

//            openFile(file);
//        }

        }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


    public void execCommand(String cmd) {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", cmd);
        builder.redirectErrorStream(true);
        Process p = null;
        try {
            p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleAfterTime.setSelected(true);
        curBotTime.setText(betterTimeCounter.takeCurrentTime());
        hSet.setText(Integer.toString(1 + (Integer.parseInt(betterTimeCounter.takeCurrentHour()))));
        mSet.setText(betterTimeCounter.takeCurrentMinute());
        sSet.setText(betterTimeCounter.takeCurrentSec());
        curTime.setText("0:0:0");
        if (!toggleOnTime.isSelected()) {
            hSet.setDisable(true);
            mSet.setDisable(true);
            sSet.setDisable(true);
            onTimeButton.setDisable(true);
        }

    }
}
