package lcleite.github.com.helloandroidjava.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by leandro on 22/09/17.
 */

public class Tweet implements Parcelable{
    private TwitterUser user;
    private TweetContent content;
    private TweetMedia media;
    private Date createdAt;

    public Tweet(){}

    protected Tweet(Parcel in) {
        user = in.readParcelable(TwitterUser.class.getClassLoader());
        content = in.readParcelable(TweetContent.class.getClassLoader());
        media = in.readParcelable(TweetMedia.class.getClassLoader());
        createdAt = new Date(in.readLong());
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    public TwitterUser getUser() {
        return user;
    }

    public void setUser(TwitterUser user) {
        this.user = user;
    }

    public TweetContent getContent() {
        return content;
    }

    public void setContent(TweetContent content) {
        this.content = content;
    }

    public TweetMedia getMedia() {
        return media;
    }

    public void setMedia(TweetMedia media) {
        this.media = media;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "user=" + user +
                ", content=" + content +
                ", media=" + media +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(user, i);
        parcel.writeParcelable(content, i);
        parcel.writeParcelable(media, i);
        parcel.writeLong(createdAt.getTime());
    }
}
