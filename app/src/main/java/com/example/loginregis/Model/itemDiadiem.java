package com.example.loginregis.Model;

import java.io.Serializable;
import java.sql.Date;

public class itemDiadiem implements Serializable {
        public int ID;
        public String username;
        public Date ngaydang;
        public String tieude;
        public String noidung;
        public String hinhanh;

    public itemDiadiem() {
    }

    public float rating;
        public String diachi;
        public String postdate;

    public itemDiadiem setRating(float rating) {
        this.rating = rating;
        return this;
    }

    public itemDiadiem setDiachi(String diachi) {
        this.diachi = diachi;
        return this;
    }

    public String getPostdate() {
        return postdate;
    }

    public itemDiadiem setPostdate(String postdate) {
        this.postdate = postdate;
        return this;
    }

    public itemDiadiem(int ID, String username, String tieude, String noidung, String hinhanh, float rating, String diachi, String postdate) {
        this.ID = ID;
        this.username = username;
        this.tieude = tieude;
        this.noidung = noidung;
        this.hinhanh = hinhanh;
        this.rating = rating;
        this.diachi = diachi;
        this.postdate = postdate;
    }

    public int getID() {
        return ID;
    }

    public itemDiadiem(int ID, String username, Date ngaydang, String tieude, String noidung, String hinhanh, float rating, String diachi) {
        this.ID = ID;
        this.username = username;
        this.ngaydang = ngaydang;
        this.tieude = tieude;
        this.noidung = noidung;
        this.hinhanh = hinhanh;
        this.rating = rating;
        this.diachi = diachi;
    }

    public itemDiadiem setID(int ID) {
        this.ID = ID;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public itemDiadiem setUsername(String username) {
        this.username = username;
        return this;
    }

    public Date getNgaydang() {
        return ngaydang;
    }

    public itemDiadiem setNgaydang(Date ngaydang) {
        this.ngaydang = ngaydang;
        return this;
    }

    public String getTieude() {
        return tieude;
    }

    public itemDiadiem setTieude(String tieude) {
        this.tieude = tieude;
        return this;
    }

    public String getNoidung() {
        return noidung;
    }

    public itemDiadiem setNoidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public float getRating() {
        return rating;
    }

    public String getDiachi() {
        return diachi;
    }

    public itemDiadiem setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
        return this;
    }


    public itemDiadiem(int ID, String username, String tieude, String noidung, String hinhanh, float rating, String diachi) {
        this.ID = ID;
        this.username = username;
        this.tieude = tieude;
        this.noidung = noidung;
        this.hinhanh = hinhanh;
        this.rating=rating;
        this.diachi=diachi;
    }


}
