package com.example.loginregis.Model;

import android.widget.Toast;

import java.io.Serializable;

public class userProfile implements Serializable {
    public String hoten;
    public String ngaysinh;
    public String sdt;
    public String ava;
    public String usrname;

    public userProfile() {
    }

    public String getHoten() {
        return hoten;
    }

    public userProfile setHoten(String hoten) {
        this.hoten = hoten;
        return this;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public userProfile setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
        return this;
    }

    public String getSdt() {
        return sdt;
    }

    public userProfile setSdt(String sdt) {
        this.sdt = sdt;
        return this;
    }

    public String getAva() {
        return ava;
    }

    public userProfile setAva(String ava) {
        this.ava = ava;
        return this;
    }

    public String getUsrname() {
        return usrname;
    }

    public userProfile setUsrname(String usrname) {
        this.usrname = usrname;
        return this;
    }

    public userProfile(String hoten, String ngaysinh, String sdt, String ava, String usrname) {
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.ava = "http://52.230.70.150/android/avatar/"+ava;
        this.usrname = usrname;
    }
}
