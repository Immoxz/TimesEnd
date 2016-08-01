package pl.immoxz.main;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;

import javafx.scene.input.Clipboard;

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
    @FXML
    private Hyperlink mailLink = new Hyperlink();


    @FXML
    public void okButtonClicked() {
        System.out.println("ok button");
        if (finalTime >= 60) {
            System.out.println("zamyka za 60++");
            execCommand("shutdown /f /s /t " + this.finalTime);
        } else {
            if (confirmBox.displey("Wykryto brak wprowadzonego czasu zamknięcia", "Czy chcesz zamknąc system teraz?")) {
                alertBox.displey("Zamykanie Systemu", "Za 60 sekund system zostanie zamknięty!");
                execCommand("shutdown /f /s /t 60");
            }
        }
        System.out.println(this.finalTime);
    }

    @FXML
    public void onToggleButtonOn() {
        curTime.setText("0:0:0");
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
        curTime.setText("0:0:0");
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
        this.finalTime = 0;
        VBox center = null;
        try {
            center = FXMLLoader.load(getClass().getResource("/centerOneScene.fxml"));
            root.setCenter(null);
            root.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setMovieScene() {
        this.finalTime = 0;
        VBox center = null;
        try {
            center = FXMLLoader.load(getClass().getResource("/centerTwoScene.fxml"));
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
            if (isGoodIntOfTF(afterHouSet))
                H = Integer.parseInt(afterHouSet.getText());
        }
        if (!afterMinSet.getText().isEmpty()) {
            if (isGoodIntOfTF(afterMinSet))
                M = Integer.parseInt(afterMinSet.getText());
        }
        if (!afterSecSet.getText().isEmpty()) {
            if (isGoodIntOfTF(afterSecSet))
                S = Integer.parseInt(afterSecSet.getText());
        }
        curTime.setText(H + ":" + M + ":" + S);
        this.finalTime = betterTimeCounter.countTime(H, M, S);
    }

    protected boolean isGoodIntOfTF(TextField textField) {

        if (isInteger(textField.getText())) {
            textField.setStyle("-fx-background-color: lightskyblue;");
            return true;
        } else {
            textField.setStyle("-fx-background-color: red;");
            return false;
        }

    }

    protected static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    @FXML
    public void setOnTime() {
        String H = "", M = "", S = "";
        boolean[] status = {false, false, false};
        if (!hSet.getText().isEmpty()) {
            if (isGoodIntOfTF(hSet)) {
                H = hSet.getText();
                status[0] = true;
            }
        }
        if (!mSet.getText().isEmpty()) {
            if (isGoodIntOfTF(mSet)) {
                M = mSet.getText();
                status[1] = true;
            }
        }
        if (!sSet.getText().isEmpty()) {
            if (isGoodIntOfTF(sSet)) {
                S = sSet.getText();
                status[2] = true;
            }
        }
        if (areAllTrue(status)) {
            long[] differentsInTimeL = betterTimeCounter.countDiff(H, M, S);
            curTime.setText(differentsInTimeL[0] + ":" + differentsInTimeL[1] + ":" + differentsInTimeL[2]);
            this.finalTime = betterTimeCounter.countTime(differentsInTimeL[0], differentsInTimeL[1], differentsInTimeL[2]);
        }
    }


    public static boolean areAllTrue(boolean[] array)
    {
        for(boolean b : array) if(!b) return false;
        return true;
    }




    @FXML
    private void setPathToMovie() {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            filePathText.setText(file.getPath());
            long milisec = (long) propertiesReader.GetDuration(file.getPath());
            long[] Time = betterTimeCounter.CalculateTimeFromMilsec((milisec / 1000));
            System.out.println("H: " + Time[0] + " M: " + Time[1] + " S: " + Time[2]);
            curTime.setText(Time[0] + ":" + Time[1] + ":" + Time[2]);
            this.finalTime = betterTimeCounter.countTime(Time[0], Time[1], Time[2]);
        }

    }


    public void testRead() {
//        File file = fileChooser.showOpenDialog(stage);
//        if (file != null) {
        long milisec = (long) propertiesReader.GetDuration("C:/Projekts/svid.mp4");
        long[] Time = betterTimeCounter.CalculateTimeFromMilsec((milisec / 1000));
        System.out.println("H: " + Time[0] + " M: " + Time[1] + " S: " + Time[2]);

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

    public void onClickMailLink() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString("things.immo@gmail.com");
        clipboard.setContent(content);
        if (mailLink.getText().equals("Mail")) {
            mailLink.setText("things.immo@gmail.com");
        } else {
            mailLink.setText("things.immo@gmail.com [copied to clipboard]");
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
