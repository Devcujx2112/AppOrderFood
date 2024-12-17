package com.tungduong.orderfood.Entity;

public class ShopingCart {
    private String uid;
    private String tenSP;
    private String giaTien;
    private String soLuong;
    private String tongTien;

    public ShopingCart(){}

    public ShopingCart(String uid, String tenSP, String giaTien, String soLuong, String tongTien) {
        this.uid = uid;
        this.tenSP = tenSP;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return "ShopingCart{" +
                "uid='" + uid + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", giaTien='" + giaTien + '\'' +
                ", soLuong='" + soLuong + '\'' +
                ", tongTien='" + tongTien + '\'' +
                '}';
    }
}
