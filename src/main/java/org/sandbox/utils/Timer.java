package org.sandbox.utils;

public class Timer {

    private long start = System.currentTimeMillis();
    private Long stop;

    private static final String TIMER_FORMAT = "%d ms";

    public void stop() {
        stop = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        if (stop == null) {
            stop();
        }

        long elapsed = stop - start;

        return StringUtils.format(TIMER_FORMAT, elapsed);
    }
}
