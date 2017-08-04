package com.mrrusof.thecourtroom;

public class Ruling {

    public static final String ACCEPTED = "ACCEPTED";
    public static final String WRONG_ANSWER = "WRONG_ANSWER";
    public static final String TIMEOUT = "TIMEOUT";
    public static final String RUNTIME_ERROR = "RUNTIME_ERROR";

    private final String ruling;
    private final Long centiseconds;

    public Ruling(String ruling, Long centiseconds) {
        this.ruling = ruling;
        this.centiseconds = centiseconds;
    }

    public Ruling(String ruling, String time) {
        this(ruling, timeStringToCentiseconds(time));
    }

    public String getRuling() {
        return ruling;
    }

    public String getSeconds() {
        return centiseconds / 100 + "." + String.format("%02d", centiseconds % 100);
    }

    Long getCentiseconds() {
        return centiseconds;
    }

    private static Long timeStringToCentiseconds(String time) {

        String hours = "0";
        String minutes = "0";
        String seconds;
        String centis = "0";

        String[] partsTime = time.split(":");
        switch(partsTime.length) {
        case 3:
            hours = partsTime[0];
            minutes = partsTime[1];
            seconds = partsTime[2];
            break;
        case 2:
            minutes = partsTime[0];
            seconds = partsTime[1];
            break;
        case 1:
            seconds = time;
            break;
        default:
            throw new RuntimeException("Wrong format for time string: " + time);
        }

        String[] partsSeconds = seconds.split("\\.");
        switch(partsSeconds.length) {
        case 2:
            seconds = partsSeconds[0];
            centis = partsSeconds[1];
            break;
        case 1:
            break;
        default:
            throw new RuntimeException("Wrong format for time string: " + time);
        }

        return Long.valueOf(hours) * 360000 +
            Long.valueOf(minutes) * 6000 +
            Long.valueOf(seconds) * 100 +
            Long.valueOf(centis);
    }

}
