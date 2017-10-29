package lcleite.github.com.helloandroidjava.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import lcleite.github.com.helloandroidjava.R;

/**
 * Created by leandro on 24/09/17.
 */

public class AndroidUtils {
    public static void hideSoftKeyboard (Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public static SharedPreferences getPreferences(Context context){
        String preferencesKey = context.getString(R.string.preference_file_key);

        return context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE);
    }

    public static int getMaxTweetsPreference(Context context){
        String preferenceKey = context.getString(R.string.preference_max_tweets);
        SharedPreferences sharedPrefs = getPreferences(context);

        return sharedPrefs.getInt(preferenceKey, 10);
    }
}
