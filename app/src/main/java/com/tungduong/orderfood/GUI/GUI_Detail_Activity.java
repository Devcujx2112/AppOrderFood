package com.tungduong.orderfood.GUI;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.tungduong.orderfood.R;

public class GUI_Detail_Activity extends AppCompatActivity {
    ImageView btnBack, img_detail;
    TextView txt_name, txt_moTa, txt_price, txt_min, txt_plus, txt_soLuong, txt_tongTien;
    AppCompatButton btn_add;
    String giaTien;
    int soLuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_detail);
        AnhXa();

        txt_soLuong.setText("1");
        soLuong = 1;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(this).load(bundle.getString("Image_Product")).into(img_detail);
            txt_name.setText(bundle.getString("Name_Product"));
            txt_moTa.setText(bundle.getString("MoTa_Product"));
            giaTien = bundle.getString("GiaTien_Product");
            txt_price.setText(giaTien + " VND");
        }

        txt_min.setOnClickListener(view -> {
            if (soLuong == 1) {
                Toast.makeText(GUI_Detail_Activity.this, "Không thể đặt ít hơn 1 sản phẩm", Toast.LENGTH_SHORT).show();
            } else {
                soLuong--;
                updateTongTien();
            }
        });

        txt_plus.setOnClickListener(view -> {
            soLuong++;
            updateTongTien();
        });

        updateTongTien();
    }

    private void updateTongTien() {
        int giaTienint = Integer.parseInt(giaTien);
        int TongTien = soLuong * giaTienint;
        txt_soLuong.setText(String.valueOf(soLuong));
        txt_tongTien.setText(TongTien + " VND");

    }

    public void AnhXa() {
        btnBack = findViewById(R.id.btn_back);
        img_detail = findViewById(R.id.detail_image);
        txt_name = findViewById(R.id.detail_name);
        txt_price = findViewById(R.id.detail_giaTien);
        txt_moTa = findViewById(R.id.detail_moTa);
        txt_min = findViewById(R.id.detail_minBtn);
        txt_plus = findViewById(R.id.detail_plusBtn);
        txt_soLuong = findViewById(R.id.detail_soLuong);
        txt_tongTien = findViewById(R.id.txt_tongTien);
        btn_add = findViewById(R.id.btn_addToCart);
    }
}