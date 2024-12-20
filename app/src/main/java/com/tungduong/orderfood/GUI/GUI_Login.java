package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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

public class GUI_Login extends AppCompatActivity {

    EditText txt_email, txt_passWord;
    Button btn_login;
    TextView tv_signin, tv_forgotPassWord;
    List<Account> accountList;
    DAO_Account daoAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        AnhXa();

        daoAccount = new DAO_Account();

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GUI_Login.this, GUI_SignUp.class);
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
                String email = txt_email.getText().toString().trim();
                String passWord = txt_passWord.getText().toString().trim();
                if (email.isEmpty() || passWord.isEmpty()) {
                    Toast.makeText(GUI_Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                daoAccount.CheckLogin(email, passWord, GUI_Login.this);
                txt_email.setText("");
                txt_passWord.setText("");

                daoAccount.SearchAccountFormEmail(email, new DAO_Account.ListCallBackAccount() {
                    @Override
                    public void CallBack(String uid, String emaildb, String image, String fullName, String sdt, String roledb, String warning) {
                        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putString("uid",uid);
                        editor.putString("fullName",fullName);
                        editor.putString("sdt",sdt);
                        editor.putString("image",image);
                        editor.putString("role",roledb);
                        editor.apply();
                    }
                });

            }
        });

        tv_forgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GUI_Login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgotpassword, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btn_takePassword).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getText().toString().trim();

                        // Kiểm tra email có hợp lệ không
                        if (TextUtils.isEmpty(userEmail)) {
                            Toast.makeText(GUI_Login.this, "Vui lòng nhập Email của bạn", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(GUI_Login.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            daoAccount.ForgotPassword(userEmail, GUI_Login.this, dialog);
                        }
                    }
                });

                // Xử lý sự kiện khi bấm nút "Hủy"
                dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());

                // Xóa nền của dialog (làm nền trong suốt)
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                // Hiển thị dialog
                dialog.show();
            }
        });
    }

    public void AnhXa() {
        txt_email = findViewById(R.id.txt_emailLogin);
        txt_passWord = findViewById(R.id.txt_passWordLogin);
        btn_login = findViewById(R.id.btn_login);
        tv_signin = findViewById(R.id.tv_signin);
        tv_forgotPassWord = findViewById(R.id.forgot_passWord);
        daoAccount = new DAO_Account();
        accountList = new ArrayList<>();
    }
}