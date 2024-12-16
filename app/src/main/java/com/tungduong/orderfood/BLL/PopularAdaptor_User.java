package com.tungduong.orderfood.BLL;

import android.content.Context;
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
import com.tungduong.orderfood.R;

import java.util.List;

public class PopularAdaptor_User extends RecyclerView.Adapter<Adaptor_Popular_Admin> {
    private Context context;
    private List<Product> productList;

    public PopularAdaptor_User(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public Adaptor_Popular_Admin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product,parent,false);
        return new Adaptor_Popular_Admin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Popular_Admin holder, int position) {
        Product product = productList.get(position);
        String imageURL = product.getImage();
        Glide.with(holder.itemView.getContext()).load(imageURL).into(holder.product_image);
        holder.product_name.setText(product.getTensp());
        holder.product_price.setText(product.getGiaTien()+" VND");

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

class Adaptor_Popular_Admin extends RecyclerView.ViewHolder{
    ImageView product_image;
    TextView product_name,product_price,click;
    CardView cardView;

    public Adaptor_Popular_Admin(@NonNull View itemView) {
        super(itemView);
        product_image = itemView.findViewById(R.id.product_image);
        product_name = itemView.findViewById(R.id.txt_title);
        product_price = itemView.findViewById(R.id.txt_giaTien);
        click = itemView.findViewById(R.id.click);

    }
}
