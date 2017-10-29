package lcleite.github.com.helloandroidjava.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import lcleite.github.com.helloandroidjava.R;
import lcleite.github.com.helloandroidjava.async.DownloadImageTask;
import lcleite.github.com.helloandroidjava.async.SetDownloadedImageTask;
import lcleite.github.com.helloandroidjava.model.Tweet;
import lcleite.github.com.helloandroidjava.model.TweetMedia;
import lcleite.github.com.helloandroidjava.utils.DateUtils;
import lcleite.github.com.helloandroidjava.utils.StringUtils;

public class TweetActivity extends AppCompatActivity implements View.OnClickListener, DownloadImageTask.Callback {

    private Tweet tweet;

    private Toolbar appBar;
    private ImageView ivUserProfile;
    private TextView tvUserName;
    private TextView tvUserScreenName;
    private TextView tvCreatedAt;
    private TextView tvText;
    private FrameLayout mediaFrame;
    private ProgressBar pbarLoading;
    private ImageView ivMediaPhoto;
    private ImageButton ibMediaPlay;
    private TextView tvHashtags;
    private LinearLayout hashtagsContainer;
    private TextView tvMentions;
    private LinearLayout mentionsContainer;
    private TextView tvLinks;
    private LinearLayout linksContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        initAppBar();
        getExtras();
        initViews();
        setupViews();
    }

    private void initAppBar() {
        appBar = (Toolbar) findViewById(R.id.appBar);
        appBar.setTitle(R.string.tweet_details_title);
        setSupportActionBar(appBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void getExtras() {
        tweet = getIntent().getParcelableExtra(MainActivity.EXTRA_TWEET);
    }

    private void initViews() {
        ivUserProfile = (ImageView) findViewById(R.id.ivUserProfile);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserScreenName = (TextView) findViewById(R.id.tvUserScreenName);
        tvCreatedAt = (TextView) findViewById(R.id.tvCreatedAt);
        tvText = (TextView) findViewById(R.id.tvText);
        mediaFrame = (FrameLayout) findViewById(R.id.mediaFrame);
        pbarLoading = (ProgressBar) findViewById(R.id.pbarLoading);
        ivMediaPhoto = (ImageView) findViewById(R.id.ivMediaPhoto);
        ibMediaPlay = (ImageButton) findViewById(R.id.ibMediaPlay);
        tvHashtags = (TextView) findViewById(R.id.tvHashtags);
        hashtagsContainer = (LinearLayout) findViewById(R.id.hashtagsContainer);
        tvMentions = (TextView) findViewById(R.id.tvMentions);
        mentionsContainer = (LinearLayout) findViewById(R.id.mentionsContainer);
        tvLinks = (TextView) findViewById(R.id.tvLinks);
        linksContainer = (LinearLayout) findViewById(R.id.linksContainer);
    }

    private void setupViews() {
        setupTextViews();
        downloadUserProfileImage();
        downloadTweetMedia();
        setupLists();
        setupClickableLinks();
    }

    private void setupTextViews() {
        tvUserName.setText(tweet.getUser().getUserName());
        tvUserScreenName.setText(StringUtils.screenNameWithAt(tweet.getUser().getUserScreenName()));
        tvCreatedAt.setText(DateUtils.getStringFromDate(tweet.getCreatedAt()));
        tvText.setText(tweet.getContent().getText());
    }

    private void setupLists() {
        setupTextListValues(hashtagsContainer, tvHashtags, tweet.getContent().getHashtags(), "#");
        setupTextListValues(mentionsContainer, tvMentions, tweet.getContent().getUserMentions(), "@");
        setupTextListValues(linksContainer, tvLinks, tweet.getContent().getLinks(), "");
    }

    private void downloadUserProfileImage(){
        new SetDownloadedImageTask(tweet.getUser().getUserProfileImageUrl(), ivUserProfile).execute();
    }

    private void downloadTweetMedia(){
        if(tweet.getMedia().getType() != TweetMedia.Type.NONE)
            new DownloadImageTask(tweet.getMedia().getPhotoUrl(), this).execute();
        else
            mediaFrame.setVisibility(View.GONE);
    }

    private void setupTextListValues(LinearLayout container, TextView textView, List<String> list, String prefix){
        if(list.size() == 0){
            container.setVisibility(View.GONE);
            return;
        }

        for(int i = 0; i < list.size(); i++){
            textView.append(prefix + list.get(i));
            if(i != list.size() - 1)
                textView.append(", ");
        }
    }

    private void setupClickableLinks() {
        Linkify.addLinks(tvLinks, Linkify.WEB_URLS);
        tvLinks.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        String videoUrl = tweet.getMedia().getVideoUrl();
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));

        startActivity(intent);
    }

    @Override
    public void onImageDownloadComplete(Bitmap downloadedBitmap) {
        ivMediaPhoto.setImageBitmap(downloadedBitmap);
        pbarLoading.setVisibility(View.GONE);
        if(tweet.getMedia().getType() == TweetMedia.Type.VIDEO)
            setupVideoThumbnail();
    }

    private void setupVideoThumbnail() {
        ibMediaPlay.setVisibility(View.VISIBLE);
        mediaFrame.setOnClickListener(this);
        ibMediaPlay.setOnClickListener(this);
    }
}
