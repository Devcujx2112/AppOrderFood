package com.tungduong.orderfood.GUI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.R;

public class ChiTiet_AccountAdmin extends AppCompatActivity {
    ImageView img_avatar;
    TextView txt_fullName, txt_email, txt_phone, txt_role, txt_warning;
    FloatingActionButton btn_warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_account_admin);
        AnhXa();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Accounts")) {
            Account account = intent.getParcelableExtra("Accounts");

            if (account != null) {
                String email = account.getEmail().toString().trim();
                String fullName = account.getFullName().toString().trim();
                String role = account.getRole().toString().trim();
                String phone = account.getPhone().toString().trim();
                String uid = account.getId().toString().trim();
                String warning = account.getWarning().toString().trim();
                String image = account.getImage();

                txt_fullName.setText(fullName);
                txt_email.setText(email);
                txt_role.setText(role);
                txt_phone.setText(phone);

                if ("ban".equals(warning)){
                    txt_warning.setText("Tài khoản đã bị vô hiệu hóa");
                    txt_warning.setBackgroundColor(ContextCompat.getColor(ChiTiet_AccountAdmin.this,R.color.red));
                }
                else if ("active".equals(warning)){
                    txt_warning.setText("Hoạt động");
                    txt_warning.setBackgroundColor(ContextCompat.getColor(ChiTiet_AccountAdmin.this,R.color.green));
                }

                Glide.with(this)
                        .load(image)
                        .placeholder(R.drawable.logo_admin)  // Ảnh mặc định
                        .error(R.drawable.error_avatar)  // Ảnh khi có lỗi
                        .into(img_avatar);


                btn_warning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(ChiTiet_AccountAdmin.this)
                                .setTitle("Xác nhận")
                                .setMessage("Bạn có muốn thay đổi trạng thái tài khoản không ?")
                                .setPositiveButton("Có", (dialog, which) -> {
                                    DAO_Account daoAccount = new DAO_Account();
                                    daoAccount.BanAccount(uid);

                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("isUpdated", true); // Gửi thông tin cần cập nhật
                                    setResult(RESULT_OK, resultIntent);
                                    finish(); // Quay lại Activity/Fragment trước đó
                                })
                                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });



            } else {
                Toast.makeText(ChiTiet_AccountAdmin.this, "Không thể Intent data", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void AnhXa() {
        img_avatar = findViewById(R.id.ct_avatar);

        txt_email = findViewById(R.id.ct_email);
        txt_fullName = findViewById(R.id.ct_fullName);
        txt_role = findViewById(R.id.ct_role);
        txt_phone = findViewById(R.id.ct_phone);
        txt_warning = findViewById(R.id.ct_warning);

        btn_warning = findViewById(R.id.fab_warning);
    }
}