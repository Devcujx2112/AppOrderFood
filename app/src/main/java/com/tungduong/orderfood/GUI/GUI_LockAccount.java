package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.tungduong.orderfood.R;

public class GUI_LockAccount extends AppCompatActivity {

Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_lock_account);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GUI_LockAccount.this,GUI_Login.class);
                startActivity(intent);
            }
        });
    }
    public void AnhXa(){
        btn_back = findViewById(R.id.btn_back);
    }
}