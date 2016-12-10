package Normal;

/**
 * Created by oskar on 2016-11-21.
 * This class has a timer and will count down 60 ticks per second
 */
public class Timer {

    private int ticks;
    private int maxTicks;
    private boolean done;

    public Timer(int ticks) {
        maxTicks = ticks;
        this.ticks = maxTicks;
    }

    public int update() {
        if (!done) {
            ticks--;
            if (ticks <= 0) done = true;
        }
        return ticks;
    }

    public boolean isDone() {
        return done;
    }

    public void restart() {
        done = false;
        ticks = maxTicks;
    }

    public void ring() {
        ticks = 0;
        done = true;
    }

    public double getPercent() {
        return 1 - (ticks / (double) maxTicks);
    }
}
