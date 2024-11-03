package com.tungduong.orderfood.GUI;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.R;
import java.util.ArrayList;
import java.util.List;

public class GUI_Login extends AppCompatActivity {

    EditText txt_email,txt_passWord;
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
                String email = txt_email.getText().toString().trim();
                String passWord = txt_passWord.getText().toString().trim();
                if (email.isEmpty() || passWord.isEmpty()){
                    Toast.makeText(GUI_Login.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    return;
                }
                daoAccount.CheckLogin(email,passWord,GUI_Login.this);

            }
        });

        tv_forgotPassWord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ShowForgotPassword();
//            }
            FirebaseAuth auth = FirebaseAuth.getInstance();
            @Override
            public void onClick(View view) {
                // Tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(GUI_Login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgotpassword, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                // Xử lý sự kiện khi bấm nút "Lấy lại mật khẩu"
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
                        }

                        // Gửi email đặt lại mật khẩu qua Firebase
                        auth.sendPasswordResetEmail(userEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(GUI_Login.this, "Vui lòng kiểm tra Email để đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            // Kiểm tra lỗi và thông báo chi tiết hơn
                                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Không thể gửi Email";
                                            Toast.makeText(GUI_Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

                // Xử lý sự kiện khi bấm nút "Hủy"
                dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // Xóa nền của dialog (làm nền trong suốt)
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                // Hiển thị dialog
                dialog.show();
            }
        });

    }

//    public void ShowForgotPassword(){
//        Dialog dialog = new Dialog(this);
//
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_forgotpassword,null);
//        dialog.setContentView(dialogView);
//
//        EditText txt_fgEmail,txt_fgUserName;
//        Button btn_passWord,btn_cancel;
//
//        txt_fgEmail = dialogView.findViewById(R.id.emailBox);
//        txt_fgUserName = dialogView.findViewById(R.id.userNameBox);
//        btn_cancel = dialogView.findViewById(R.id.btn_cancel);
//        btn_passWord = dialogView.findViewById(R.id.btn_takePassword);
//
//        btn_passWord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userName = txt_fgUserName.getText().toString().trim();
//                String email = txt_fgEmail.getText().toString().trim();
//
//                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(userName)) {
//                    Toast.makeText(GUI_Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                daoAccount.ForgotPassword(userName,email,GUI_Login.this);
//                dialog.dismiss();
//            }
//        });
//
//        btn_cancel.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//
//        dialog.show();
//        //đặt kích thước dialog tùy biến với màn hình
//        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9); // Chiều rộng 90% màn hình
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // Chiều cao tự động
//        dialog.getWindow().setAttributes(layoutParams);
//
//    }
    public void AnhXa(){
        txt_email = findViewById(R.id.txt_emailLogin);
        txt_passWord = findViewById(R.id.txt_passWordLogin);
        btn_login = findViewById(R.id.btn_login);
        tv_signin = findViewById(R.id.tv_signin);
        tv_forgotPassWord = findViewById(R.id.forgot_passWord);

        daoAccount = new DAO_Account();
        accountList = new ArrayList<>();
    }
}