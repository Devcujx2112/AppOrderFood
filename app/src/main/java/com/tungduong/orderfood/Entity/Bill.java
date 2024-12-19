package com.tungduong.orderfood.Entity;

public class Bill {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String total;
    private String listSanPham;
    private String trangThai;

    public Bill(){}

    public Bill(String address, String email, String fullName, String listSanPham, String phone, String total, String trangThai) {
        this.address = address;
        this.email = email;
        this.fullName = fullName;
        this.listSanPham = listSanPham;
        this.phone = phone;
        this.total = total;
        this.trangThai = trangThai;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListSanPham() {
        return listSanPham;
    }

    public void setListSanPham(String listSanPham) {
        this.listSanPham = listSanPham;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", total='" + total + '\'' +
                ", listSanPham='" + listSanPham + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}


