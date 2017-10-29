package lcleite.github.com.helloandroidjava.mapper;

import org.json.JSONObject;

import lcleite.github.com.helloandroidjava.model.TwitterUser;

/**
 * Created by leandro on 22/09/17.
 */

public class TwitterUserJsonMapper implements JsonMapper<TwitterUser> {

    @Override
    public TwitterUser toModel(JSONObject jsonObject) {
        TwitterUser twitterUser = new TwitterUser();
        JSONObject user = jsonObject.optJSONObject("user");

        twitterUser.setUserName(user.optString("name"));
        twitterUser.setUserScreenName(user.optString("screen_name"));
        twitterUser.setUserProfileImageUrl(user.optString("profile_image_url"));

        return twitterUser;
    }
}