package utils;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CountDown {
    private int interval;
    private Timer timer;
    private boolean finish;


    public CountDown(int intervalInMilliSecond) {
        this.interval = intervalInMilliSecond;
        finish = false;
        setCountDown();
    }

    public void setCountDown(){
        int delay = 10;
        int period = 10;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
//                System.out.println(interval);
                setInterval();
            }
        }, delay, period);
    }

    private final void setInterval() {
        if (interval == 1){
            timer.cancel();
            finish = true;
        }
        interval--;
    }

    public boolean isFinish() {
        return finish;
    }
}