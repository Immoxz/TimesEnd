package pl.immoxz.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    TimeCounter timeCounter = new TimeCounter();

    int finalTime = 0;
    int H = 0;
    int M = 0;
    int S = 0;

    @FXML
    private TextField afterHouSet;
    @FXML
    private TextField afterMinSet;
    @FXML
    private TextField afterSecSet;
    @FXML
    private Label curTime;
    @FXML
    private Label curBotTime;
    @FXML
    private TextField hSet;
    @FXML
    private TextField mSet;
    @FXML
    private TextField sSet;


    //TODO stwoprzyc alertbox na elsa gdy nie ustawiono godziny
    public void okButtonClicked() {
        System.out.println("ok button");
        if (finalTime <= 60)
            execCommand("shutdown /f /s /t " + this.finalTime);
        //else
        System.out.println(this.finalTime);
    }

    public void cancelButtonClicked() {
        System.out.println("cancel button");
        execCommand("shutdown -a");
    }

    @FXML
    public void setTime() {


        if (afterHouSet.getText() != null)
            H = Integer.parseInt(afterHouSet.getText());
        if (afterMinSet.getText() != null)
            M = Integer.parseInt(afterMinSet.getText());
        if (afterSecSet.getText() != null)
            S = Integer.parseInt(afterSecSet.getText());
        System.out.println(afterHouSet.getText() + " " + afterMinSet.getText() + " " + afterSecSet.getText());
        System.out.println(H + " " + M + " " + S);
        this.finalTime = timeCounter.cauntTime(H, M, S);
    }

    public void testRead() {
        System.out.println(hSet.getText() + " " + mSet.getText() + " " + sSet.getText()+ " test");
        int[] cos = timeCounter.countDiff("12","50","00");
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
        curBotTime.setText(timeCounter.takeCurrentTime());
        hSet.setText(timeCounter.takeCurrentHour());
        mSet.setText(timeCounter.takeCurrentMinute());
        sSet.setText(timeCounter.takeCurrentSec());
    }
}
