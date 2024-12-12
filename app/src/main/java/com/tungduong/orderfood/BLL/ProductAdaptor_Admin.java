package com.tungduong.orderfood.BLL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tungduong.orderfood.Entity.Product;
import com.tungduong.orderfood.GUI.Admin_ChiTiet_Product;
import com.tungduong.orderfood.R;

import java.util.List;

public class ProductAdaptor_Admin extends RecyclerView.Adapter<Adaptor_Product_Admin> {
    private Context context;
    private List<Product> productList;

    public ProductAdaptor_Admin(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public Adaptor_Product_Admin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_admin_product,parent,false);
        return new Adaptor_Product_Admin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Product_Admin holder, int position) {
        Product product = productList.get(position);
        String imageURL = product.getImage();
        Glide.with(holder.itemView.getContext()).load(imageURL).placeholder(R.drawable.ic_launcher_foreground).into(holder.img_product);
        holder.name_product.setText(product.getTensp());
        holder.soLuong.setText(String.valueOf("Số lượng: "+product.getSoLuong()));
        holder.giaTien.setText(product.getGiaTien()+ " VNĐ");
        holder.typeFood.setText("Loại đồ ăn: "+product.getLoaiDoAn());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Admin_ChiTiet_Product.class);
                intent.putExtra("Image_Product",productList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("Masp_Product",productList.get(holder.getAdapterPosition()).getMasp());
                intent.putExtra("Tensp_Product",productList.get(holder.getAdapterPosition()).getTensp());
                intent.putExtra("SoLuong_Product",productList.get(holder.getAdapterPosition()).getSoLuong());
                intent.putExtra("GiaTien_Product",productList.get(holder.getAdapterPosition()).getGiaTien());
                intent.putExtra("Mota_Product",productList.get(holder.getAdapterPosition()).getMoTa());
                intent.putExtra("TypeFood_Product",productList.get(holder.getAdapterPosition()).getLoaiDoAn());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}


class Adaptor_Product_Admin extends RecyclerView.ViewHolder{
    ImageView img_product;
    TextView name_product,soLuong,giaTien,typeFood;
    CardView cardView;

    public Adaptor_Product_Admin(@NonNull View itemView) {
        super(itemView);
        img_product = itemView.findViewById(R.id.img_product);
        name_product = itemView.findViewById(R.id.name_product);
        soLuong = itemView.findViewById(R.id.soLuong_product);
        giaTien = itemView.findViewById(R.id.giaTien_product);
        typeFood = itemView.findViewById(R.id.typeFood);
        cardView = itemView.findViewById(R.id.item_account_admin);
    }

}