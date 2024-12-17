package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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
import com.tungduong.orderfood.BLL.PopularAdaptor_User;
import com.tungduong.orderfood.BLL.ProductAdaptor_Admin;
import com.tungduong.orderfood.DAO.DAO_Product;
import com.tungduong.orderfood.Entity.Product;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class GUI_Viewholder_TypeFood_User extends AppCompatActivity {
    ImageView imgTypeFood;
    TextView title;
    RecyclerView list_product;
    DAO_Product daoProduct;
    List<Product> productList;
    PopularAdaptor_User adaptor_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_viewholder_type_food_user);
        AnhXa();

        daoProduct = new DAO_Product();
        productList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        list_product.setLayoutManager(gridLayoutManager);

        adaptor_product = new PopularAdaptor_User(GUI_Viewholder_TypeFood_User.this,productList);
        list_product.setAdapter(adaptor_product);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(this).load(bundle.getString("Image_TypeFood")).into(imgTypeFood);
            String typeFood = bundle.getString("Name_TypeFood");
            title.setText(typeFood);

            daoProduct.SearchProductWhereTypeFood(typeFood, GUI_Viewholder_TypeFood_User.this, new DAO_Product.ListProductWhereTypeFood() {
                @Override
                public void ListProduct(List<Product> product) {
                    productList.clear();
                    productList.addAll(product);

                    if (adaptor_product != null){
                        adaptor_product.notifyDataSetChanged();
                    }
                    else {
                        adaptor_product = new PopularAdaptor_User(GUI_Viewholder_TypeFood_User.this,productList);
                        list_product.setAdapter(adaptor_product);
                    }
                    if (productList.isEmpty()){
                        Toast.makeText(GUI_Viewholder_TypeFood_User.this,"Không có sản phẩm nào phù hợp",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }
    public void AnhXa(){
        imgTypeFood = findViewById(R.id.img_typeFood);
        title = findViewById(R.id.title_typeFood);
        list_product = findViewById(R.id.list_Product1);
    }
}