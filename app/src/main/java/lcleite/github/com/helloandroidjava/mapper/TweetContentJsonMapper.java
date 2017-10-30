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

public class TweetContentJsonMapper implements JsonMapper<TweetContent>{

    @Override
    public TweetContent toModel(JSONObject jsonObject) {
        TweetContent tweetContent = new TweetContent();
        JSONObject entities = jsonObject.optJSONObject("entities");

        tweetContent.setText(jsonObject.optString("text"));
        tweetContent.setLinks(getContent(entities.optJSONArray("urls"), "expanded_url"));
        tweetContent.setUserMentions(getContent(entities.optJSONArray("user_mentions"), "screen_name"));
        tweetContent.setHashtags(getContent(entities.optJSONArray("hashtags"), "text"));

        return tweetContent;
    }

    private List<String> getContent(JSONArray array, String jsonField) {
        List<String> content = new ArrayList<>();

        try {
            for(int i = 0; i < array.length(); i++){
                JSONObject jsonUrl = (JSONObject) array.get(i);
                content.add(jsonUrl.optString(jsonField));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return content;
    }
}
