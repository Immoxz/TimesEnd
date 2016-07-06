package pl.immoxz.main;

/**
 * Created by AdminIT on 2016-07-06.
 */
public class TimeCounter {

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
}
