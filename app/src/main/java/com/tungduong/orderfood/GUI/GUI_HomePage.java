package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tungduong.orderfood.R;

public class GUI_HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_home_page);
        AnhXa();

        bottomNavigationView.setSelectedItemId(R.id.home_user);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_user:
                    return true;
                case R.id.gioHang_user:
                    startActivity(new Intent(getApplicationContext(),GUI_ShoppingCart.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
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
    }

    public void AnhXa(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }
}