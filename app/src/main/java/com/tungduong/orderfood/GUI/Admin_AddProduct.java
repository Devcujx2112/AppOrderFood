package com.tungduong.orderfood.GUI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tungduong.orderfood.DAO.DAO_Product;
import com.tungduong.orderfood.DAO.DAO_TypeFood;
import com.tungduong.orderfood.Entity.Product;
import com.tungduong.orderfood.Entity.TypeFood;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_AddProduct extends AppCompatActivity {
    private String selectedTypeFood;
    ImageView imgProduct;
    EditText masp_product, tensp_product, soluong_product, giatien_product, mota_product;
    Button save_product;
    Spinner typefood_product;
    DAO_TypeFood daoTypeFood;
    DAO_Product daoProduct;
    List<String> typeFood;
    String imageURL;
    Uri uri;
    Drawable image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_product);
        AnhXa();

        ActivityResultLauncher<Intent> activityResultLauncherImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),//khởi động 1 intent nhận kết quả
                new ActivityResultCallback<ActivityResult>() {//nhận kết quả trả về
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;

                            uri = data.getData();
                            imgProduct.setImageURI(uri);
                        } else {
                            Toast.makeText(Admin_AddProduct.this, "Không có ảnh nào được chọn", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncherImage.launch(intent);
            }
        });

        daoProduct = new DAO_Product();
        daoTypeFood = new DAO_TypeFood();
        typeFood = new ArrayList<>();

        daoTypeFood.SelectItemsInSpinner(typeFood, new DAO_TypeFood.OnDataTypeFoodInSpinner() {
            @Override
            public void onDataLoaded(List<String> loadedData) {
                updateSpinner(loadedData);
            }
        });

        typefood_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTypeFood = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        save_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveStorageImage();
            }
        });
    }

    public void SaveStorageImage() {
        String masp = masp_product.getText().toString();
        String tensp = tensp_product.getText().toString();
        String soLuongString = soluong_product.getText().toString();
        String giaTien = giatien_product.getText().toString();
        String moTa = mota_product.getText().toString();

        if (masp.isEmpty() || tensp.isEmpty() || soLuongString.isEmpty() || giaTien.isEmpty() || moTa.isEmpty()) {
            Toast.makeText(Admin_AddProduct.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int soLuong = Integer.parseInt(soLuongString);

        if (uri == null) {
            Toast.makeText(Admin_AddProduct.this, "Vui lòng thêm ảnh của loại sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Product Image").child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_AddProduct.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                AddProduct(masp, tensp, soLuong, giaTien, moTa);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void AddProduct(String masp, String tensp, int soLuong, String giaTien, String moTa) {
        Product product = new Product(masp, tensp, soLuong, giaTien, imageURL, selectedTypeFood, moTa);
        daoProduct = new DAO_Product();
        daoProduct.AddProDuct(product,Admin_AddProduct.this);

        SetText();
    }

    public void updateSpinner(List<String> typeFoodList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeFoodList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typefood_product.setAdapter(adapter);
    }

    public void SetText() {
        masp_product.setText("");
        tensp_product.setText("");
        soluong_product.setText("");
        giatien_product.setText("");
        mota_product.setText("");
        imgProduct.setImageDrawable(image);
    }

    public void AnhXa() {
        imgProduct = findViewById(R.id.upload_imgPD);
        masp_product = findViewById(R.id.upload_idPD);
        tensp_product = findViewById(R.id.upload_namePD);
        soluong_product = findViewById(R.id.upload_soLuongPD);
        giatien_product = findViewById(R.id.upload_giaTienPD);
        mota_product = findViewById(R.id.upload_moTaPD);
        save_product = findViewById(R.id.saveButtonPD);
        typefood_product = findViewById(R.id.tenTypeFood);
        image = getResources().getDrawable(R.drawable.add_image);
    }
}
