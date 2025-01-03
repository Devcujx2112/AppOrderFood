package com.tungduong.orderfood.BLL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tungduong.orderfood.Entity.TypeFood;
import com.tungduong.orderfood.GUI.Admin_ChiTiet_TypeFood;
import com.tungduong.orderfood.R;

import java.util.List;

public class TypeFoodAdapter_Admin extends RecyclerView.Adapter<Adaptor_TypeFood_Admin> {
    private Context context;
    private List<TypeFood> typeFoodList;

    public TypeFoodAdapter_Admin(Context context, List<TypeFood> newTypeFoodList) {
        this.context = context;
        this.typeFoodList = newTypeFoodList;
    }


    @NonNull
    @Override
    public Adaptor_TypeFood_Admin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_admin_typefood, parent, false);
        return new Adaptor_TypeFood_Admin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_TypeFood_Admin holder, int position) {
        TypeFood typeFood = typeFoodList.get(position);
        String imageURL = typeFood.getimageTypeFood();
        Glide.with(holder.itemView.getContext()).load(imageURL).placeholder(R.drawable.ic_launcher_foreground).into(holder.typeImg);
        holder.typeName.setText(typeFood.getnameTypeFood());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Admin_ChiTiet_TypeFood.class);
                intent.putExtra("Image_TypeFood", typeFoodList.get(holder.getAdapterPosition()).getimageTypeFood());
                intent.putExtra("Id_TypeFood", typeFoodList.get(holder.getAdapterPosition()).getidTypeFood());
                intent.putExtra("Name_TypeFood", typeFoodList.get(holder.getAdapterPosition()).getnameTypeFood());
                intent.putExtra("MoTa_TypeFood", typeFoodList.get(holder.getAdapterPosition()).getMoTa());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return typeFoodList.size();
    }
}

class Adaptor_TypeFood_Admin extends RecyclerView.ViewHolder {
    ImageView typeImg;
    TextView typeName;
    CardView cardView;

    public Adaptor_TypeFood_Admin(@NonNull View itemView) {
        super(itemView);
        typeImg = itemView.findViewById(R.id.image_TypeFood);
        typeName = itemView.findViewById(R.id.name_TypeFood);
        cardView = itemView.findViewById(R.id.recard);
    }
}

