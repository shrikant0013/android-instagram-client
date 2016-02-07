package com.shrikant.instagrampopularphotosclient;

import java.util.List;

/**
 * @author Shrikant Pandhare
 */
public class PhotoResource {
    String url;
    String caption;
    String height;
    String owner;
    long likeCount;
    String ownerURL;
    long createdTime;
    List<Comment> comments;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public String getOwnerURL() {
        return ownerURL;
    }

    public void setOwnerURL(String ownerURL) {
        this.ownerURL = ownerURL;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public List<Comment> getComment() {
        return comments;
    }

    public void setComment(List<Comment> comments) {
        this.comments = comments;
    }
}
