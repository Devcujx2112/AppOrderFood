package com.tungduong.orderfood.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tungduong.orderfood.BLL.ShoppingCart_Adaptor_User;
import com.tungduong.orderfood.Entity.ShopingCart;
import com.tungduong.orderfood.Entity.TypeFood;

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

    public void GetAllProductInShoppingCart(String uid,ListProductInShoppingCartCallBack callBack) {
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


}
