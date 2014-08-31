package org.shizhz.game.v2.domain;

/**
 * Created by zzshi on 8/31/14.
 */
public class Counter {
    private int times;

    private Counter(int times) {
        this.times = times;
    }

    public void reduce(int times) {
        this.times -= times;
    }

    public boolean hasChanceLeft() {
        return times > 0;
    }

    public int times() {
        return times;
    }

    @Override
    public String toString() {
        return String.valueOf(times);
    }

    public static Counter of(int times) {
        return new Counter(times);
    }

    public static Counter copy(Counter maxTimes) {
        return new Counter(maxTimes.times);
    }
}
