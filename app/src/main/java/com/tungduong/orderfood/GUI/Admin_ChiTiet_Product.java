package com.tungduong.orderfood.GUI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.tungduong.orderfood.DAO.DAO_Product;
import com.tungduong.orderfood.DAO.DAO_TypeFood;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Admin_ChiTiet_Product extends AppCompatActivity {
    private String selectedTypeFood;
    ImageView update_image;
    EditText update_masp, update_tensp, update_soLuong, update_giaTien, update_mota;
    Spinner update_typeFood;
    Button updatePD, deletePD;
    DAO_TypeFood daoTypeFood;
    DAO_Product daoProduct;
    List<String> typeFoodList;
    Uri uri;
    Drawable brgImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_chi_tiet_product);
        AnhXa();

        daoProduct = new DAO_Product();
        daoTypeFood = new DAO_TypeFood();
        typeFoodList = new ArrayList<>();

        ActivityResultLauncher<Intent> activityResultLauncherImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),//khởi động 1 intent nhận kết quả
                new ActivityResultCallback<ActivityResult>() {//nhận kết quả trả về
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;
                            uri = data.getData();
                            update_image.setImageURI(uri);
                        } else {
                            Toast.makeText(Admin_ChiTiet_Product.this, "Không có hình ảnh nào được chọn", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        update_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncherImage.launch(intent);
            }
        });

        daoTypeFood.SelectItemsInSpinner(typeFoodList, new DAO_TypeFood.OnDataTypeFoodInSpinner() {
            @Override
            public void onDataLoaded(List<String> typeFood) {
                if (typeFood != null && !typeFood.isEmpty()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Admin_ChiTiet_Product.this, android.R.layout.simple_spinner_item, typeFood);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    update_typeFood.setAdapter(adapter);

                    String typeFoodPD = getIntent().getStringExtra("TypeFood_Product");
                    if (typeFoodPD != null) {
                        int position = adapter.getPosition(typeFoodPD);
                        if (position >= 0) {
                            update_typeFood.setSelection(position);
                        }
                    }
                }
            }
        });

        update_typeFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedTypeFood = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            update_masp.setText(bundle.getString("Masp_Product"));
            update_tensp.setText(bundle.getString("Tensp_Product"));
            update_soLuong.setText(String.valueOf(bundle.getInt("SoLuong_Product")));
            update_giaTien.setText(bundle.getString("GiaTien_Product"));
            update_mota.setText(bundle.getString("Mota_Product"));

            Glide.with(this).load(bundle.getString("Image_Product")).into(update_image);
        }

        updatePD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProduct();
            }
        });

        deletePD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteProduct();
            }
        });
    }

    public void DeleteProduct(){
        String masp = update_masp.getText().toString();
        String image = getIntent().getStringExtra("Image_Product");
        daoProduct = new DAO_Product();
        daoProduct.DeleteProduct(masp,image,this);
        SetText();
    }

    public void UpdateProduct() {
        String masp = update_masp.getText().toString();
        String tensp = update_tensp.getText().toString();
        String soLuongString = update_soLuong.getText().toString();
        String giaTien = update_giaTien.getText().toString();
        String moTa = update_mota.getText().toString();

        if (masp.isEmpty() || tensp.isEmpty() || soLuongString.isEmpty() || giaTien.isEmpty() || moTa.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        int soLuong = Integer.parseInt(soLuongString);

        String oldImageUrl = getIntent().getStringExtra("Image_Product");
        daoProduct = new DAO_Product();
        daoProduct.SelectImage(masp, tensp, soLuong, giaTien, uri, oldImageUrl, selectedTypeFood, moTa, Admin_ChiTiet_Product.this);
        SetText();
    }

    public void AnhXa() {
        update_image = findViewById(R.id.update_ImgPD);
        update_masp = findViewById(R.id.update_maspPD);
        update_tensp = findViewById(R.id.update_namePD);
        update_soLuong = findViewById(R.id.update_soLuongPD);
        update_giaTien = findViewById(R.id.update_giaTienPD);
        update_mota = findViewById(R.id.update_moTaPD);
        update_typeFood = findViewById(R.id.update_typeFoodPD);
        updatePD = findViewById(R.id.updateButtonPD);
        deletePD = findViewById(R.id.deleteButtonPD);
        brgImg = getResources().getDrawable(R.drawable.add_image);
    }

    public void SetText() {
        update_image.setImageDrawable(brgImg);
        update_masp.setText("");
        update_tensp.setText("");
        update_soLuong.setText("");
        update_giaTien.setText("");
        update_mota.setText("");
    }
}