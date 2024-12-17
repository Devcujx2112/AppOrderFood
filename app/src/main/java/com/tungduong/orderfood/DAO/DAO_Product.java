package com.tungduong.orderfood.DAO;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.tungduong.orderfood.Entity.Product;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class DAO_Product {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Product");

    public void AddProDuct(Product product, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        databaseReference.orderByChild("masp").equalTo(product.getMasp()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(context, "Mã sản phẩm này đã tồn tại. Vui lòng chọn mã khác", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    databaseReference.child(product.getMasp()).setValue(product).addOnCompleteListener(task -> {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
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

    public interface ListProductCallBack {
        void CallBack(List<Product> product);
    }

    public void GetAllProduct(ListProductCallBack callBack) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productsList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null) {
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

    public void SelectImage(String masp, String tensp, int soLuong, String giaTien, Uri newImageUri, String oldImageUri, String typeFood, String moTa, Context context) {
        if (newImageUri != null) {
            StorageReference newImagePD = FirebaseStorage.getInstance().getReference().child("Product Image").child(newImageUri.getLastPathSegment());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();

            newImagePD.putFile(newImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String newImageUrl = task.getResult().toString();
                                UpdateProduct(masp, tensp, soLuong, giaTien, newImageUrl, typeFood, moTa, context);

                                if (oldImageUri != null && !oldImageUri.isEmpty()) {
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUri);
                                    storageReference.delete();
                                }
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Không thể lấy URL new Image", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Lỗi khi upload ảnh mới: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        } else {
            UpdateProduct(masp, tensp, soLuong, giaTien, oldImageUri, typeFood, moTa, context);
        }
    }

    public void UpdateProduct(String masp, String tensp, int soLuong, String giaTien, String imageUri, String typeFood, String moTa, Context context) {
        Product product = new Product(masp, tensp, soLuong, giaTien, imageUri, typeFood, moTa);
        databaseReference.child(masp).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteProduct(String masp, String image, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        databaseReference.child(masp).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(masp).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                        storageReference.delete();
                        Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Xóa loại sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Lỗi kết nối Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public interface ListProductSearch {
        void ListProduct(List<Product> product);
    }

    public void SearchProductFormTenSP(String tensp, ListProductSearch ListProduct) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productsList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null && product.getTensp().toLowerCase().contains(tensp.toLowerCase())) {
                        // Kiểm tra nếu tên sản phẩm chứa từ khóa tìm kiếm (không phân biệt hoa thường)
                        productsList.add(product);
                    }
                }
                ListProduct.ListProduct(productsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Search product Admin", error.getMessage().trim());
            }
        });
    }

    public interface ListProductWhereTypeFood {
        void ListProduct(List<Product> product);
    }

    public void SearchProductWhereTypeFood(String typeFood,Context context, ListProductWhereTypeFood ListProduct) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productsList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null && product.getLoaiDoAn().toLowerCase().contains(typeFood.toLowerCase())) {
                        productsList.add(product);
                    }
                }
                ListProduct.ListProduct(productsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Search product Admin", error.getMessage().trim());
            }
        });
    }

}









