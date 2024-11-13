package com.tungduong.orderfood.GUI;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
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
    Account account;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__account,container,false);
        AnhXa(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       LoadAllAccount();
        return view;
    }

    public void AnhXa(View view){
        recyclerView = view.findViewById(R.id.list_account);
    }

    public void LoadAllAccount(){
        daoAccount = new DAO_Account();
        daoAccount.GetAllAccounts(new DAO_Account.ListAccountCallBack() {
            @Override
            public void CallBack(List<Account> accounts) {
                accountList = accounts;
                adaptor_accounts = new AccountAdaptor_Admin(accountList, getContext());
                recyclerView.setAdapter(adaptor_accounts);
            }
        });
    }
}