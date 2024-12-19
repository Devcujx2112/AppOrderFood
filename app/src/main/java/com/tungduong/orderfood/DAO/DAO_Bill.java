package com.tungduong.orderfood.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tungduong.orderfood.BLL.BillAdaptor;
import com.tungduong.orderfood.Entity.Bill;
import com.tungduong.orderfood.Entity.ShopingCart;

import java.util.ArrayList;
import java.util.List;

public class DAO_Bill {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Bill");

    public interface ListBillCallBackAdmin {
        void CallBack(List<Bill> list_bill);
    }

    public void GetAllBillAdmin(ListBillCallBackAdmin callBack) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Bill> billList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Bill bill = data.getValue(Bill .class);
                    if (bill != null) {
                        billList.add(bill);
                    }
                }
                callBack.CallBack(billList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Show All Bill", error.getMessage().trim());
            }
        });
    }

    public interface ListBillCallBackUser {
        void CallBack(List<Bill> list_bill);
    }

    public void GetAllBillUser(String fullname, ListBillCallBackUser callBack) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Bill> billList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Bill bill = data.getValue(Bill.class);
                    if (bill != null && fullname.equals(bill.getFullName())) {
                        billList.add(bill);
                    }
                }
                callBack.CallBack(billList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("GetAllBillUser", error.getMessage().trim());
            }
        });
    }

}
