package com.razor.ourjournal.screens.timeline.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Post implements Parcelable {

    private String title;
    private String description;
    private String userEmailPostedBy;
    private String userEmailPostedFor;
    private Long date;

    public Post() {
    }

    protected Post(Parcel in) {
        title = in.readString();
        description = in.readString();
        userEmailPostedBy = in.readString();
        userEmailPostedFor = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getDate() {
        return date;
    }

    public String getUserEmailPostedBy() {
        return userEmailPostedBy;
    }

    public String getUserEmailPostedFor() {
        return userEmailPostedFor;
    }

    public Post(String title, String description, String userEmailPostedBy, String userEmailPostedFor) {
        this.title = title;
        this.description = description;
        this.userEmailPostedBy = userEmailPostedBy;
        this.userEmailPostedFor = userEmailPostedFor;
        this.date = new Date().getTime();
    }

    public String getPostId() {
        return getPostId(userEmailPostedBy, userEmailPostedFor);
    }

    public static String getPostId(String idA, String idB) {
        int compare = idA.toLowerCase().compareTo(idB.toLowerCase());
        String postId;
        if (compare < 0) {
            postId = String.format("%s_%s", idA, idB);
        }
        else {
            postId = String.format("%s_%s", idB, idA);
        }

        return postId.replace(".", "");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(userEmailPostedBy);
        parcel.writeString(userEmailPostedFor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (title != null ? !title.equals(post.title) : post.title != null) return false;
        if (description != null ? !description.equals(post.description) : post.description != null)
            return false;
        if (userEmailPostedBy != null ? !userEmailPostedBy.equals(post.userEmailPostedBy) : post.userEmailPostedBy != null)
            return false;
        return userEmailPostedFor != null ? userEmailPostedFor.equals(post.userEmailPostedFor) : post.userEmailPostedFor == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (userEmailPostedBy != null ? userEmailPostedBy.hashCode() : 0);
        result = 31 * result + (userEmailPostedFor != null ? userEmailPostedFor.hashCode() : 0);
        return result;
    }
}
