package com.tungduong.orderfood.BLL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.R;
import java.util.List;

public class AccountAdaptor_Admin extends RecyclerView.Adapter<Adaptor_Account_Admin> {
    private Context context;
    private List<Account> accountList;
    private OnItemClickListener listener;

    // Constructor
    public AccountAdaptor_Admin(List<Account> accountList, Context context) {
        this.accountList = accountList;
        this.context = context;
    }

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(Account account);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Phương thức để cập nhật danh sách account
    public void updateData(List<Account> newAccountList) {
        this.accountList = newAccountList;
        notifyDataSetChanged(); // Cập nhật lại RecyclerView
    }

    @NonNull
    @Override
    public Adaptor_Account_Admin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_account_admin, parent, false);
        return new Adaptor_Account_Admin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Account_Admin holder, int position) {
        Account account = accountList.get(position);
        String imageUrl = account.getImage();

        // Load ảnh từ URL
        if (holder.img_avatar != null && context != null) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.error_avatar)
                    .into(holder.img_avatar);
        }

        holder.email.setText(account.getEmail());
        holder.phone.setText(account.getPhone());

        // Hiển thị trạng thái tài khoản
        String db_warning = account.getWarning();
        if ("ban".equals(db_warning)) {
            holder.warning.setText("Tài khoản đã bị vô hiệu hóa");
            holder.warning.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        } else if ("active".equals(db_warning)) {
            holder.warning.setText("Hoạt động");
            holder.warning.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }

        // Xử lý sự kiện click item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(account); // Gửi sự kiện đến callback
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }
}

class Adaptor_Account_Admin extends RecyclerView.ViewHolder {
    ImageView img_avatar;
    TextView email, phone, warning;

    public Adaptor_Account_Admin(@NonNull View itemView) {
        super(itemView);
        img_avatar = itemView.findViewById(R.id.img_Avatar);
        email = itemView.findViewById(R.id.account_email);
        phone = itemView.findViewById(R.id.account_phone);
        warning = itemView.findViewById(R.id.account_warning);
    }
}
