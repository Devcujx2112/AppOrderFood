package com.tungduong.orderfood.GUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tungduong.orderfood.BLL.BillAdaptor;
import com.tungduong.orderfood.BLL.ProductAdaptor_Admin;
import com.tungduong.orderfood.DAO.DAO_Bill;
import com.tungduong.orderfood.Entity.Bill;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_DonHang extends Fragment {
    RecyclerView recyclerView;
    BillAdaptor adaptorBill;
    DAO_Bill daoBill;
    List<Bill> listBill;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__don_hang, container, false);
        AnhXa(view);

        daoBill = new DAO_Bill();
        listBill = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adaptorBill = new BillAdaptor(getContext(),listBill);
        recyclerView.setAdapter(adaptorBill);

        LoadAllBill();
        return view;
    }

    public void LoadAllBill(){
        daoBill.GetAllBillAdmin(new DAO_Bill.ListBillCallBackAdmin() {
            @Override
            public void CallBack(List<Bill> list_bill) {
                listBill.clear();
                listBill.addAll(list_bill);

                if (adaptorBill != null) {
                    adaptorBill.notifyDataSetChanged();
                } else {
                    adaptorBill = new BillAdaptor(getContext(), listBill);
                    recyclerView.setAdapter(adaptorBill);
                }
                if (list_bill.isEmpty()) {
                    Toast.makeText(getContext(), "Chưa có đơn hàng nào", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AnhXa(View view){
        recyclerView = view.findViewById(R.id.list_ProductCartAdmin);
    }
}