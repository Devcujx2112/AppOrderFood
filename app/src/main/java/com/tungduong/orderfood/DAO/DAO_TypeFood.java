package com.tungduong.orderfood.DAO;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tungduong.orderfood.Entity.TypeFood;
import com.tungduong.orderfood.R;

import java.security.MessageDigest;

public class DAO_TypeFood {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("TypeFood");

    public void InsertTypeFood(TypeFood typeFood, Context context){
        databaseReference.child(typeFood.getIDTypeFood()).setValue(typeFood).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(context, "Thêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Thêm loại sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
