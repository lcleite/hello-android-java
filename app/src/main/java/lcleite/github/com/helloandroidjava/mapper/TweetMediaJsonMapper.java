package lcleite.github.com.helloandroidjava.mapper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lcleite.github.com.helloandroidjava.model.TweetMedia;

/**
 * Created by leandro on 22/09/17.
 */

public class TweetMediaJsonMapper implements JsonMapper<TweetMedia> {

    @Override
    public TweetMedia toModel(JSONObject jsonObject) {
        TweetMedia tweetMedia = new TweetMedia();
        JSONObject entities = jsonObject.optJSONObject("entities");
        JSONObject media = getMedia(entities);
        JSONObject extendedEntities = jsonObject.optJSONObject("extended_entities");
        JSONObject extendedMedia = getMedia(extendedEntities);

        if(media == null)
            setTweetWithNoMedia(tweetMedia);
        else if(extendedMedia == null)
            setTweetWithPhotoMedia(tweetMedia, media);
        else
            setTweetWithVideoMedia(tweetMedia, media, extendedMedia);

        return tweetMedia;
    }

    private void setTweetWithNoMedia(TweetMedia tweetMedia){
        tweetMedia.setType(TweetMedia.Type.NONE);
    }

    private void setTweetWithPhotoMedia(TweetMedia tweetMedia, JSONObject media) {
        tweetMedia.setType(TweetMedia.Type.PHOTO);
        tweetMedia.setPhotoUrl(getPhotoUrl(media));
    }

    private void setTweetWithVideoMedia(TweetMedia tweetMedia, JSONObject media, JSONObject extendedMedia) {
        tweetMedia.setType(getExtendedMediaType(extendedMedia));
        tweetMedia.setPhotoUrl(getPhotoUrl(media));
        tweetMedia.setVideoUrl(getVideoUrl(extendedMedia));
    }

        private JSONObject getMedia(JSONObject entities) {
        try {
            JSONArray media = entities.optJSONArray("media");
            if (media.length() > 0)
                return (JSONObject) media.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Log.d("TweetMediaJsonMapper", "Tweet without media");
        }

        return null;
    }

    private TweetMedia.Type getExtendedMediaType(JSONObject extendedMedia) {
        String type = extendedMedia.optString("type");

        if(type.equals("photo"))
            return TweetMedia.Type.PHOTO;
        else
            return TweetMedia.Type.VIDEO;
    }

    private String getPhotoUrl(JSONObject media){
        return getUrl(media, "media_url");
    }

    private String getVideoUrl(JSONObject media){
        return getUrl(media, "expanded_url");
    }

    private String getUrl(JSONObject media, String field) {
        if(media != null)
            return media.optString(field);
        else
            return "";
    }
}
