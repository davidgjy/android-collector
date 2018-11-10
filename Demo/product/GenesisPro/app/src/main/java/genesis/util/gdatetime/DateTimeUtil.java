package genesis.util.gdatetime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KG on 16/3/1.
 */
public class DateTimeUtil {
    public static String getDateTimeByFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);

        return dateString;
    }

    public static Date convertDateFromStringFormat(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateString);
            return date;
        } catch (Exception e) {
            return null;
        }
    }
}
