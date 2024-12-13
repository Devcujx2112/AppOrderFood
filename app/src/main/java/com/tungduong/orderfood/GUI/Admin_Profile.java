package com.tungduong.orderfood.GUI;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__profile, container, false);
        AnhXa(view);

        daoAccount = new DAO_Account();
        account = new Account();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserSession", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "Email chưa có");

        daoAccount.SearchAccountFormEmail(email, new DAO_Account.ListCallBackAccount() {
            @Override
            public void CallBack(String email, String fullNameCB, String sdt) {
                gmail.setText(email);
                fullName.setText(fullNameCB);
                phone.setText(sdt);
                Log.d("Callback","data:" + email+fullNameCB+sdt);
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