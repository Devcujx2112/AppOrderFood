package com.tungduong.orderfood.GUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tungduong.orderfood.BLL.BillAdaptor;
import com.tungduong.orderfood.BLL.StatisticalAdaptor_Admin;
import com.tungduong.orderfood.DAO.DAO_Bill;
import com.tungduong.orderfood.Entity.Bill;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_ThongKe extends Fragment {
    RecyclerView recyclerView;
    TextView txt_thuNhap;
    DAO_Bill daoBill;
    StatisticalAdaptor_Admin adaptorBill;
    List<Bill> listBill;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__thong_ke, container, false);
        AnhXa(view);

        daoBill = new DAO_Bill();
        listBill = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adaptorBill = new StatisticalAdaptor_Admin(getContext(),listBill);
        recyclerView.setAdapter(adaptorBill);

        LoadAllBill();
        return view;
    }

    public void LoadAllBill(){
        daoBill.ShowAllDoneBill(new DAO_Bill.ListBillDone() {
            @Override
            public void CallBack(List<Bill> list_Bill) {
                listBill.clear();
                listBill.addAll(list_Bill);

                if (adaptorBill != null) {
                    adaptorBill.notifyDataSetChanged();
                } else {
                    adaptorBill = new StatisticalAdaptor_Admin(getContext(), listBill);
                    recyclerView.setAdapter(adaptorBill);
                }
                if (list_Bill.isEmpty()) {
                    Toast.makeText(getContext(), "Chưa có đơn hàng nào được duyệt", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void TongTien(double thuNhap) {
                txt_thuNhap.setText(thuNhap+" VND");
            }
        });
    }

    public void AnhXa(View view){
        recyclerView = view.findViewById(R.id.list_bill);
        txt_thuNhap = view.findViewById(R.id.txt_thuNhap);
    }
}