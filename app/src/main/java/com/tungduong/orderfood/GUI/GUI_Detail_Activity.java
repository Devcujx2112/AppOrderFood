package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.tungduong.orderfood.DAO.DAO_ShopingCart;
import com.tungduong.orderfood.Entity.ShopingCart;
import com.tungduong.orderfood.R;

public class GUI_Detail_Activity extends AppCompatActivity {
    ImageView btnBack, img_detail;
    TextView txt_name, txt_moTa, txt_price, txt_min, txt_plus, txt_soLuong, txt_tongTien;
    AppCompatButton btn_add;
    String giaTien;
    int soLuong;
    private String uid,imageURL;
    DAO_ShopingCart daoShopingCart;
    ShopingCart shopingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_detail);
        AnhXa();
        daoShopingCart = new DAO_ShopingCart();
        shopingCart = new ShopingCart();

        txt_soLuong.setText("1");
        soLuong = 1;

        SharedPreferences sharedPreferences = GUI_Detail_Activity.this.getSharedPreferences("ShoppingCart", MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", "");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageURL = bundle.getString("Image_Product");
            Glide.with(this).load(imageURL).into(img_detail);
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

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProductInShoppingCart();
            }
        });

        updateTongTien();
    }

    public void AddProductInShoppingCart(){
        String tensp = txt_name.getText().toString().trim();
        String giaTienVND = txt_price.getText().toString().trim();
        String giaTien = giaTienVND.replace("VND","").trim();

        shopingCart = new ShopingCart(tensp,uid,giaTien,soLuong,imageURL);
        daoShopingCart.AddProductInShoppingCart(shopingCart,GUI_Detail_Activity.this);
        finish();
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