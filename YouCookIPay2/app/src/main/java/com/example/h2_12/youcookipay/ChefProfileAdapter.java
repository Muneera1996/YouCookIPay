package com.example.h2_12.youcookipay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChefProfileAdapter extends RecyclerView.Adapter<ChefProfileAdapter.ChefProfileViewHolder>{

    private Context context;
    private ArrayList<Meal> mealList;
    public ChefProfileAdapter(Context context,ArrayList<Meal> meals){
        this.context=context;
        this.mealList=meals;
    }
    @NonNull
    @Override
    public ChefProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_layout_profile_view,parent,false);
        return new ChefProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChefProfileViewHolder holder, int position) {
          final Meal currentMeal=mealList.get(position);
          holder.dishName.setText(currentMeal.getMealName());
          holder.dishPrice.setText(currentMeal.getMealPrice());
         // Glide.with(holder.dishPic.getContext()).load(currentMeal.getMealImageUrl()).into((ImageView) holder.dishPic);
        Glide
                .with(holder.dishPic.getContext())
                .asBitmap()
                .load(currentMeal.getMealImageUrl())
                .into(new SimpleTarget<Bitmap>(120,170) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable dr = new BitmapDrawable(resource);
                        holder.dishPic.setBackgroundDrawable(dr);
                    }
                });


        holder.placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.placeOrder.getContext(),OrderScreeen2Activity.class);
                intent.putExtra("Screen","profile");
                intent.putExtra("MealId", currentMeal.getMealId());
                intent.putExtra("MealName", currentMeal.getMealName());
                intent.putExtra("MealPrice", currentMeal.getMealPrice());
                intent.putExtra("MealImage", currentMeal.getMealImageUrl());
                holder.placeOrder.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mealList.size();
    }

    public class ChefProfileViewHolder extends RecyclerView.ViewHolder{
        View dishPic;
        TextView dishName,dishPrice;
        ImageView placeOrder;

        public ChefProfileViewHolder(View itemView) {
            super(itemView);
            dishName=itemView.findViewById(R.id.dish_name);
            dishPrice=itemView.findViewById(R.id.dish_price);
            dishPic=itemView.findViewById(R.id.dish_pic);
            placeOrder=itemView.findViewById(R.id.place_your_order);
        }
    }


}