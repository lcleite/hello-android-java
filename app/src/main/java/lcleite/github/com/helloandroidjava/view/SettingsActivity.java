package lcleite.github.com.helloandroidjava.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import lcleite.github.com.helloandroidjava.R;

public class SettingsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private Toolbar appBar;
    private SeekBar sbarTweetsSettings;
    private TextView tvTweetsSettingsValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initAppBar();
        initViews();
        getSavedSettings();
    }

    private void initAppBar() {
        appBar = (Toolbar) findViewById(R.id.appBar);
        appBar.setTitle(R.string.settings_title);
        setSupportActionBar(appBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        tvTweetsSettingsValue = (TextView) findViewById(R.id.tvTweetsSettingsValue);
        sbarTweetsSettings = (SeekBar) findViewById(R.id.sbarTweetsSettings);
        sbarTweetsSettings.setOnSeekBarChangeListener(this);
    }

    private void getSavedSettings() {
        SharedPreferences sharedPrefs = getSharedPreferences();
        int tweetsSettingsValue = sharedPrefs.getInt(getString(R.string.preference_max_tweets), 10);
        int actualValue = tweetsSettingsValue - 5;

        tvTweetsSettingsValue.setText(String.valueOf(tweetsSettingsValue));
        sbarTweetsSettings.setProgress(actualValue);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }

    private SharedPreferences getSharedPreferences(){
        return getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int actualValue = i + 5;

        tvTweetsSettingsValue.setText(String.valueOf(actualValue));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    protected void onStop() {
        saveSettings();
        super.onStop();
    }

    private void saveSettings() {
        SharedPreferences sharedPrefs = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(getString(R.string.preference_max_tweets), Integer.valueOf(tvTweetsSettingsValue.getText().toString()));
        editor.commit();
    }
}
