package com.tungduong.orderfood.GUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.tungduong.orderfood.R;

public class GUI_AdminPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Admin_Account()).commit();
            navigationView.setCheckedItem(R.id.nav_Product);
        }
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
                Toast.makeText(this,"Dang xuat",Toast.LENGTH_SHORT).show();
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
}