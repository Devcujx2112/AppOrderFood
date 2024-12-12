package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tungduong.orderfood.BLL.ProductAdaptor_Admin;
import com.tungduong.orderfood.DAO.DAO_Product;
import com.tungduong.orderfood.Entity.Product;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_Product extends Fragment {
    ImageView img;
    EditText searchProduct;
    FloatingActionButton reloadProduct, addProduct;
    RecyclerView recyclerView;
    DAO_Product daoProduct;
    List<Product> productList;
    ProductAdaptor_Admin adaptorProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__product, container, false);
        AnhXa(view);

        productList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adaptorProduct = new ProductAdaptor_Admin(getContext(),productList);
        recyclerView.setAdapter(adaptorProduct);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Admin_AddProduct.class);
                startActivity(intent);
            }
        });

        reloadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadAllProduct();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensp = searchProduct.getText().toString().trim();
                Log.d("Admin_Product", "Searching for: " + tensp);  // Log tên sản phẩm
                if (tensp.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                daoProduct = new DAO_Product();
                daoProduct.SearchProductFormTenSP(tensp, new DAO_Product.ListProductSearch() {
                    @Override
                    public void ListProduct(List<Product> product) {
                        Log.d("Admin_Product", "Products found: " + product.size());  // Log số sản phẩm tìm thấy

                        productList.clear();
                        productList.addAll(product);

                        if (adaptorProduct != null){
                            adaptorProduct.notifyDataSetChanged();
                        }
                        else {
                            adaptorProduct = new ProductAdaptor_Admin(getContext(),productList);
                            recyclerView.setAdapter(adaptorProduct);
                        }
                        if (productList.isEmpty()){
                            Toast.makeText(getContext(),"Không có sản phẩm nào phù hợp",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        LoadAllProduct();
        return view;
    }

    public void LoadAllProduct(){
        daoProduct = new DAO_Product();
        daoProduct.GetAllProduct(new DAO_Product.ListProductCallBack() {
            @Override
            public void CallBack(List<Product> product) {
                productList.clear();
                productList.addAll(product);

                if (adaptorProduct != null){
                    adaptorProduct.notifyDataSetChanged();
                }
                else {
                    adaptorProduct = new ProductAdaptor_Admin(getContext(),productList);
                    recyclerView.setAdapter(adaptorProduct);
                }
                if (productList.isEmpty()){
                    Toast.makeText(getContext(),"Danh sách đồ ăn rỗng",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AnhXa(View view){
        searchProduct = view.findViewById(R.id.search_product);
        reloadProduct = view.findViewById(R.id.fab_reloadProduct);
        addProduct = view.findViewById(R.id.fab_addProduct);
        recyclerView = view.findViewById(R.id.list_Product);
        img = view.findViewById(R.id.search_icon);
    }
}