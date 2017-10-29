package lcleite.github.com.helloandroidjava.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by leandro on 22/09/17.
 */

public class TweetContent implements Parcelable{
    private String text;
    private List<String> links;
    private List<String> hashtags;
    private List<String> userMentions;

    public TweetContent() {}

    protected TweetContent(Parcel in) {
        text = in.readString();
        links = in.createStringArrayList();
        hashtags = in.createStringArrayList();
        userMentions = in.createStringArrayList();
    }

    public static final Creator<TweetContent> CREATOR = new Creator<TweetContent>() {
        @Override
        public TweetContent createFromParcel(Parcel in) {
            return new TweetContent(in);
        }

        @Override
        public TweetContent[] newArray(int size) {
            return new TweetContent[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public List<String> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<String> userMentions) {
        this.userMentions = userMentions;
    }

    @Override
    public String toString() {
        return "TweetContent{" +
                "text='" + text + '\'' +
                ", links=" + links +
                ", hashtags=" + hashtags +
                ", userMentions=" + userMentions +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeStringList(links);
        parcel.writeStringList(hashtags);
        parcel.writeStringList(userMentions);
    }
}
