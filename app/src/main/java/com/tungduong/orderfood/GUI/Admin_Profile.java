package com.tungduong.orderfood.GUI;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.R;

public class Admin_Profile extends Fragment {
    ImageView image;
    EditText fullName, gmail, phone;
    Button btn_xacNhan;
    TextView forgotPassword;
    FloatingActionButton delete;
    DAO_Account daoAccount;
    Account account;
    Uri uri;
    private String role,warning,fullName_acc,sdt_acc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__profile, container, false);
        AnhXa(view);

        daoAccount = new DAO_Account();
        account = new Account();

        ActivityResultLauncher<Intent> activityResultLauncherImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),//khởi động 1 intent nhận kết quả
                new ActivityResultCallback<ActivityResult>() {//nhận kết quả trả về
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;

                            uri = data.getData();
                            image.setImageURI(uri);
                        } else {
                            Toast.makeText(getContext(), "Không có ảnh nào được chọn", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncherImage.launch(intent);
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserSession", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "Email chưa có");

        String encodedEmail = Base64.encodeToString(email.getBytes(), Base64.NO_WRAP);

        daoAccount.SearchAccountFormEmail(email, new DAO_Account.ListCallBackAccount() {
            @Override
            public void CallBack(String email, String fullNameCB, String sdt, String roleCD, String warningCB) {
                gmail.setText(email);
                fullName_acc = fullNameCB.trim();
                fullName.setText(fullName_acc);
                sdt_acc = sdt.trim();
                phone.setText(sdt_acc);
                role = roleCD.trim();
                warning = warningCB.trim();

            }
        });

        btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.isEmpty() || fullName_acc.isEmpty() || sdt_acc.isEmpty() || role.isEmpty() || warning.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (uri == null) {
                    Toast.makeText(getContext(), "Vui lòng thêm ảnh của loại sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserSession", MODE_PRIVATE);
                String imageUrl = sharedPreferences.getString("Image_Account","");

                daoAccount.SelectAvatarAccount(uri,imageUrl,fullName_acc,encodedEmail,sdt_acc,role,warning,getContext());
            }
        });
        return view;
    }



    public void AnhXa(View view){
        image = view.findViewById(R.id.profileImage);
        fullName = view.findViewById(R.id.fullName);
        gmail = view.findViewById(R.id.gmail);
        phone = view.findViewById(R.id.phoneNumber);
        btn_xacNhan = view.findViewById(R.id.btn_xacNhan);
        forgotPassword = view.findViewById(R.id.forgot);
        delete = view.findViewById(R.id.fab_deleteAcc);
    }
}