package lcleite.github.com.helloandroidjava.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by leandro on 24/09/17.
 */

public class StringUtils {

    public static String encodeSearchTerm(String query){
        try {
            return URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return query;
    }

    public static String screenNameWithAt(String screenName){
        return "@" + screenName;
    }
}
