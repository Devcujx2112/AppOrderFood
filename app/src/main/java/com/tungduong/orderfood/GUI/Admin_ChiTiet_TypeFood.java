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
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.tungduong.orderfood.DAO.DAO_TypeFood;
import com.tungduong.orderfood.R;

public class Admin_ChiTiet_TypeFood extends AppCompatActivity {
    ImageView update_imgTF;
    EditText update_nameTF, update_motaTF;
    Button update_TF, delete_TF;
    Uri uri;
    DAO_TypeFood daoTypeFood;
    Drawable add_image;

    private String update_idTF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_chitiet_typefood);
        AnhXa();

        daoTypeFood = new DAO_TypeFood();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            update_idTF = bundle.getString("Id_TypeFood");
            String name = bundle.getString("Name_TypeFood");
            update_nameTF.setText(name);
            String moTa = bundle.getString("MoTa_TypeFood");
            update_motaTF.setText(moTa);
            Glide.with(this).load(bundle.getString("Image_TypeFood")).into(update_imgTF);

        }

        ActivityResultLauncher<Intent> activityResultLauncherImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),//khởi động 1 intent nhận kết quả
                new ActivityResultCallback<ActivityResult>() {//nhận kết quả trả về
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;
                            uri = data.getData();
                            update_imgTF.setImageURI(uri);
                        } else {
                            Toast.makeText(Admin_ChiTiet_TypeFood.this, "Không có hình ảnh nào được chọn", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        update_imgTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncherImage.launch(intent);
            }
        });

        update_TF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateTypeFood();
            }
        });

        delete_TF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteTypeFood();
            }
        });
    }

    public void UpdateTypeFood() {
        String id = update_idTF.toString().trim();
        String name = update_nameTF.getText().toString();
        String mota = update_motaTF.getText().toString();

        if (id.isEmpty() || name.isEmpty() || mota.isEmpty()){
            Toast.makeText(this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            return;
        }

        String oldImageUrl = getIntent().getStringExtra("Image_TypeFood");
        daoTypeFood.SelectImage(id, name, uri, oldImageUrl, mota, this);
        SetText();
    }

    public void DeleteTypeFood(){
        String id = update_idTF.toString().trim();
        String imageURL = getIntent().getStringExtra("Image_TypeFood");
        daoTypeFood.DeleteTypeFood(id,imageURL,this);
        SetText();

    }

    public void AnhXa() {
        update_imgTF = findViewById(R.id.update_imageTF);
        update_nameTF = findViewById(R.id.update_nameTF);
        update_motaTF = findViewById(R.id.update_motaTF);

        update_TF = findViewById(R.id.update_TF);
        delete_TF = findViewById(R.id.delete_TF);
        add_image = getResources().getDrawable(R.drawable.add_image);
    }

    public void SetText() {
        update_nameTF.setText("");
        update_motaTF.setText("");
        update_imgTF.setImageDrawable(add_image);
    }
}