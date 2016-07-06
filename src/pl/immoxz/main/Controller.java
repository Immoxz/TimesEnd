package pl.immoxz.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    TimeCounter timeCounter = new TimeCounter();

    int finalTime = 0;

    @FXML
    private TextField afterHouSet;
    @FXML
    private TextField afterMinSet;
    @FXML
    private TextField afterSecSet;

    public void okButtonClicked() {
        System.out.println("ok button");
        execCommand("shutdown /f /s /t " + timeCounter.cauntTime(0, 100, 100));
        System.out.println(timeCounter.cauntTime(0, 100, 100));
    }

    public void cancelButtonClicked() {
        System.out.println("cancel button");
        execCommand("shutdown -a");
        if (afterHouSet.getText().equals(null))
            System.out.println(afterHouSet.getText());
        else
            System.out.println("kupa");
    }

    @FXML
    public void setTime() {
        int H = 0;
        int M = 0;
        int S = 0;
        if (!afterHouSet.getText().isEmpty())
            H = Integer.parseInt(afterHouSet.getText());
        if (afterMinSet.getText() != null || afterMinSet.getText() != "")
            M = Integer.parseInt(afterMinSet.getText());
        if (afterSecSet.getText() != null || afterSecSet.getText() != "")
            S = Integer.parseInt(afterSecSet.getText());
        System.out.println(H + " " + M + " " + S);
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
        afterHouSet.setText(null);
        afterMinSet.setText(null);
        afterSecSet.setText(null);

    }
}
