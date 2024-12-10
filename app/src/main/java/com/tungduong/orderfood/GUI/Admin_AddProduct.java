package com.tungduong.orderfood.GUI;
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
import androidx.appcompat.app.AppCompatActivity;
import com.tungduong.orderfood.DAO.DAO_Product;
import com.tungduong.orderfood.DAO.DAO_TypeFood;
import com.tungduong.orderfood.R;
import java.util.ArrayList;
import java.util.List;

public class Admin_AddProduct extends AppCompatActivity {
    ImageView imgProduct;
    EditText masp_product, tensp_product, soluong_product, giatien_product, mota_product;
    Button save_product;
    Spinner typefood_product;
    DAO_TypeFood daoTypeFood;
    DAO_Product daoProduct;
    List<String> typeFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_product);
        AnhXa();

        daoTypeFood = new DAO_TypeFood();
        typeFood = new ArrayList<>();

        daoTypeFood.SelectItemsInSpinner(typeFood, new DAO_TypeFood.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<String> loadedData) {
                updateSpinner(loadedData);
            }
        });

    }

    public void updateSpinner(List<String> typeFoodList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeFoodList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Định dạng cho dropdown
        typefood_product.setAdapter(adapter);
    }

    public void AnhXa(){
        imgProduct = findViewById(R.id.upload_imgPD);
        masp_product = findViewById(R.id.upload_idPD);
        tensp_product = findViewById(R.id.upload_namePD);
        soluong_product = findViewById(R.id.upload_soLuongPD);
        giatien_product = findViewById(R.id.upload_giaTienPD);
        mota_product = findViewById(R.id.upload_moTaPD);
        save_product = findViewById(R.id.saveButtonPD);
        typefood_product = findViewById(R.id.tenTypeFood);
    }
}
