package com.tungduong.orderfood.GUI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.tungduong.orderfood.DAO.DAO_TypeFood;
import com.tungduong.orderfood.Entity.TypeFood;
import com.tungduong.orderfood.R;

public class Admin_AddTypeFood extends AppCompatActivity {
    ImageView imgTypeFood;
    Button cancel, save;
    EditText id, nameTypeFood, mota;
    String imageURL;
    Uri uri;
    DAO_TypeFood daoTypeFood;
    TypeFood typeFood;
    Drawable add_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_typefood);

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
                            imgTypeFood.setImageURI(uri);
                        } else {
                            Toast.makeText(Admin_AddTypeFood.this, "Không có ảnh nào được chọn", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imgTypeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncherImage.launch(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveStorageImage();
            }
        });
    }

    public void SaveStorageImage() {
        String TypeId = id.getText().toString();
        String TypeName = nameTypeFood.getText().toString();
        String TypeMoTa = mota.getText().toString();

        if (TypeId.isEmpty() || TypeName.isEmpty() || TypeMoTa.isEmpty()) {
            Toast.makeText(Admin_AddTypeFood.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (uri == null) {
            Toast.makeText(Admin_AddTypeFood.this, "Vui lòng thêm ảnh của loại sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("TypeFood Image").child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_AddTypeFood.this);
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
                AddTypeFood(TypeId, TypeName, TypeMoTa);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void AddTypeFood(String TypeId, String TypeName, String TypeMoTa) {
        typeFood = new TypeFood(TypeId, TypeName, imageURL, TypeMoTa);
        daoTypeFood = new DAO_TypeFood();
        daoTypeFood.InsertTypeFood(typeFood, Admin_AddTypeFood.this);

        SetText();
    }

    public void AnhXa() {
        imgTypeFood = findViewById(R.id.upload_imgTF);
        save = findViewById(R.id.saveButtonTF);

        id = findViewById(R.id.upload_idTF);
        nameTypeFood = findViewById(R.id.upload_nameTypeFoodTF);
        mota = findViewById(R.id.upload_moTaTF);
        add_image = getResources().getDrawable(R.drawable.add_image);
    }

    public void SetText(){
        id.setText("");
        nameTypeFood.setText("");
        mota.setText("");
        imgTypeFood.setImageDrawable(add_image);
    }

}