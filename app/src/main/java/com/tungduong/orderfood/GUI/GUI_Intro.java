package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tungduong.orderfood.R;

public class GUI_Intro extends AppCompatActivity {

    TextView tv_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);

        tv_intro = findViewById(R.id.tv_started);
        tv_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GUI_Intro.this,GUI_Login.class);
                startActivity(intent);

            }
        });
    }
}