package com.tungduong.orderfood.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tungduong.orderfood.Entity.Product;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class DAO_Product {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Product");

    public void AddProDuct(Product product, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        databaseReference.orderByChild("ProductID").equalTo(product.getMasp()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(context,"Mã sản phẩm này đã tồn tại. Vui lòng chọn mã khác",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else{
                    databaseReference.child(product.getMasp()).setValue(product).addOnCompleteListener(task -> {
                       dialog.dismiss();
                       if (task.isSuccessful()){
                           Toast.makeText(context,"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();
                       }
                       else {
                           Toast.makeText(context,"Thêm sản phẩm thất bại",Toast.LENGTH_SHORT).show();
                       }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Lỗi kết nối với Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public interface ListProductCallBack{
        void CallBack(List<Product> product);
    }
    public void GetAllProduct(ListProductCallBack callBack){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productsList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null){
                        productsList.add(product);
                    }
                }
                callBack.CallBack(productsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Show All product Admin", error.getMessage().trim());
            }
        });
    }
}
