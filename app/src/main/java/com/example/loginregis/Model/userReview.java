package com.example.loginregis.Model;

import java.io.Serializable;
import java.sql.Date;

public class userReview implements Serializable {
    public String userName;
    public String noidungReview;
    public float userRating;
    public String userAvatar;
    public Date postDate;
    public String ngaydang;
    public String hinhanhBinhLuan;

    public String getNgaydang() {
        return ngaydang;
    }

    public void setHinhanhBinhLuan(String hinhanhBinhLuan) {
        this.hinhanhBinhLuan = hinhanhBinhLuan;
    }

    public String getHinhanhBinhLuan() {
        return hinhanhBinhLuan;
    }

    public userReview(String userName, String noidungReview, float userRating, String userAvatar, String ngaydang, String img) {
        this.userName = userName;
        this.noidungReview = noidungReview;
        this.userRating = userRating;
        this.userAvatar = userAvatar;
        this.ngaydang = ngaydang;
        this.hinhanhBinhLuan=img;
    }

    public userReview(String userName, String noidungReview, float userRating, String userAvatar, Date postDate) {
        this.userName = userName;
        this.noidungReview = noidungReview;
        this.userRating = userRating;
        this.userAvatar = userAvatar;
        this.postDate = postDate;
    }

    public String getUserName() {
        return userName;
    }

    public userReview setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getNoidungReview() {
        return noidungReview;
    }

    public userReview setNoidungReview(String noidungReview) {
        this.noidungReview = noidungReview;
        return this;
    }

    public float getUserRating() {
        return userRating;
    }

    public userReview setUserRating(float userRating) {
        this.userRating = userRating;
        return this;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public userReview setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }

    public Date getPostDate() {
        return postDate;
    }

    public userReview setPostDate(Date postDate) {
        this.postDate = postDate;
        return this;
    }
}
