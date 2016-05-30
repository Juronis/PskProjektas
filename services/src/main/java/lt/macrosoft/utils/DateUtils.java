package lt.macrosoft.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Arnas on 2016-05-26.
 */
public class DateUtils {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(dateFormat);
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static Date getDate(String dateStartStr) {
        Date date = null;
        try {
            date =  simpleDateFormatter.parse(dateStartStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date generateDate(String dateStr) {
        DateTime date = DateTime.parse(dateStr, formatter);
        date = date.withDayOfWeek(DateTimeConstants.MONDAY);
        return date.toDate();
    }

    public static Date[] generateDateDuration(String from, String to) {
        DateTime jodaFrom = DateTime.parse(from, formatter);
        DateTime jodaTo = DateTime.parse(to, formatter);

        jodaFrom = jodaFrom.withDayOfWeek(DateTimeConstants.MONDAY);
        jodaTo = jodaTo.withDayOfWeek(DateTimeConstants.MONDAY);

        Date[] generatedDate = new Date[2];
        generatedDate[0] = jodaFrom.toDate();
        generatedDate[1] = jodaTo.toDate();
        return generatedDate;
    }
}
