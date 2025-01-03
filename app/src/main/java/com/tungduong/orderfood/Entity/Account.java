package com.tungduong.orderfood.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Account implements Parcelable {
    private String id;
    private String fullName;
    private String role;
    private String email;
    private String phone;
    private String image;
    private String warning;

    public Account() {
    }

    public Account(String fullName, String role, String email, String phone, String image, String warning) {
        this.fullName = fullName;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.image = (image == null) ? "default_image_url" : image;
        this.warning = warning;
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + image + '\'' +
                ", warning='" + warning + '\'' +
                '}';
    }

    //return 0 nếu không có đối tượng nào được ghi
    @Override
    public int describeContents() {
        return 0;
    }

    //Ghi thuộc tính của đối tượng vào 1 phương thức
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(fullName);
        parcel.writeString(role);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(image);
        parcel.writeString(warning);
    }

    //Đọc và khôi phục lại đối tượng ban đầu
    protected Account(Parcel in) {
        id = in.readString();
        fullName = in.readString();
        role = in.readString();
        email = in.readString();
        phone = in.readString();
        image = in.readString();
        warning = in.readString();
    }
}
