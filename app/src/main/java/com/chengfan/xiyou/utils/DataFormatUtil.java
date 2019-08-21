package com.chengfan.xiyou.utils;

public class DataFormatUtil {

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static String formatDate(String date) {
        String format;
        if (date.contains("T")) {
            format = date.split("T")[0];
            String time = date.split("T")[1];
            if (time.contains(":") && time.split(":").length >= 2) {
                format = format + " " + time.split(":")[0] + ":" + time.split(":")[1];
            }
        } else {
            format = date;
        }
        return format;
    }

}
