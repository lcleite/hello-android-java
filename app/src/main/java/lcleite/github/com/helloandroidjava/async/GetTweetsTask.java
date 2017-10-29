package lcleite.github.com.helloandroidjava.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lcleite.github.com.helloandroidjava.mapper.TweetJsonMapper;
import lcleite.github.com.helloandroidjava.model.Tweet;
import lcleite.github.com.helloandroidjava.utils.AndroidUtils;

/**
 * Created by leandro on 21/09/17.
 */

public class GetTweetsTask extends AsyncTask<String, Void, String> {

    private Callback callback;
    private int responseMaxLength;

    public GetTweetsTask(Callback callback) {
        this.callback = callback;
        this.responseMaxLength = AndroidUtils.getMaxTweetsPreference((Context) callback);
    }

    @Override
    protected String doInBackground(String... params) {
        String query = params[0];
        String url = "https://api.twitter.com/1.1/search/tweets.json?q=" + query;
        HttpURLConnection urlConnection = null;
        String response = "";

        try {
            urlConnection = createHttpUrlConnection(url);
            response = getResponse(urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        List<Tweet> results = new ArrayList<>();

        try {
            JSONObject responseJson = new JSONObject(response);
            JSONArray jsonArray = responseJson.getJSONArray("statuses");
            for(int i = 0; i < jsonArray.length(); i++){
                if(i == responseMaxLength)
                    break;

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Tweet tweet = new TweetJsonMapper().toModel(jsonObject);
                results.add(tweet);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        callback.setSearchResult(results);
    }

    private HttpURLConnection createHttpUrlConnection(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Authorization",
                "Bearer AAAAAAAAAAAAAAAAAAAAANKm2QAAAAAA4egRyibPOIGTFLzy%2BhDnYDdr62o%3DcGtYhty15tRTVLwWjWux3S2rcP4cLxGoFEgJcissPmYOYkSikA");

        return urlConnection;
    }

    private String getResponse(HttpURLConnection urlConnection) throws IOException {
        InputStream stream;

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            stream = new BufferedInputStream(urlConnection.getInputStream());
        else
            stream = new BufferedInputStream(urlConnection.getErrorStream());

        return readStreamLines(stream);
    }

    private String readStreamLines(InputStream stream) throws IOException {
        StringBuilder response = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line;
        while ((line = reader.readLine()) != null)
            response.append(line);

        return response.toString();
    }

    public interface Callback{
        void setSearchResult(List<Tweet> results);
    }
}
