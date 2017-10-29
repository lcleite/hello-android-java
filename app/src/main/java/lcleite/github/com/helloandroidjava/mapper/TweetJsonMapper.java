package lcleite.github.com.helloandroidjava.mapper;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lcleite.github.com.helloandroidjava.model.Tweet;
import lcleite.github.com.helloandroidjava.utils.DateUtils;

/**
 * Created by leandro on 22/09/17.
 */

public class TweetJsonMapper implements JsonMapper<Tweet> {

    private TwitterUserJsonMapper twitterUserJsonMapper;
    private TweetTextJsonMapper tweetTextJsonMapper;
    private TweetMediaJsonMapper tweetMediaJsonMapper;

    public TweetJsonMapper() {
        twitterUserJsonMapper = new TwitterUserJsonMapper();
        tweetTextJsonMapper = new TweetTextJsonMapper();
        tweetMediaJsonMapper = new TweetMediaJsonMapper();
    }

    @Override
    public Tweet toModel(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        tweet.setCreatedAt(DateUtils.createDateFromString(jsonObject.optString("created_at")));
        tweet.setUser(twitterUserJsonMapper.toModel(jsonObject));
        tweet.setText(tweetTextJsonMapper.toModel(jsonObject));
        tweet.setMedia(tweetMediaJsonMapper.toModel(jsonObject));

        return tweet;
    }


}
