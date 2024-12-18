package com.tungduong.orderfood.Entity;

public class ShopingCart {
    private String tenSP;
    private String uid;
    private String giaTien;
    private int soLuong;
    private String image;

    public ShopingCart(){}

    public ShopingCart(String tenSP, String uid, String giaTien, int soLuong, String image) {
        this.tenSP = tenSP;
        this.uid = uid;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.image = image;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ShopingCart{" +
                "tenSP='" + tenSP + '\'' +
                ", uid='" + uid + '\'' +
                ", giaTien='" + giaTien + '\'' +
                ", soLuong=" + soLuong +
                ", image='" + image + '\'' +
                '}';
    }
}
