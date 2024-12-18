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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tungduong.orderfood.BLL.ShoppingCart_Adaptor_User;
import com.tungduong.orderfood.BLL.TypeFoodAdapter_Admin;
import com.tungduong.orderfood.DAO.DAO_ShopingCart;
import com.tungduong.orderfood.Entity.ShopingCart;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class GUI_ShoppingCart extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView list_shoppingCart;
    AppCompatButton btn_thanhToan;
    DAO_ShopingCart daoShopingCart;
    List<ShopingCart> shopingCartList;
    ShoppingCart_Adaptor_User adaptor_cart;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_shopping_cart);
        AnhXa();

        SharedPreferences sharedPreferences = GUI_ShoppingCart.this.getSharedPreferences("ShoppingCart", MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", "");

        daoShopingCart = new DAO_ShopingCart();
        shopingCartList = new ArrayList<>();
        daoShopingCart = new DAO_ShopingCart();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(GUI_ShoppingCart.this,1);
        list_shoppingCart.setLayoutManager(gridLayoutManager);

        adaptor_cart = new ShoppingCart_Adaptor_User(GUI_ShoppingCart.this,shopingCartList);
        list_shoppingCart.setAdapter(adaptor_cart);

        bottomNavigationView.setSelectedItemId(R.id.gioHang_user);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_user:
                    startActivity(new Intent(getApplicationContext(),GUI_HomePage.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.gioHang_user:
                    return true;
                case R.id.donHang_user:
                    startActivity(new Intent(getApplicationContext(),GUI_OrderStatus.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.profile_user:
                    startActivity(new Intent(getApplicationContext(),GUI_Profile.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
                    return true;
            }
            return false;
        });

        btn_thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        LoadAllProductInCart();
    }


    public void LoadAllProductInCart(){
        daoShopingCart.GetAllProductInShoppingCart(uid,new DAO_ShopingCart.ListProductInShoppingCartCallBack() {
            @Override
            public void CallBack(List<ShopingCart> list_cart) {
                shopingCartList.clear();
                shopingCartList.addAll(list_cart);

                if (adaptor_cart != null) {
                    adaptor_cart.notifyDataSetChanged();
                } else {
                    adaptor_cart = new ShoppingCart_Adaptor_User(GUI_ShoppingCart.this, shopingCartList);
                    list_shoppingCart.setAdapter(adaptor_cart);
                }
                if (shopingCartList.isEmpty()) {
                    Toast.makeText(GUI_ShoppingCart.this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AnhXa() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btn_thanhToan = findViewById(R.id.btn_thanhToan);
        list_shoppingCart = findViewById(R.id.list_ProductCart);
    }
}
