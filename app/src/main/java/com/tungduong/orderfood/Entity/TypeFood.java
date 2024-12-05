package com.tungduong.orderfood.Entity;

public class TypeFood {
    private String IDTypeFood;
    private String NameTypeFood;
    private String imageTypeFood;
    private String moTa;

    public TypeFood(){}

    public TypeFood(String IDTypeFood, String NameTypeFood, String imageTypeFood, String moTa) {
        this.IDTypeFood = IDTypeFood;
        this.NameTypeFood = NameTypeFood;
        this.imageTypeFood = imageTypeFood;
        this.moTa = moTa;
    }

    public String getIDTypeFood() {
        return IDTypeFood;
    }

    public void setIDTypeFood(String IDTypeFood) {
        this.IDTypeFood = IDTypeFood;
    }

    public String getNameTypeFood() {
        return NameTypeFood;
    }

    public void setNameTypeFood(String NameTypeFood) {
        this.NameTypeFood = NameTypeFood;
    }

    public String getimageTypeFood() {
        return imageTypeFood;
    }

    public void setimageTypeFood(String imageTypeFood) {
        this.imageTypeFood = imageTypeFood;
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
                "IDTypeFood='" + IDTypeFood + '\'' +
                ", NameTypeFood='" + NameTypeFood + '\'' +
                ", imageTypeFood='" + imageTypeFood + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
