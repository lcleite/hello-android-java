package lcleite.github.com.helloandroidjava.async;

import android.graphics.Bitmap;
import android.widget.ImageView;

import lcleite.github.com.helloandroidjava.R;
import lcleite.github.com.helloandroidjava.utils.CircularImage;

/**
 * Created by leandro on 22/09/17.
 */

public class SetDownloadedImageTask extends DownloadImageTask {

    private ImageView targetImageView;

    public SetDownloadedImageTask(String imageUrl, ImageView targetImageView) {
        this.imageUrl = imageUrl;
        this.targetImageView = targetImageView;
    }

    @Override
    protected void onPreExecute() {
        targetImageView.setImageResource(R.drawable.downloading_placeholder);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap downloadedBitmap = super.doInBackground();

        return transformBitmap(downloadedBitmap);
    }

    @Override
    protected void onPostExecute(Bitmap downloadedBitmap) {
        targetImageView.setImageBitmap(downloadedBitmap);
    }

    private Bitmap transformBitmap(Bitmap downloadedBitmap) {
        final int IMAGE_SIZE = 100;

        return CircularImage.getRoundedCornerBitmap(downloadedBitmap, IMAGE_SIZE, IMAGE_SIZE);
    }
}
