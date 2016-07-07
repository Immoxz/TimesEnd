package pl.immoxz.main;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ImmoXZ on 2016-07-07.
 */
public class BetterTimeCounter {

    public int cauntTime(int H, int M, int S) {
        Integer fullTime = 0;

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

    public int[] countDiff(String H, String M, String S) {
        int[] diffreents = {0, 0, 0};

        try {
            Date curTime = new SimpleDateFormat("HH:mm:ss").parse(takeCurrentTime());
            Calendar calendarCurTime = Calendar.getInstance();
            calendarCurTime.setTime(curTime);
            calendarCurTime.add(Calendar.DATE, 1);

            String setTime = H + ":" + M + ":" + S;
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(setTime);
            Calendar calendarTimeSet = Calendar.getInstance();
            calendarTimeSet.setTime(time2);
            calendarTimeSet.add(Calendar.DATE, 1);

            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            if (calendarCurTime.after(calendarTimeSet.getTime())) {

                System.out.println("Cur data: "+dateFormat.format(calendarCurTime.getTime()));
                System.out.println("set data: "+dateFormat.format(calendarTimeSet.getTime()));
                System.out.println(calendarCurTime.compareTo(calendarTimeSet));
//                Date diff = dateFormat.format(calendarCurTime.compareTo(calendarTimeSet));
                System.out.println(true);
            } else {
                System.out.println("Cur data: "+dateFormat.format(calendarCurTime.getTime()));
                System.out.println("set data: "+dateFormat.format(calendarTimeSet.getTime()));
                System.out.println(calendarCurTime.compareTo(calendarTimeSet));
                System.out.println(false);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        /*String[] curStrTime = {takeCurrentHour(), takeCurrentMinute(), takeCurrentSec()};
        int[] curintTime = {Integer.parseInt(curStrTime[0]), Integer.parseInt(curStrTime[1]), Integer.parseInt(curStrTime[2])};
        int itime[] = {Integer.parseInt(H), Integer.parseInt(M), Integer.parseInt(S)};
        itime[0] = curintTime[0] - itime[0];
        if(itime[0]>0)
            itime[0]=0;
        itime[1] = curintTime[1] - itime[1];
        if(itime[1]>0)
            itime[1]=-itime[1];
        itime[2] = curintTime[2] - itime[2];
        if(itime[1]>0)
            itime[1]=-itime[1];
        if (itime[0] <= 0 && itime[1] <= 0 && itime[1] <= 0)
            diffreents = itime;*/
        return diffreents;
    }

}
