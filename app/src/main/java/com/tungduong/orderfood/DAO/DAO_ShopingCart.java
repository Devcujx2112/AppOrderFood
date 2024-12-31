package com.tungduong.orderfood.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tungduong.orderfood.Entity.Bill;
import com.tungduong.orderfood.Entity.ShopingCart;

import java.util.ArrayList;
import java.util.List;

public class DAO_ShopingCart {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("ShoppingCart");

    public void AddProductInShoppingCart(ShopingCart shopingCart, Context context) {
        DatabaseReference userCartRef = databaseReference.child(shopingCart.getUid());
        DatabaseReference productRef = userCartRef.child(shopingCart.getTenSP());

        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer oldSoLuong = snapshot.child("soLuong").getValue(Integer.class);
                    oldSoLuong = (oldSoLuong == null) ? 0 : oldSoLuong;

                    int updateSoLuong = oldSoLuong + shopingCart.getSoLuong();
                    productRef.child("soLuong").setValue(updateSoLuong).addOnCompleteListener(task -> {
                        Toast.makeText(context, "Đã cập nhật giỏ hàng", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, "Lỗi khi cập nhật số lượng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    productRef.setValue(shopingCart).addOnSuccessListener(unused -> {
                        Toast.makeText(context, "Thêm sản phẩm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, "Lỗi khi thêm sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Lỗi khi truy vấn Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface ListProductInShoppingCartCallBack {
        void CallBack(List<ShopingCart> list_cart);
    }

    public void GetAllProductInShoppingCart(String uid, ListProductInShoppingCartCallBack callBack) {
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ShopingCart> shopingCartList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    ShopingCart shopingCart = data.getValue(ShopingCart.class);
                    if (shopingCart != null) {
                        shopingCartList.add(shopingCart);
                    }
                }
                callBack.CallBack(shopingCartList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Show All Shopping Cart User", error.getMessage().trim());
            }
        });
    }

    public interface UpdateCallback {
        void onUpdate();
    }

    public void UpdateSoLuongCart(String uid, String tensp, UpdateCallback callback) {
        databaseReference.child(uid).child(tensp).child("soLuong").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer soLuong = snapshot.getValue(Integer.class);
                    if (soLuong != null) {
                        soLuong++;
                        databaseReference.child(uid).child(tensp).child("soLuong").setValue(soLuong);
                        if (callback != null) callback.onUpdate();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UpdateSoLuongCart", "Lỗi khi cập nhật soLuong: " + error.getMessage());
            }
        });
    }

    public void DeleteSoLuongCart(String uid, String tensp, UpdateCallback callback) {
        databaseReference.child(uid).child(tensp).child("soLuong").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer soLuong = snapshot.getValue(Integer.class);
                    if (soLuong != null && soLuong > 1) {
                        soLuong--;
                        databaseReference.child(uid).child(tensp).child("soLuong").setValue(soLuong);
                        if (callback != null) callback.onUpdate();
                    } else {
                        databaseReference.child(uid).child(tensp).removeValue();
                        if (callback != null) callback.onUpdate();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DeleteSoLuongCart", "Lỗi khi xóa soLuong: " + error.getMessage());
            }
        });
    }

    public void GetMoney(String uid, final MoneyCallback callback) {
        databaseReference.child(uid).get().addOnCompleteListener(task -> {
            double total = 0;
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                List<String> listProduct = new ArrayList<>();

                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Object giaTienObject = productSnapshot.child("giaTien").getValue();
                    Object soLuongObject = productSnapshot.child("soLuong").getValue();
                    String tensp = productSnapshot.child("tenSP").getValue(String.class);
                    listProduct.add(tensp);
                    String giaTien = giaTienObject.toString().trim();

                    int soLuong = Integer.parseInt(soLuongObject.toString());
                    double price = Double.parseDouble(giaTien);

                    total += price * soLuong;
                }
                callback.TongTienDonHang(total);
                callback.ProductInShoppingCart(listProduct);

            } else {
                Log.e("Errors", "Lỗi khi lấy dữ liệu: " + task.getException().getMessage());
            }
        });
    }

    public interface MoneyCallback {
        void TongTienDonHang(double total);
        void ProductInShoppingCart(List<String> tensp);
    }

    public void DeleteShoppingCart(String uid, Context context) {
        databaseReference = firebaseDatabase.getReference("ShoppingCart");
        databaseReference.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                    Toast.makeText(context, "Xóa giỏ hàng thất bại: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void AddHoaDon(Bill hoaDon, Context context, String uid) {
        databaseReference = firebaseDatabase.getReference("Bill").push();
        String autoID = databaseReference.getKey();
        hoaDon.setId(autoID);
        databaseReference.setValue(hoaDon).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DeleteShoppingCart(uid,context);
            } else {
                Toast.makeText(context, "Thêm hóa đơn thất bại: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
