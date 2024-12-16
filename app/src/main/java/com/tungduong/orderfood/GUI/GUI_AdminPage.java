package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.R;

public class GUI_AdminPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView txt_fullName, txt_email;
    private DrawerLayout drawerLayout;
    ImageView image;
    private String image_url,fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_admin_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.main_admin);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Admin_Account()).commit();
            navigationView.setCheckedItem(R.id.nav_Product);
        }

        View headerView = navigationView.getHeaderView(0);
        AnhXa(headerView);

        SharedPreferences sharedPreferences_email = getSharedPreferences("Profile", MODE_PRIVATE);
        String email = sharedPreferences_email.getString("email", "Email chưa có");

        DAO_Account daoAccount = new DAO_Account();
        daoAccount.SearchAccountFormEmail(email, new DAO_Account.ListCallBackAccount() {
            @Override
            public void CallBack(String uid, String emaildb, String imagedb, String fullNamedb, String sdt, String roledb, String warningdb) {
                image_url = imagedb.toString().trim();
                fullName = fullNamedb.toString().trim();
                txt_fullName.setText(fullName);
                Glide.with(GUI_AdminPage.this).load(image_url).circleCrop().into(image);
            }

        });

        txt_email.setText(email);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_Account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Admin_Account()).commit();
                break;
            case R.id.nav_TypeFood:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Admin_TypeFood()).commit();
                break;
            case R.id.nav_Product:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Admin_Product()).commit();
                break;
            case R.id.nav_DonHang:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Admin_DonHang()).commit();
                break;
            case R.id.nav_ThongKe:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Admin_ThongKe()).commit();
                break;
            case R.id.nav_Profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Admin_Profile()).commit();
                break;
            case R.id.nav_Logout:
                Intent intent = new Intent(GUI_AdminPage.this,GUI_Login.class);
                startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public void AnhXa(View view){
        txt_email = view.findViewById(R.id.nav_email);
        txt_fullName = view.findViewById(R.id.nav_fullName);
        image = view.findViewById(R.id.nav_image);
    }
}