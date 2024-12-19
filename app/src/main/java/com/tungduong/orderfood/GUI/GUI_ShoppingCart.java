package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tungduong.orderfood.BLL.ShoppingCart_Adaptor_User;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.DAO.DAO_ShopingCart;
import com.tungduong.orderfood.Entity.Bill;
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
    DAO_Account daoAccount;
    String listSanPham;

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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(GUI_ShoppingCart.this, 1);
        list_shoppingCart.setLayoutManager(gridLayoutManager);

        adaptor_cart = new ShoppingCart_Adaptor_User(GUI_ShoppingCart.this, shopingCartList);
        list_shoppingCart.setAdapter(adaptor_cart);

        bottomNavigationView.setSelectedItemId(R.id.gioHang_user);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_user:
                    startActivity(new Intent(getApplicationContext(), GUI_HomePage.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.gioHang_user:
                    return true;
                case R.id.donHang_user:
                    startActivity(new Intent(getApplicationContext(), GUI_OrderStatus.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.profile_user:
                    startActivity(new Intent(getApplicationContext(), GUI_Profile.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
            }
            return false;
        });

        btn_thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GUI_ShoppingCart.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_thanhtoan, null);
                EditText emailBox = dialogView.findViewById(R.id.tt_email);
                EditText fullName = dialogView.findViewById(R.id.tt_fullName);
                EditText phone = dialogView.findViewById(R.id.tt_phone);
                EditText address = dialogView.findViewById(R.id.tt_address);
                EditText tongTien = dialogView.findViewById(R.id.tt_tongTien);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                SharedPreferences sharedPreferences_email = GUI_ShoppingCart.this.getSharedPreferences("Profile", MODE_PRIVATE);
                String email = sharedPreferences_email.getString("email", "Email chưa có");
                emailBox.setText(email);

                daoAccount = new DAO_Account();
                daoAccount.SearchAccountFormEmail(email, new DAO_Account.ListCallBackAccount() {
                    @Override
                    public void CallBack(String uiddb, String email, String image, String fullNamedb, String sdtdb, String role, String warning) {
                        uid = uiddb.toString().trim();
                        fullName.setText(fullNamedb);
                        phone.setText(sdtdb);
                    }
                });

                daoShopingCart.GetMoney(uid, new DAO_ShopingCart.MoneyCallback() {
                    @Override
                    public void TongTienDonHang(double total) {
                        tongTien.setText(total+" VND");
                    }

                    @Override
                    public void ProductInShoppingCart(List<String> tensp) {
                        StringBuilder result = new StringBuilder();
                        for (String item : tensp) {
                            result.append(item).append(", "); // Thêm dấu phẩy sau mỗi tên sản phẩm
                        }
                        // Loại bỏ dấu phẩy cuối cùng (nếu có)
                        if (result.length() > 0) {
                            result.setLength(result.length() - 2);
                        }
                        listSanPham = result.toString();
                    }
                });

                dialogView.findViewById(R.id.btn_ttThanhToan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String addressText = address.getText().toString().trim();
                        if (addressText.isEmpty()) {
                            Toast.makeText(GUI_ShoppingCart.this, "Vui lòng không để trống địa chỉ", Toast.LENGTH_SHORT).show();
                        } else {
                            String txt_fullName = fullName.getText().toString().trim();
                            String txt_phone = phone.getText().toString().trim();
                            String txt_tongTien = tongTien.getText().toString().trim();
                            String txt_trangThai = "Đang chờ";
                            Bill hoaDon = new Bill(addressText,email,txt_fullName,listSanPham,txt_phone,txt_tongTien,txt_trangThai);
                            daoShopingCart.AddHoaDon(hoaDon,GUI_ShoppingCart.this,uid);
                            dialog.dismiss();
                            LoadAllProductInCart();

                            AlertDialog.Builder builder = new AlertDialog.Builder(GUI_ShoppingCart.this);
                            View dialogView = getLayoutInflater().inflate(R.layout.dialog_banking, null);
                            builder.setView(dialogView);
                            AlertDialog dialogBanking = builder.create();
                            dialogBanking.show();
                        }
                    }

                });
                dialogView.findViewById(R.id.btn_ttcancel).setOnClickListener(v -> dialog.dismiss());

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                dialog.show();
            }
        });

        LoadAllProductInCart();
    }

    public void LoadAllProductInCart() {
        daoShopingCart.GetAllProductInShoppingCart(uid, new DAO_ShopingCart.ListProductInShoppingCartCallBack() {
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
