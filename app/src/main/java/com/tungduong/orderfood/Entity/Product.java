package com.tungduong.orderfood.Entity;

public class Product {
    private String masp;
    private String tensp;
    private int soLuong;
    private String giaTien;
    private String image;
    private String loaiDoAn;
    private String moTa;

    public Product() {
    }

    public Product(String masp, String tensp, int soLuong, String giaTien, String image, String loaiDoAn, String moTa) {
        this.masp = masp;
        this.tensp = tensp;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.image = image;
        this.loaiDoAn = loaiDoAn;
        this.moTa = moTa;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLoaiDoAn() {
        return loaiDoAn;
    }

    public void setLoaiDoAn(String loaiDoAn) {
        this.loaiDoAn = loaiDoAn;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "Product{" +
                "masp='" + masp + '\'' +
                ", tensp='" + tensp + '\'' +
                ", soLuong=" + soLuong +
                ", giaTien='" + giaTien + '\'' +
                ", image='" + image + '\'' +
                ", loaiDoAn='" + loaiDoAn + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
