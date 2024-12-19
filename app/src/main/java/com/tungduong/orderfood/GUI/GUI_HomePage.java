package com.tungduong.orderfood.GUI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tungduong.orderfood.BLL.CategoriesAdaptor_User;
import com.tungduong.orderfood.BLL.PopularAdaptor_User;
import com.tungduong.orderfood.BLL.ProductAdaptor_Admin;
import com.tungduong.orderfood.BLL.TypeFoodAdapter_Admin;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.DAO.DAO_Product;
import com.tungduong.orderfood.DAO.DAO_TypeFood;
import com.tungduong.orderfood.Entity.Product;
import com.tungduong.orderfood.Entity.TypeFood;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class GUI_HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView txt_nameUser;
    ImageView avatarUser;
    EditText txt_search;
    RecyclerView list_categories, list_productUser;
    DAO_Account daoAccount;
    DAO_TypeFood daoTypeFood;
    DAO_Product daoProduct;
    List<Product> productList;
    List<TypeFood> typeFoodList;
    CategoriesAdaptor_User adaptor_TypeFood;
    PopularAdaptor_User adaptor_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_home_page);
        AnhXa();
        LoadAllTypeFood();
        LoadAllProduct();

        daoTypeFood = new DAO_TypeFood();
        daoAccount = new DAO_Account();
        productList = new ArrayList<>();
        typeFoodList = new ArrayList<>();

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

        SharedPreferences sharedPreferences_email = GUI_HomePage.this.getSharedPreferences("Profile", MODE_PRIVATE);
        String email = sharedPreferences_email.getString("email", "Email chưa có");

        daoAccount.SearchAccountFormEmail(email, new DAO_Account.ListCallBackAccount() {
            @Override
            public void CallBack(String uiddb, String emaildb, String imagedb, String fullNamedb, String sdtdb, String roledb, String warningdb) {
                String uid = uiddb.toString().trim();
                String fullName = fullNamedb.toString().trim();
                txt_nameUser.setText(fullNamedb);

                if (GUI_HomePage.this != null && !GUI_HomePage.this.isFinishing()) {
                    Glide.with(GUI_HomePage.this).load(imagedb).circleCrop().error(R.drawable.error_avatar).into(avatarUser);
                }

                SharedPreferences sharedPreferences_uid = getSharedPreferences("ShoppingCart", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences_uid.edit();
                editor.putString("uid", uid);
                editor.putString("fullName",fullName);
                editor.apply();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        list_categories.setLayoutManager(gridLayoutManager);

        adaptor_TypeFood = new CategoriesAdaptor_User(this,typeFoodList);
        list_categories.setAdapter(adaptor_TypeFood);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        list_productUser.setLayoutManager(gridLayoutManager2);


        adaptor_product = new PopularAdaptor_User(this,productList);
        list_productUser.setAdapter(adaptor_product);
    }

    public void LoadAllProduct(){
        daoProduct = new DAO_Product();
        daoProduct.GetAllProduct(new DAO_Product.ListProductCallBack() {
            @Override
            public void CallBack(List<Product> product) {
                productList.clear();
                productList.addAll(product);

                if (adaptor_product != null){
                    adaptor_product.notifyDataSetChanged();
                }
                else {
                    adaptor_product = new PopularAdaptor_User(GUI_HomePage.this,productList);
                    list_productUser.setAdapter(adaptor_product);
                }
                if (productList.isEmpty()){
                    Toast.makeText(GUI_HomePage.this,"Danh sách đồ ăn rỗng",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void LoadAllTypeFood(){
        daoTypeFood = new DAO_TypeFood();
        daoTypeFood.GetAllTypeFood(new DAO_TypeFood.ListTypeFoodCallBack() {
            @Override
            public void CallBack(List<TypeFood> typeFood) {
                typeFoodList.clear();
                typeFoodList.addAll(typeFood);

                if (adaptor_TypeFood != null) {
                    adaptor_TypeFood.notifyDataSetChanged();
                } else {
                    adaptor_TypeFood = new CategoriesAdaptor_User(GUI_HomePage.this, typeFoodList);
                    list_categories.setAdapter(adaptor_TypeFood);
                }
                if (typeFoodList.isEmpty()) {
                    Toast.makeText(GUI_HomePage.this, "Không có loại món ăn nào!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AnhXa(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        txt_nameUser = findViewById(R.id.txt_nameUser);
        avatarUser = findViewById(R.id.image_user);
        txt_search = findViewById(R.id.txt_search);
        list_categories = findViewById(R.id.list_categories);
        list_productUser = findViewById(R.id.list_ProductUser);
    }
}