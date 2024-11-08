package com.tungduong.orderfood.BLL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.GUI.Admin_Account;
import com.tungduong.orderfood.R;

import java.util.List;

public class AccountAdaptor_Admin extends RecyclerView.Adapter<MyViewHolder> {
    private Admin_Account adminAccount;
    private Context context;
    private List<Account> accountList;

    public AccountAdaptor_Admin(List<Account> accountList) {
        this.accountList = accountList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_account_admin,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Account account = accountList.get(position);
            holder.img_avatar.setImageResource(R.drawable.bgr);
            holder.email.setText(account.getEmail());
            holder.phone.setText(account.getPhone());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(context,)
                }
            });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView img_avatar;
    TextView email, phone;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        img_avatar = itemView.findViewById(R.id.img_Avatar);
        email = itemView.findViewById(R.id.account_email);
        phone = itemView.findViewById(R.id.account_phone);
    }
}
