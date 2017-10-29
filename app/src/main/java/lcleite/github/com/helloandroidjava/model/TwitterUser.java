package lcleite.github.com.helloandroidjava.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by leandro on 22/09/17.
 */

public class TwitterUser implements Parcelable{
    private String userName;
    private String userScreenName;
    private String userProfileImageUrl;

    public TwitterUser() {}

    protected TwitterUser(Parcel in) {
        userName = in.readString();
        userScreenName = in.readString();
        userProfileImageUrl = in.readString();
    }

    public static final Creator<TwitterUser> CREATOR = new Creator<TwitterUser>() {
        @Override
        public TwitterUser createFromParcel(Parcel in) {
            return new TwitterUser(in);
        }

        @Override
        public TwitterUser[] newArray(int size) {
            return new TwitterUser[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public void setUserProfileImageUrl(String userProfileImageUrl) {
        this.userProfileImageUrl = userProfileImageUrl;
    }

    @Override
    public String toString() {
        return "TwitterUser{" +
                "userName='" + userName + '\'' +
                ", userScreenName='" + userScreenName + '\'' +
                ", userProfileImageUrl='" + userProfileImageUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(userScreenName);
        parcel.writeString(userProfileImageUrl);
    }
}
