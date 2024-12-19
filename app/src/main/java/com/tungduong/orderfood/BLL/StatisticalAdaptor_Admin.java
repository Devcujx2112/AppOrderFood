package com.tungduong.orderfood.BLL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tungduong.orderfood.DAO.DAO_Bill;
import com.tungduong.orderfood.Entity.Bill;
import com.tungduong.orderfood.R;

import java.util.List;

public class StatisticalAdaptor_Admin extends RecyclerView.Adapter<Adaptor_Statistical> {
    private Context context;
    private List<Bill> listBill;

    public StatisticalAdaptor_Admin(Context context, List<Bill> listBill) {
        this.context = context;
        this.listBill = listBill;
    }

    @NonNull
    @Override
    public Adaptor_Statistical onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_thongke,parent,false);
        return new Adaptor_Statistical(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Statistical holder, int position) {
        Bill hoaDon = listBill.get(position);

        holder.txt_fullName.setText(hoaDon.getFullName());
        holder.txt_phone.setText(hoaDon.getPhone());
        holder.txt_address.setText(hoaDon.getAddress());
        holder.txt_price.setText(hoaDon.getTotal());
        holder.txt_trangThai.setText(hoaDon.getTrangThai());

    }

    @Override
    public int getItemCount() {
        return listBill.size();
    }
}

class Adaptor_Statistical extends RecyclerView.ViewHolder{
    TextView txt_fullName,txt_phone,txt_address,txt_price,txt_trangThai;

    public Adaptor_Statistical(@NonNull View itemView) {
        super(itemView);
        txt_fullName = itemView.findViewById(R.id.txt_fullNameTT);
        txt_phone = itemView.findViewById(R.id.txt_phoneTT);
        txt_address = itemView.findViewById(R.id.txt_addressTT);
        txt_price = itemView.findViewById(R.id.txt_priceTT);
        txt_trangThai = itemView.findViewById(R.id.txt_trangThai);
    }
}
