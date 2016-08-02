package pl.immoxz.main;
;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MyUtilities {

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

    public static boolean areAllTrue(boolean[] array) {
        for (boolean b : array) if (!b) return false;
        return true;
    }


    public boolean isGoodIntOfTF(TextField textField) {

        if (isInteger(textField.getText())) {
            textField.setStyle("-fx-background-color: lightskyblue;");
            return true;
        } else {
            textField.setStyle("-fx-background-color: red;");
            return false;
        }

    }

    public static boolean isInteger(String input) {
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

}
