package lcleite.github.com.helloandroidjava.mapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lcleite.github.com.helloandroidjava.model.TweetContent;

/**
 * Created by leandro on 22/09/17.
 */

public class TweetTextJsonMapper implements JsonMapper<TweetContent>{

    @Override
    public TweetContent toModel(JSONObject jsonObject) {
        TweetContent tweetContent = new TweetContent();
        JSONObject entities = jsonObject.optJSONObject("entities");

        tweetContent.setText(jsonObject.optString("text"));
        tweetContent.setLinks(getLinks(entities.optJSONArray("urls")));
        tweetContent.setUserMentions(getUserMentions(entities.optJSONArray("user_mentions")));
        tweetContent.setHashtags(getHashtags(entities.optJSONArray("hashtags")));

        return tweetContent;
    }

    private List<String> getLinks(JSONArray urls) {
        List<String> links = new ArrayList<>();

        try {
            for(int i = 0; i < urls.length(); i++){
                JSONObject jsonUrl = (JSONObject) urls.get(i);
                links.add(jsonUrl.optString("expanded_url"));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return links;
    }

    private List<String> getUserMentions(JSONArray userMentions) {
        List<String> users = new ArrayList<>();

        try {
            for(int i = 0; i < userMentions.length(); i++){
                JSONObject jsonUser = (JSONObject) userMentions.get(i);
                users.add(jsonUser.optString("screen_name"));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    private List<String> getHashtags(JSONArray hashtags) {
        List<String> tags = new ArrayList<>();

        try {
            for(int i = 0; i < hashtags.length(); i++){
                JSONObject jsonHashtag = (JSONObject) hashtags.get(i);
                tags.add(jsonHashtag.optString("text"));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return tags;
    }
}
