package com.tungduong.orderfood.BLL;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tungduong.orderfood.DAO.DAO_ShopingCart;
import com.tungduong.orderfood.Entity.ShopingCart;
import com.tungduong.orderfood.GUI.GUI_ShoppingCart;
import com.tungduong.orderfood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingCart_Adaptor_User extends RecyclerView.Adapter<Adaptor_ShoppingCart_User> {
    private Context context;
    private List<ShopingCart> shopingCartList;

    public ShoppingCart_Adaptor_User(Context context, List<ShopingCart> shopingCartList) {
        this.context = context;
        this.shopingCartList = shopingCartList;
    }

    @NonNull
    @Override
    public Adaptor_ShoppingCart_User onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_shoppingcart,parent,false);
        return  new Adaptor_ShoppingCart_User(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_ShoppingCart_User holder, int position) {
        ShopingCart shopingCart = shopingCartList.get(position);
        DAO_ShopingCart daoShopingCart = new DAO_ShopingCart();
        String imageURL = shopingCart.getImage();
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            Glide.with(context).load(imageURL).into(holder.imageCart);
        }

        holder.txt_title.setText(shopingCart.getTenSP());
        holder.txt_soLuong.setText(String.valueOf(shopingCart.getSoLuong()));
        holder.txt_slprice.setText(shopingCart.getGiaTien() + " VND");
        AtomicInteger soLuong = new AtomicInteger(shopingCart.getSoLuong());
        int giaTien = Integer.parseInt(shopingCart.getGiaTien());

        String a = String.valueOf(soLuong.get() * giaTien);
        holder.txt_price.setText(a +" VND");

        holder.txt_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensp = holder.txt_title.getText().toString().trim();
                SharedPreferences sharedPreferences = context.getSharedPreferences("ShoppingCart", MODE_PRIVATE);
                String uid = sharedPreferences.getString("uid", "");
                daoShopingCart.DeleteSoLuongCart(uid, tensp, new DAO_ShopingCart.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        shopingCart.setSoLuong(shopingCart.getSoLuong() - 1);
                        notifyItemChanged(position);
                    }
                });
            }
        });

        holder.txt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensp = holder.txt_title.getText().toString().trim();
                SharedPreferences sharedPreferences = context.getSharedPreferences("ShoppingCart", MODE_PRIVATE);
                String uid = sharedPreferences.getString("uid", "");
                daoShopingCart.UpdateSoLuongCart(uid, tensp, new DAO_ShopingCart.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        shopingCart.setSoLuong(shopingCart.getSoLuong() + 1);
                        notifyItemChanged(position);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopingCartList.size();
    }
}

class Adaptor_ShoppingCart_User extends RecyclerView.ViewHolder{
    ImageView imageCart;
    TextView txt_title,txt_soLuong,txt_price,txt_min,txt_plus,txt_slprice;

    public Adaptor_ShoppingCart_User(@NonNull View itemView) {
        super(itemView);
        imageCart = itemView.findViewById(R.id.img_cart);
        txt_title = itemView.findViewById(R.id.txt_title);
        txt_slprice = itemView.findViewById(R.id.txt_price_sl);
        txt_price = itemView.findViewById(R.id.txt_price);
        txt_min = itemView.findViewById(R.id.txt_min);
        txt_plus = itemView.findViewById(R.id.txt_plus);
        txt_soLuong = itemView.findViewById(R.id.txt_soLuong);
    }
}
