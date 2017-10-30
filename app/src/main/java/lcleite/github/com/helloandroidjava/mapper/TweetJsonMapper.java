package lcleite.github.com.helloandroidjava.mapper;

import org.json.JSONObject;

import lcleite.github.com.helloandroidjava.model.Tweet;
import lcleite.github.com.helloandroidjava.utils.DateUtils;

/**
 * Created by leandro on 22/09/17.
 */

public class TweetJsonMapper implements JsonMapper<Tweet> {

    private TwitterUserJsonMapper twitterUserJsonMapper;
    private TweetContentJsonMapper tweetContentJsonMapper;
    private TweetMediaJsonMapper tweetMediaJsonMapper;

    public TweetJsonMapper() {
        twitterUserJsonMapper = new TwitterUserJsonMapper();
        tweetContentJsonMapper = new TweetContentJsonMapper();
        tweetMediaJsonMapper = new TweetMediaJsonMapper();
    }

    @Override
    public Tweet toModel(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        tweet.setCreatedAt(DateUtils.createDateFromString(jsonObject.optString("created_at")));
        tweet.setUser(twitterUserJsonMapper.toModel(jsonObject));
        tweet.setContent(tweetContentJsonMapper.toModel(jsonObject));
        tweet.setMedia(tweetMediaJsonMapper.toModel(jsonObject));

        return tweet;
    }


}
