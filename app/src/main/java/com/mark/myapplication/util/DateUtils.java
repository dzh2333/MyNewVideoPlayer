package com.mark.myapplication.util;

public class DateUtils {

    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(hour > 0) {
            sb.append(hour+":");
        }
        if(minute > 0) {
            sb.append(minute+":");
        }else {
            sb.append("00:");
        }
        if(second > 0) {
            sb.append(second);
        }else {
            sb.append("00");
        }
        return sb.toString();
    }

}
