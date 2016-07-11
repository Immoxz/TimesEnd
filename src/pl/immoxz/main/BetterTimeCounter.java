package pl.immoxz.main;


import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by ImmoXZ on 2016-07-07.
 */
public class BetterTimeCounter {
    AlertBox alertBox = new AlertBox();
    ConfirmBox confirmBox = new ConfirmBox();

    public long countTime(long H, long M, long S) {
        long fullTime = 0;

        if (S > 60) {
            M += S / 60;
            S = S % 60;
        }
        if (M > 60) {
            H += M / 60;
            M = M % 60;
        }
        fullTime = S + 60 * M + H * 60 * 60;
        return fullTime;
    }

    public String takeCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String takeCurrentHour() {
        DateFormat dateFormat = new SimpleDateFormat("HH");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String takeCurrentMinute() {
        DateFormat dateFormat = new SimpleDateFormat("mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String takeCurrentSec() {
        DateFormat dateFormat = new SimpleDateFormat("ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public long[] countDiff(String H, String M, String S) {
        long[] diffreents = {0, 0, 0};

        try {
            Date curTime = new SimpleDateFormat("HH:mm:ss").parse(takeCurrentTime());

            String time = H + ":" + M + ":" + S;
            Date setTime = new SimpleDateFormat("HH:mm:ss").parse(time);


            long diff = setTime.getTime() - curTime.getTime();

            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long minutes = (TimeUnit.MILLISECONDS.toMinutes(diff) - (hours * 60)) % 60;
            long seconds = (TimeUnit.MILLISECONDS.toSeconds(diff) - (hours * 60 * 60) - (minutes * 60)) % 60;
            if (setTime.after(curTime)) {
                diffreents[0] = hours;
                diffreents[1] = minutes;
                diffreents[2] = seconds;
            } else {
                if (confirmBox.displey("Ustawiony czas jest duży", "Czas zamknięcia został ustawiony na " + time + " czy napewno chcesz zamknąc komputer za " + hours + ":" + minutes)) {
                    diffreents[0] = hours;
                    diffreents[1] = minutes;
                    diffreents[2] = seconds;
                } else {
                    return null;
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffreents;
    }


}
