package lcleite.github.com.helloandroidjava.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by leandro on 22/09/17.
 */

public class TweetMedia implements Parcelable{
    public enum Type{
        NONE, PHOTO, VIDEO
    }

    private Type type;
    private String photoUrl;
    private String videoUrl;

    public TweetMedia() {}

    protected TweetMedia(Parcel in) {
        type = Type.valueOf(in.readString());
        photoUrl = in.readString();
        videoUrl = in.readString();
    }

    public static final Creator<TweetMedia> CREATOR = new Creator<TweetMedia>() {
        @Override
        public TweetMedia createFromParcel(Parcel in) {
            return new TweetMedia(in);
        }

        @Override
        public TweetMedia[] newArray(int size) {
            return new TweetMedia[size];
        }
    };

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "TweetMedia{" +
                "type=" + type +
                ", photoUrl='" + photoUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type.name());
        parcel.writeString(photoUrl);
        parcel.writeString(videoUrl);
    }
}
