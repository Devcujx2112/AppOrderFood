package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.R;

public class ChiTiet_AccountAdmin extends AppCompatActivity {
    ImageView img_avatar;
    TextView txt_fullName, txt_email,txt_phone,txt_role;
    FloatingActionButton btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_account_admin);
        AnhXa();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Accounts")){
            Account account = intent.getParcelableExtra("Accounts");

            if (account != null) {
                String email = account.getEmail().toString().trim();
                String fullName = account.getFullName().toString().trim();
                String role = account.getRole().toString().trim();
                String phone = account.getPhone().toString().trim();
                String uid = account.getId().toString().trim();

                txt_fullName.setText(fullName);
                txt_email.setText(email);
                txt_role.setText(role);
                txt_phone.setText(phone);
                img_avatar.setImageResource(R.drawable.bgr);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DAO_Account daoAccount = new DAO_Account();
                        daoAccount.DeleteAccount(ChiTiet_AccountAdmin.this,uid);
                    }
                });
            }
            else {
                Toast.makeText(ChiTiet_AccountAdmin.this,"Không thể Intent data",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void AnhXa(){
        img_avatar = findViewById(R.id.ct_avatar);
        txt_email = findViewById(R.id.ct_email);
        txt_fullName = findViewById(R.id.ct_fullName);
        txt_role = findViewById(R.id.ct_role);
        txt_phone = findViewById(R.id.ct_phone);

        btn_delete = findViewById(R.id.fab_delete);
    }
}