package com.cdfive.springboot.startup;

/**
 * @author cdfive
 */
public class ApplicationStartupInfo {

    private ApplicationStartupInfo() {
    }

    private static Long startingTime;

    private static Long startedTime;

    private static Long startTimeCostMs;

    public static boolean starting() {
        if (startingTime != null) {
            return false;
        }
        startingTime = System.currentTimeMillis();
        return true;
    }

    public static boolean started() {
        if (startedTime != null) {
            return false;
        }

        startedTime = System.currentTimeMillis();
        startTimeCostMs = startedTime - startingTime;
        return true;
    }

    public static Long getStartingTime() {
        return startingTime;
    }

    public static Long getStartedTime() {
        return startedTime;
    }

    public static Long getStartTimeCostMs() {
        return startTimeCostMs;
    }
}
