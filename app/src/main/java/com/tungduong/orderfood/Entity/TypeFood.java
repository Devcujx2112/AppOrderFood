package com.tungduong.orderfood.Entity;

public class TypeFood {
    private String maLoaiDoAn;
    private String tenLoaiDoAn;
    private String image;
    private String moTa;

    public TypeFood(){}

    public TypeFood(String maLoaiDoAn, String tenLoaiDoAn, String image, String moTa) {
        this.maLoaiDoAn = maLoaiDoAn;
        this.tenLoaiDoAn = tenLoaiDoAn;
        this.image = image;
        this.moTa = moTa;
    }

    public String getMaLoaiDoAn() {
        return maLoaiDoAn;
    }

    public void setMaLoaiDoAn(String maLoaiDoAn) {
        this.maLoaiDoAn = maLoaiDoAn;
    }

    public String getTenLoaiDoAn() {
        return tenLoaiDoAn;
    }

    public void setTenLoaiDoAn(String tenLoaiDoAn) {
        this.tenLoaiDoAn = tenLoaiDoAn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "TypeFood{" +
                "maLoaiDoAn='" + maLoaiDoAn + '\'' +
                ", tenLoaiDoAn='" + tenLoaiDoAn + '\'' +
                ", image='" + image + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
