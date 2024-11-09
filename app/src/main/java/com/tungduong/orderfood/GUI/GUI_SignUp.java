package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.R;

public class GUI_SignUp extends AppCompatActivity {

    EditText txt_phone, txt_password, txt_fullName, txt_email;
    Button btn_signUp;
    TextView tv_login;
    DAO_Account daoAccount;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        AnhXa();

        tv_login.setOnClickListener(v -> {
            Intent intent = new Intent(GUI_SignUp.this, GUI_Login.class);
            startActivity(intent);
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertAccount();
                Intent intent = new Intent(GUI_SignUp.this, GUI_Login.class);
                startActivity(intent);
            }
        });
    }

    public void InsertAccount() {
        String phone = txt_phone.getText().toString().trim();
        String fullName = txt_fullName.getText().toString().trim();
        String passWord = txt_password.getText().toString().trim();
        String role = "user";
        String email = txt_email.getText().toString().trim();
        String warning = "active";
        String image = null;
        if (phone.isEmpty() || passWord.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            Toast.makeText(GUI_SignUp.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Log.d("AccountInfo", "image: " + image + ", warning: " + warning);
        Account account = new Account(fullName, role, email, phone, image, warning);
        daoAccount.InsertAccount(account, passWord, GUI_SignUp.this);

    }


    public void AnhXa() {
        txt_phone = findViewById(R.id.txt_phone);
        txt_password = findViewById(R.id.txt_passWord);
        txt_fullName = findViewById(R.id.txt_fullName);
        txt_email = findViewById(R.id.txt_email);

        btn_signUp = findViewById(R.id.btn_signUp);
        tv_login = findViewById(R.id.tv_login);

        daoAccount = new DAO_Account();
    }
}