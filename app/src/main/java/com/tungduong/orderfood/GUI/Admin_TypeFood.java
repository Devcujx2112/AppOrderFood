package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tungduong.orderfood.R;

public class Admin_TypeFood extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__type_food, container, false);
        AnhXa(view);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Admin_AddTypeFood.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }

    public void AnhXa(View view){
         recyclerView = view.findViewById(R.id.list_typeFood);
         fab = view.findViewById(R.id.fab_addTypeFood);
    }
}