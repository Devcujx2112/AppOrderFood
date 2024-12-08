package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tungduong.orderfood.BLL.TypeFoodAdapter_Admin;
import com.tungduong.orderfood.DAO.DAO_TypeFood;
import com.tungduong.orderfood.Entity.TypeFood;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_TypeFood extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    TypeFoodAdapter_Admin adaptorTypeFood;
    List<TypeFood> typeFoodList;
    DAO_TypeFood daoTypeFood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__type_food, container, false);
        AnhXa(view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        typeFoodList = new ArrayList<>();

        adaptorTypeFood = new TypeFoodAdapter_Admin(getContext(),typeFoodList);
        recyclerView.setAdapter(adaptorTypeFood);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Admin_AddTypeFood.class);
                startActivity(intent);
            }
        });

        LoadAllTypeFood();
        return view;
    }

    public void LoadAllTypeFood() {
        daoTypeFood = new DAO_TypeFood();
        daoTypeFood.GetAllTypeFood(new DAO_TypeFood.ListTypeFoodCallBack() {
            @Override
            public void CallBack(List<TypeFood> typeFood) {
                typeFoodList.clear();
                typeFoodList.addAll(typeFood);

                if (adaptorTypeFood != null) {
                    adaptorTypeFood.notifyDataSetChanged();
                } else {
                    adaptorTypeFood = new TypeFoodAdapter_Admin(getContext(), typeFoodList);
                    recyclerView.setAdapter(adaptorTypeFood);
                }
                if (typeFoodList.isEmpty()) {
                    Toast.makeText(getContext(), "Không có loại món ăn nào!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void AnhXa(View view){
         recyclerView = view.findViewById(R.id.list_typeFood);
         fab = view.findViewById(R.id.fab_addTypeFood);
    }
}