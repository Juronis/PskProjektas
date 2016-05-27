package lt.macrosoft.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Arnas on 2016-05-26.
 */
public class DateUtils {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(dateFormat);

    public static Date getDate(String dateStartStr) {
        Date date = null;
        try {
            date =  simpleDateFormatter.parse(dateStartStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
