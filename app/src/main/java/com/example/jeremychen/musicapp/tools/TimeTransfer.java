package com.example.jeremychen.musicapp.tools;

/**
 * Created by jeremychen on 2018/7/26.
 */

public class TimeTransfer {

    private int time;

    public TimeTransfer(int time) {
        this.time = time;
    }

    public String getTime(){
        return secToTime(time);
    }

    public static String secToTime(int time) {
        String timeStr;
        int hour, minute, second, millisecond;
        if (time <= 0)
            return "00:00:00.000";
        else {
            second = time /1000;
            minute = second / 60;
            millisecond = time % 1000;
            if (second < 60) {

                timeStr = "00:00:" + unitFormat(second) + "." + unitFormat2(millisecond);
            }else if (minute < 60) {
                second = second % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second) + "." + unitFormat2(millisecond);
            }else{
                hour = minute /60;
                minute = minute % 60;
                second = second - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second) + "." + unitFormat2(millisecond);
            }
        }
        return timeStr.substring(3,8);
    }

    public static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String unitFormat2(int i) {
        String retStr;
        if (i >= 0 && i < 10)
            retStr = "00" + Integer.toString(i);
        else if (i >=10 && i < 100) {
            retStr = "0" + Integer.toString(i);
        }
        else
            retStr = "" + i;
        return retStr;
    }
}
