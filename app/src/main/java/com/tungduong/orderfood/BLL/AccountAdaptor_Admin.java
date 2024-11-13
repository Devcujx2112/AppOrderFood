package com.tungduong.orderfood.BLL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideExperiments;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.GUI.Admin_Account;
import com.tungduong.orderfood.GUI.ChiTiet_AccountAdmin;
import com.tungduong.orderfood.R;

import java.util.List;

public class AccountAdaptor_Admin extends RecyclerView.Adapter<MyViewHolder> {
    private Admin_Account adminAccount;
    private Context context;
    private List<Account> accountList;

    public AccountAdaptor_Admin(List<Account> accountList,Context context) {
        this.accountList = accountList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_account_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Account account = accountList.get(position);
        String imageUrl = account.getImage();

        if (holder.img_avatar != null && context != null) {
            Glide.with(holder.itemView.getContext()).load(imageUrl).placeholder(R.drawable.logo_admin).error(R.drawable.error_avatar).into(holder.img_avatar);
        }

        holder.email.setText(account.getEmail());
        holder.phone.setText(account.getPhone());

        String db_warning = account.getWarning();
        if ("ban".equals(db_warning)) {
            holder.warning.setText("Tài khoản đã bị vô hiệu hóa");
            holder.warning.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
        }
        else if ("active".equals(db_warning)) {
            holder.warning.setText("Hoạt động");
            holder.warning.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChiTiet_AccountAdmin.class);
            intent.putExtra("Accounts", account);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView img_avatar;
    TextView email, phone,warning;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        img_avatar = itemView.findViewById(R.id.img_Avatar);
        email = itemView.findViewById(R.id.account_email);
        phone = itemView.findViewById(R.id.account_phone);
        warning = itemView.findViewById(R.id.account_warning);
    }
}
