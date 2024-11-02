package com.tungduong.orderfood.GUI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GUI_Login extends AppCompatActivity {

    EditText txt_userName,txt_passWord;
    Button btn_login;
    TextView tv_signin,tv_forgotPassWord;
    List<Account> accountList;
    DAO_Account daoAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        AnhXa();

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GUI_Login.this,GUI_SignUp.class);
                startActivity(intent);
            }
        });

        tv_forgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txt_userName.getText().toString().trim();
                String passWord = txt_passWord.getText().toString().trim();
                if (userName.isEmpty() || passWord.isEmpty()){
                    Toast.makeText(GUI_Login.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    return;
                }
                daoAccount.CheckLogin(userName,passWord,GUI_Login.this);

            }
        });

        tv_forgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowForgotPassword();
            }
        });
    }

    public void ShowForgotPassword(){
        Dialog dialog = new Dialog(this);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_forgotpassword,null);
        dialog.setContentView(dialogView);

        EditText txt_fgEmail,txt_fgUserName;
        Button btn_passWord,btn_cancel;

        txt_fgEmail = dialogView.findViewById(R.id.emailBox);
        txt_fgUserName = dialogView.findViewById(R.id.userNameBox);
        btn_cancel = dialogView.findViewById(R.id.btn_cancel);
        btn_passWord = dialogView.findViewById(R.id.btn_takePassword);

        btn_passWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txt_fgUserName.getText().toString().trim();
                String email = txt_fgEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(userName)) {
                    Toast.makeText(GUI_Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                daoAccount.ForgotPassword(userName,email,GUI_Login.this);
            }
        });

        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        //đặt kích thước dialog tùy biến với màn hình
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9); // Chiều rộng 90% màn hình
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // Chiều cao tự động
        dialog.getWindow().setAttributes(layoutParams);

    }
    public void AnhXa(){
        txt_userName = findViewById(R.id.txt_userNameLogin);
        txt_passWord = findViewById(R.id.txt_passWordLogin);
        btn_login = findViewById(R.id.btn_login);
        tv_signin = findViewById(R.id.tv_signin);
        tv_forgotPassWord = findViewById(R.id.forgot_passWord);

        daoAccount = new DAO_Account();
        accountList = new ArrayList<>();
    }
}