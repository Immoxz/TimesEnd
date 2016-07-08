package pl.immoxz.main;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    BetterTimeCounter betterTimeCounter = new BetterTimeCounter();

    int finalTime = 0;
    ConfirmBox confirmBox = new ConfirmBox();
    AlertBox alertBox = new AlertBox();

    @FXML
    private TextField afterHouSet, afterMinSet, afterSecSet, hSet, mSet, sSet;
    @FXML
    private Label curTime, curBotTime;
    @FXML
    private RadioButton toggleOnTime = new RadioButton(), toggleAfterTime = new RadioButton();
    @FXML
    private Button onTimeButton, buttonAfterTime;


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
    public void setTime() {
        int H = 0, M = 0, S = 0;
        if (!afterHouSet.getText().isEmpty()) {
            H = Integer.parseInt(afterHouSet.getText());
        }
        if (!afterMinSet.getText().isEmpty()) {
            M = Integer.parseInt(afterMinSet.getText());
        }
        if (!afterSecSet.getText().isEmpty()) {
            H = Integer.parseInt(afterSecSet.getText());
        }
        System.out.println(afterHouSet.getText() + " " + afterMinSet.getText() + " " + afterSecSet.getText());
        System.out.println(H + " " + M + " " + S);
        this.finalTime = betterTimeCounter.cauntTime(H, M, S);
    }


    public void testRead() {
        System.out.println(hSet.getText() + " " + mSet.getText() + " " + sSet.getText() + " test");
        int[] cos = betterTimeCounter.countDiff("12", "50", "00");
        if (confirmBox.displey("Hello", "chcesz przetestować to?")) {
            System.out.println("działa");
        } else {
            System.out.println("nie działa");
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
        hSet.setText(betterTimeCounter.takeCurrentHour());
        mSet.setText(betterTimeCounter.takeCurrentMinute());
        sSet.setText(betterTimeCounter.takeCurrentSec());
        if (!toggleOnTime.isSelected()) {
            hSet.setDisable(true);
            mSet.setDisable(true);
            sSet.setDisable(true);
            onTimeButton.setDisable(true);
        }

    }
}
