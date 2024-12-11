package com.tungduong.orderfood.Entity;

public class TypeFood {
    private String idTypeFood;
    private String nameTypeFood;
    private String imageTypeFood;
    private String moTa;

    public TypeFood() {
    }

    public TypeFood(String idTypeFood, String nameTypeFood, String imageTypeFood, String moTa) {
        this.idTypeFood = idTypeFood;
        this.nameTypeFood = nameTypeFood;
        this.imageTypeFood = imageTypeFood;
        this.moTa = moTa;
    }

    public String getidTypeFood() {
        return idTypeFood;
    }

    public void setidTypeFood(String idTypeFood) {
        this.idTypeFood = TypeFood.this.idTypeFood;
    }

    public String getnameTypeFood() {
        return nameTypeFood;
    }

    public void setnameTypeFood(String nameTypeFood) {
        this.nameTypeFood = nameTypeFood;
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
                "idTypeFood='" + idTypeFood + '\'' +
                ", nameTypeFood='" + nameTypeFood + '\'' +
                ", imageTypeFood='" + imageTypeFood + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
