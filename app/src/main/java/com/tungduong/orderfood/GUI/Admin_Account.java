package com.tungduong.orderfood.GUI;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tungduong.orderfood.BLL.AccountAdaptor_Admin;
import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.R;

import java.util.List;

public class Admin_Account extends Fragment {

    RecyclerView recyclerView;
    AccountAdaptor_Admin adaptor_accounts;
    List<Account> accountList;
    DAO_Account daoAccount;
    private static final int REQUEST_CODE_DETAIL = 100;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__account, container, false);
        AnhXa(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LoadAllAccount();
        return view;
    }

    public void AnhXa(View view) {
        recyclerView = view.findViewById(R.id.list_account);
    }

    public void LoadAllAccount() {
        daoAccount = new DAO_Account();
        daoAccount.GetAllAccounts(new DAO_Account.ListAccountCallBack() {
            @Override
            public void CallBack(List<Account> accounts) {
                accountList = accounts;
                if (adaptor_accounts == null) {
                    adaptor_accounts = new AccountAdaptor_Admin(accountList, getContext());
                    adaptor_accounts.setOnItemClickListener(new AccountAdaptor_Admin.OnItemClickListener() {
                        @Override
                        public void onItemClick(Account account) {
                            // Khi người dùng click vào account, mở Activity chi tiết
                            Intent intent = new Intent(getContext(), ChiTiet_AccountAdmin.class);
                            intent.putExtra("Accounts", account);
                            startActivityForResult(intent, REQUEST_CODE_DETAIL); // Yêu cầu kết quả sau khi sửa
                        }
                    });
                    recyclerView.setAdapter(adaptor_accounts);
                } else {
                    adaptor_accounts.updateData(accounts); // Cập nhật dữ liệu
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DETAIL && resultCode == RESULT_OK) {
            boolean isUpdated = data != null && data.getBooleanExtra("isUpdated", false);
            if (isUpdated) {
                LoadAllAccount();
            }
        }
    }
}
