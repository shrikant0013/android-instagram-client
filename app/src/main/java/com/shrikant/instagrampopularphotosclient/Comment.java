package com.shrikant.instagrampopularphotosclient;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 2/7/16.
 * @author Shrikant Pandhare
 */
//public class Comment implements Parcelable {
//
//    long createdTime;
//    String commentText;
//    String userName;
//    String profileURL;
//
//
//}
public class Comment implements Parcelable {

    long createdTime;
    String commentText;
    String userName;
    String profileURL;

    public Comment() {

    }

    protected Comment(Parcel in) {
        createdTime = in.readLong();
        commentText = in.readString();
        userName = in.readString();
        profileURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(createdTime);
        dest.writeString(commentText);
        dest.writeString(userName);
        dest.writeString(profileURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}