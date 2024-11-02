package com.tungduong.orderfood.DAO;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.tungduong.orderfood.Entity.Account;
import com.tungduong.orderfood.GUI.GUI_AdminPage;
import com.tungduong.orderfood.GUI.GUI_HomePage;
import com.tungduong.orderfood.GUI.GUI_SignUp;
import com.tungduong.orderfood.R;


public class DAO_Account {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Account");

    public void CheckLogin(String userName, String passWord, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        databaseReference.orderByChild("userName").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                        String db_passWord = userSnapshot.child("passWord").getValue(String.class);
                        String db_role = userSnapshot.child("role").getValue(String.class);

                        if (db_passWord != null && db_passWord.equals(passWord)) {

                            if (db_role != null) {
                                if (db_role.equals("admin")) {
                                    Intent intent = new Intent(context, GUI_AdminPage.class);
                                    context.startActivity(intent);
                                }
                                else if (db_role.equals("user")) {
                                    Intent intent = new Intent(context, GUI_HomePage.class);
                                    context.startActivity(intent);
                                }
                            }
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        return;
                    }
                } else {
                    Toast.makeText(context, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DAO_Account", error.getMessage());
            }
        });
    }

    public void InsertAccount(Account account, Context context) {
        String userName = account.getUserName().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        databaseReference.orderByChild("userName").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(context, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
                else {
                    String id = databaseReference.push().getKey();
                    if (id != null){
                        account.setId(id);
                        databaseReference.child(id).setValue(account).addOnCompleteListener(task -> {
                           if (task.isComplete()){
                               Toast.makeText(context, "Đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                               dialog.dismiss();
                           }
                           else {
                               Toast.makeText(context,"Thêm tài khoản thất bại vui lòng liên hệ admin",Toast.LENGTH_SHORT).show();
                               dialog.dismiss();
                           }
                        });
                    }
                    else {
                        Log.d("DAO_Account","Không thể tạo id mới");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DAO_Account",error.getMessage());
            }
        });
    }

    public void ForgotPassword(String userName, String email, Context context) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Account");

        databaseRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Kiểm tra nếu email tồn tại
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String storedUserName = snapshot.child("userName").getValue(String.class);
                        if (storedUserName != null && storedUserName.equals(userName)) {
                            // Gửi email để đặt lại mật khẩu
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            auth.sendPasswordResetEmail(email).addOnCompleteListener(emailTask -> {
                                if (emailTask.isSuccessful()) {
                                    Toast.makeText(context, "Mật khẩu đã được gửi, vui lòng kiểm tra Email của bạn.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("Error sending email", emailTask.getException().getMessage());
                                    Toast.makeText(context, "Lỗi khi gửi Email", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return; // Đã tìm thấy tên người dùng hợp lệ
                        }
                    }
                    // Nếu không tìm thấy tên người dùng
                    Toast.makeText(context, "UserName không khớp", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu không tìm thấy email
                    Toast.makeText(context, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error forgot password", databaseError.getMessage());
            }
        });
    }



}
