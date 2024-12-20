package com.tungduong.orderfood.GUI;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.R;

public class GUI_Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView avatar;
    EditText txt_fullName,txt_email,txt_phone;
    Button btn_xacNhan;
    TextView txt_forgot,txt_delete;
    DAO_Account daoAccount;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_profile);
        AnhXa();

        daoAccount = new DAO_Account();

        ActivityResultLauncher<Intent> activityResultLauncherImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),//khởi động 1 intent nhận kết quả
                new ActivityResultCallback<ActivityResult>() {//nhận kết quả trả về
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;
                            uri = data.getData();
                            avatar.setImageURI(uri);
                        } else {
                            Toast.makeText(GUI_Profile.this, "Không có ảnh nào được chọn", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncherImage.launch(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "defaultEmail");
        String fullName = sharedPreferences.getString("fullName", "defaultName");
        String sdt = sharedPreferences.getString("sdt", "defaultPhone");
        String image = sharedPreferences.getString("image", "defaultImage");
        String uid = sharedPreferences.getString("uid","uid");

        Glide.with(this).load(image).circleCrop().error(R.drawable.error_avatar).into(avatar);

        txt_fullName.setText(fullName);
        txt_phone.setText(sdt);
        txt_email.setText(email);

        String role = "user";
        String warning = "active";
        String encodedEmail = Base64.encodeToString(email.getBytes(), Base64.NO_WRAP);

        bottomNavigationView.setSelectedItemId(R.id.profile_user);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_user:
                    startActivity(new Intent(getApplicationContext(),GUI_HomePage.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.gioHang_user:
                    startActivity(new Intent(getApplicationContext(),GUI_ShoppingCart.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.donHang_user:
                    startActivity(new Intent(getApplicationContext(),GUI_OrderStatus.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.profile_user:
                    return true;
            }
            return false;
        });

        btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.isEmpty() || fullName.isEmpty() || sdt.isEmpty() || role.isEmpty() || warning.isEmpty()) {
                    Toast.makeText(GUI_Profile.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(GUI_Profile.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                daoAccount.SelectAvatarAccount(uid,uri,image,fullName,encodedEmail,sdt,role,warning,GUI_Profile.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(GUI_Profile.this);
                        builder.setTitle("Đăng nhập");
                        builder.setMessage("Vui lòng đăng nhập lại");

                        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GUI_Profile.this, GUI_Login.class);
                                startActivity(intent);
                            }
                        });

                        builder.show();
                    }
                }, 5000);
            }
        });
    }
    public void AnhXa(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        avatar = findViewById(R.id.profile_image);
        txt_fullName = findViewById(R.id.profile_fullname);
        txt_phone = findViewById(R.id.profile_phone);
        txt_email = findViewById(R.id.profile_email);
        btn_xacNhan = findViewById(R.id.btn_xacNhanUser);
        txt_forgot = findViewById(R.id.txt_forgot);
        txt_delete = findViewById(R.id.txt_deleteAcc);
    }
}