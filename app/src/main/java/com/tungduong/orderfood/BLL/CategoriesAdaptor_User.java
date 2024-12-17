package com.tungduong.orderfood.BLL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tungduong.orderfood.Entity.Product;
import com.tungduong.orderfood.Entity.TypeFood;
import com.tungduong.orderfood.GUI.GUI_Viewholder_TypeFood_User;
import com.tungduong.orderfood.R;

import java.util.List;

public class CategoriesAdaptor_User extends RecyclerView.Adapter<Adaptor_TypeFood_User> {
    private Context context;
    private List<TypeFood> typeFoodList;

    public CategoriesAdaptor_User(Context context, List<TypeFood> typeFoodList) {
        this.context = context;
        this.typeFoodList = typeFoodList;
    }

    @NonNull
    @Override
    public Adaptor_TypeFood_User onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category,parent,false);
        return new Adaptor_TypeFood_User(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_TypeFood_User holder, int position) {
        TypeFood typeFood = typeFoodList.get(position);
        String imageURL = typeFood.getimageTypeFood();
        Glide.with(holder.itemView.getContext()).load(imageURL).into(holder.typeFood_img);
        holder.txt_name.setText(typeFood.getnameTypeFood());

        holder.item_TypeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GUI_Viewholder_TypeFood_User.class);
                intent.putExtra("Image_TypeFood",typeFoodList.get(holder.getAdapterPosition()).getimageTypeFood());
                intent.putExtra("Name_TypeFood",typeFoodList.get(holder.getAdapterPosition()).getnameTypeFood());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeFoodList.size();
    }
}

class Adaptor_TypeFood_User extends RecyclerView.ViewHolder{
    ImageView typeFood_img;
    TextView txt_name;
    ConstraintLayout item_TypeFood;

    public Adaptor_TypeFood_User(@NonNull View itemView) {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_typeFood);
        typeFood_img = itemView.findViewById(R.id.category_img);
        item_TypeFood = itemView.findViewById(R.id.item_typeFood);
    }
}
