package com.razor.ourjournal.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static final TimeZone defaultTimeZone = TimeZone.getTimeZone("GMT+2:00");

    public static String formatDate(DateFormat dateFormat, Date date) {
        return formatDateWithTimezone(dateFormat, date, defaultTimeZone);
    }

    public static String formatDateWithTimezone(DateFormat dateFormat, Date date, TimeZone timeZone) {
        if(date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.getFormat());
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static enum DateFormat {
        TIME("hh:mm:ss"),
        UTC("yyyy-MM-dd\'T\'HH:mm:ss.SSSZ"),
        UTCZ("yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'"),
        DAY_MONTH_YEAR("dd MMMM yyyy"),
        DAY_MON_YEAR_TIME("dd MMM yyyy hh:mm"),
        DAY_MON_YEAR("dd MMM yyyy"),
        DAY_MON_TIME("dd MMM HH:mm"),
        DAY_MON("dd MMM"),
        DAY_MONTH("dd MMMM"),
        DAY_MONTH_UI("d MMM"),
        MONTH_YEAR("MM/yy"),
        DAY_NAME_DAY_MONTH_YEAR("EEEE, dd MMMM yyyy"),
        SINGLE_DAY_MON_YEAR("d MMM yyyy");

        private String format;

        private DateFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }
    }
}
