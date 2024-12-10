package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tungduong.orderfood.R;

public class Admin_Product extends Fragment {
    EditText searchProduct;
    FloatingActionButton reloadProduct, addProduct;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__product, container, false);
        AnhXa(view);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Admin_AddProduct.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void AnhXa(View view){
        searchProduct = view.findViewById(R.id.search_product);
        reloadProduct = view.findViewById(R.id.fab_reloadProduct);
        addProduct = view.findViewById(R.id.fab_addProduct);
        recyclerView = view.findViewById(R.id.list_Product);
    }
}