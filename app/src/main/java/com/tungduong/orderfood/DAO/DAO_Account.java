package com.tungduong.orderfood.DAO;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.GUI.GUI_AdminPage;
import com.tungduong.orderfood.GUI.GUI_HomePage;
import com.tungduong.orderfood.R;


public class DAO_Account {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Account");

    public void CheckLogin(String email, String passWord, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        // Sử dụng Firebase Authentication để đăng nhập
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Lấy UID của người dùng đã đăng nhập
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();

                            // Truy vấn thông tin role từ Realtime Database theo UID
                            databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String db_role = snapshot.child("role").getValue(String.class);

                                        // Kiểm tra role và chuyển tới giao diện tương ứng
                                        if ("admin".equals(db_role)) {
                                            Intent intent = new Intent(context, GUI_AdminPage.class);
                                            context.startActivity(intent);
                                        } else if ("user".equals(db_role)) {
                                            Intent intent = new Intent(context, GUI_HomePage.class);
                                            context.startActivity(intent);
                                        }

                                        Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("DAO_Account", error.getMessage());
                                    dialog.dismiss();
                                }
                            });
                        }
                    } else {
                        // Xử lý trường hợp đăng nhập thất bại
                        Toast.makeText(context, "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }


    public void InsertAccount(Account account,String passWord, Context context) {
        String email = account.getEmail().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,passWord).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                //Lấy thông tin ng dùng
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                //lấy id
                if (firebaseUser != null){
                    String userId = firebaseUser.getUid();

                    account.setId(userId);
                    //Gán uid vào id của realtime db
                    databaseReference.child(userId).setValue(account).addOnCompleteListener(dbTask -> {
                        if (dbTask.isSuccessful()){
                            Toast.makeText(context,"Đăng ký tài khoản thành công",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context,"Tạo tài khoản thất bại, vui lòng thử lại",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    });
                }
            }
            else {
                String error = task.getException() != null ? task.getException().getMessage() : "Lỗi không xác định";
                Toast.makeText(context, "Đăng ký thất bại: " + error, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

}













