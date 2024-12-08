package com.tungduong.orderfood.GUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.tungduong.orderfood.R;

public class Admin_Update_Delete_TypeFood extends AppCompatActivity {
    ImageView update_imgTF;
    EditText update_idTF,update_nameTF,update_motaTF;
    Button update_TF,delete_TF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_update_delete_type_food);
        AnhXa();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            update_idTF.setText(bundle.getString("Id_TypeFood"));
            update_nameTF.setText(bundle.getString("Name_TypeFood"));
            update_motaTF.setText(bundle.getString("MoTa_TypeFood"));
            Glide.with(this).load(bundle.getString("Image")).into(update_imgTF);

        }

        update_TF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void UpdateTypeFood(){
        String id = update_idTF.getText().toString();
        String name = update_nameTF.getText().toString();
        String mota = update_motaTF.getText().toString();
    }

    public void AnhXa(){
        update_imgTF = findViewById(R.id.update_image);
        update_idTF = findViewById(R.id.update_idTF);
        update_nameTF = findViewById(R.id.update_nameTF);
        update_motaTF = findViewById(R.id.update_motaTF);

        update_TF = findViewById(R.id.update_TF);
        delete_TF = findViewById(R.id.delete_TF);
    }
}