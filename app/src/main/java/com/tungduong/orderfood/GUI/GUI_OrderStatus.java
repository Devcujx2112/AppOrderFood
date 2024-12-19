package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tungduong.orderfood.BLL.BillAdaptor;
import com.tungduong.orderfood.BLL.ShoppingCart_Adaptor_User;
import com.tungduong.orderfood.DAO.DAO_Bill;
import com.tungduong.orderfood.Entity.Bill;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class GUI_OrderStatus extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView listBill;
    List<Bill> hoaDonList;
    BillAdaptor adaptorBill;
    DAO_Bill daoBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_order_status);
        AnhXa();

        hoaDonList = new ArrayList<>();
        daoBill = new DAO_Bill();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(GUI_OrderStatus.this,1);
        listBill.setLayoutManager(gridLayoutManager);

        adaptorBill = new BillAdaptor(GUI_OrderStatus.this,hoaDonList);
        listBill.setAdapter(adaptorBill);

        bottomNavigationView.setSelectedItemId(R.id.donHang_user);
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
                    return true;
                case R.id.profile_user:
                    startActivity(new Intent(getApplicationContext(),GUI_Profile.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
                    return true;
            }
            return false;
        });

        LoadAllBill();
    }

    public void LoadAllBill(){
        SharedPreferences sharedPreferences_role = GUI_OrderStatus.this.getSharedPreferences("ShoppingCart", MODE_PRIVATE);
        String fullName = sharedPreferences_role.getString("fullName", "");

        daoBill.GetAllBillUser(fullName,new DAO_Bill.ListBillCallBackUser() {
            @Override
            public void CallBack(List<Bill> list_bill) {
                hoaDonList.clear();
                hoaDonList.addAll(list_bill);

                if (adaptorBill != null) {
                    adaptorBill.notifyDataSetChanged();
                } else {
                    adaptorBill = new BillAdaptor(GUI_OrderStatus.this, hoaDonList);
                    listBill.setAdapter(adaptorBill);
                }
                if (list_bill.isEmpty()) {
                    Toast.makeText(GUI_OrderStatus.this, "Bạn chưa đặt đơn hàng nào", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AnhXa(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        listBill = findViewById(R.id.list_ProductCart);
    }
}