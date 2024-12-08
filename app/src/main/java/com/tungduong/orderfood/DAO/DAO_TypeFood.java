package com.tungduong.orderfood.DAO;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tungduong.orderfood.BLL.TypeFoodAdapter_Admin;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.Entity.TypeFood;
import com.tungduong.orderfood.R;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class DAO_TypeFood {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("TypeFood");
    ValueEventListener eventListener;

    public void InsertTypeFood(TypeFood typeFood, Context context) {
        databaseReference.child(typeFood.getIDTypeFood()).setValue(typeFood).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Thêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Thêm loại sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface ListTypeFoodCallBack {
        void CallBack(List<TypeFood> typeFood);
    }

    public void GetAllTypeFood(ListTypeFoodCallBack callBack) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<TypeFood> typeFoodList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    TypeFood typeFood = data.getValue(TypeFood.class);
                    if (typeFood != null) {
                        typeFoodList.add(typeFood);
                    }
                }
                callBack.CallBack(typeFoodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Show All TypeFood Admin", error.getMessage().trim());
            }
        });
    }

    public void UpdateTypeFood(TypeFood typeFood,Context context){
       databaseReference.child(typeFood.getIDTypeFood()).setValue(typeFood).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
                Toast.makeText(context,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(context,"Cập nhật thất bại" + e.getMessage() ,Toast.LENGTH_SHORT).show();
           }
       });
    }

    public void DeleteTypeFood(){

    }
}
