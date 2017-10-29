package lcleite.github.com.helloandroidjava.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by leandro on 23/09/17.
 */

public class DateUtils {

    private static final String TWEET_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    private static final String SEARCH_RESULT_DATE_FORMAT = "MMM dd yyyy HH:mm";

    public static Date createDateFromString(String dateString) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat(TWEET_DATE_FORMAT, Locale.getDefault());

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getStringFromDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(SEARCH_RESULT_DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }
}
