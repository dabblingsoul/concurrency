package test.concurrent.race_condition;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 4. Incorrect use of Timer:
 *
 * If a TimerTask throws an exception,
 * the Timer thread dies and no more tasks will be executed.
 * Use ScheduledExecutorService instead, which handles exceptions gracefully.
 */
public class IncorrectTimerUsage {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                throw new RuntimeException("Task failed");
            }
        }, 1000, 1000);
    }
}