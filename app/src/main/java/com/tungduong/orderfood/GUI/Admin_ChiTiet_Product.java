package com.tungduong.orderfood.GUI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tungduong.orderfood.R;

public class Admin_ChiTiet_Product extends AppCompatActivity {
    ImageView update_image;
    EditText update_masp, update_tensp,update_soLuong,update_giaTien,update_mota;
    Spinner update_typeFood;
    Button updatePD,deletePD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_chi_tiet_product);
        AnhXa();


    }

    public void AnhXa(){
        update_image = findViewById(R.id.update_ImgPD);
        update_masp = findViewById(R.id.update_maspPD);
        update_tensp = findViewById(R.id.update_namePD);
        update_soLuong = findViewById(R.id.update_soLuongPD);
        update_giaTien = findViewById(R.id.update_giaTienPD);
        update_mota = findViewById(R.id.update_moTaPD);
        update_typeFood = findViewById(R.id.update_typeFoodPD);
        updatePD = findViewById(R.id.updateButtonPD);
        deletePD = findViewById(R.id.deleteButtonPD);
    }
}