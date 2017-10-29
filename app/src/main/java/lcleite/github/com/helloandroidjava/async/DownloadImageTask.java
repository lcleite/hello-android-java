package lcleite.github.com.helloandroidjava.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leandro on 23/09/17.
 */

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {

    private Callback callback;
    protected String imageUrl;

    protected DownloadImageTask(){}

    public DownloadImageTask(String imageUrl, Callback callback) {
        this.imageUrl = imageUrl;
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap downloadedBitmap = null;

        try {
            downloadedBitmap = getBitmapFromUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return downloadedBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap downloadedBitmap) {
        callback.onImageDownloadComplete(downloadedBitmap);
    }

    private Bitmap getBitmapFromUrl() throws IOException {
        URL urlObject = new URL(imageUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();

        return BitmapFactory.decodeStream(urlConnection.getInputStream());
    }

    public interface Callback{
        void onImageDownloadComplete(Bitmap downloadedBitmap);
    }
}
