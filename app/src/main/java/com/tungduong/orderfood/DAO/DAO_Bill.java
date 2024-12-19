package com.tungduong.orderfood.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tungduong.orderfood.Entity.Bill;

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
                    Bill bill = data.getValue(Bill.class);
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

    public void UpdateTrangThaiDonHang(String id, String trangThai, Context context) {
        databaseReference.child(id).child("trangThai").setValue(trangThai).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Đã lưu", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Cập nhật trạng thái thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface ListBillDone{
        void CallBack(List<Bill> listBill);
        void TongTien(double thuNhap);
    }

    public void ShowAllDoneBill(ListBillDone listBillDone) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Bill> listBill = new ArrayList<>();
                double totalIncome = 0;

                for (DataSnapshot data : snapshot.getChildren()) {
                    Bill bill = data.getValue(Bill.class);
                    if (bill != null && "Duyệt".equals(bill.getTrangThai())) {
                        listBill.add(bill);

                        try {
                            double billValue = Double.parseDouble(bill.getTotal().replace("VND", "").trim());
                            totalIncome += billValue;
                        } catch (NumberFormatException e) {
                            Log.e("ShowAllDoneBill", "Lỗi khi parse tổng tiền: " + e.getMessage());
                        }
                    }
                }

                listBillDone.CallBack(listBill);
                listBillDone.TongTien(totalIncome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ShowAllDoneBill", "Error: " + error.getMessage());
            }
        });
    }


}
