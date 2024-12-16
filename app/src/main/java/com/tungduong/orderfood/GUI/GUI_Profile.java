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

public class GUI_Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_profile);
        AnhXa();

        bottomNavigationView.setSelectedItemId(R.id.profile_user);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_user:
                    startActivity(new Intent(getApplicationContext(),GUI_HomePage.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
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
                    return true;
            }
            return false;
        });
    }
    public void AnhXa(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }
}