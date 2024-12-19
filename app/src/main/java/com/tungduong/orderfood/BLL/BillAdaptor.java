package com.tungduong.orderfood.BLL;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tungduong.orderfood.DAO.DAO_Account;
import com.tungduong.orderfood.Entity.Bill;
import com.tungduong.orderfood.GUI.GUI_HomePage;
import com.tungduong.orderfood.R;

import java.util.Arrays;
import java.util.List;

public class BillAdaptor extends RecyclerView.Adapter<Adaptor_Bill> {
    private Context context;
    private List<Bill> hoaDonList;

    public BillAdaptor(Context context, List<Bill> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public Adaptor_Bill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hoadon, parent, false);
        return new Adaptor_Bill(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Bill holder, int position) {
        Bill hoaDon = hoaDonList.get(position);

        SharedPreferences sharedPreferences_email = context.getSharedPreferences("Profile", MODE_PRIVATE);
        String role = sharedPreferences_email.getString("role", "");
        Log.d("roleAcc login",""+role);

        if (role.equals("admin")) {
            holder.txt_trangThai.setEnabled(true);
        } else {
            holder.txt_trangThai.setEnabled(false);
        }
        String[] data_TrangThai = {"Đang chờ", "Duyệt", "Từ chối"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data_TrangThai);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.txt_trangThai.setAdapter(adapter);

        holder.txt_fullName.setText(hoaDon.getFullName());
        holder.txt_phone.setText(hoaDon.getPhone());
        holder.txt_address.setText(hoaDon.getAddress());
        holder.txt_price.setText(hoaDon.getTotal());
        holder.txt_listsp.setText(hoaDon.getListSanPham());

        String trangThai = hoaDon.getTrangThai();
        int positionspn = Arrays.asList(data_TrangThai).indexOf(trangThai);
        holder.txt_trangThai.setSelection(positionspn);
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }
}

class Adaptor_Bill extends RecyclerView.ViewHolder {
    TextView txt_fullName, txt_phone, txt_address, txt_listsp, txt_price;
    Spinner txt_trangThai;

    public Adaptor_Bill(@NonNull View itemView) {
        super(itemView);
        txt_fullName = itemView.findViewById(R.id.txt_titleHD);
        txt_phone = itemView.findViewById(R.id.txt_phoneHD);
        txt_address = itemView.findViewById(R.id.txt_addressHD);
        txt_price = itemView.findViewById(R.id.txt_priceHD);
        txt_listsp = itemView.findViewById(R.id.txt_listspHD);
        txt_trangThai = itemView.findViewById(R.id.ccb_trangThaiHD);
    }
}
