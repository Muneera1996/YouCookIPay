package com.example.h2_12.youcookipay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{
    private Context context;
    private ArrayList<Datum> homeList;
    private double rating = 0;
    private String mealDesc = "";

    public HomeAdapter(Context context, ArrayList<Datum> homeList) {
        this.context = context;
        this.homeList = homeList;
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_layout_home2,parent,false);
        return new HomeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, int position) {
        final Datum currentData=homeList.get(position);
        holder.chefName.setText(currentData.getUserName());
        holder.chefAddress.setText(currentData.getUserAddress());
        holder.chefDescription.setText(currentData.getUserDescription());
        holder.mealName.setText(currentData.getMealName());
        String description=currentData.getMealDescription();
        if(description.length()>80) {
            mealDesc+=description.substring(0,80);
            description=mealDesc;
            description+="....";
        }
        holder.mealDescription.setText(description);
        mealDesc="";
        holder.sellerType.setText(currentData.getSeller_type());
        holder.chefRating.setText(currentData.getRating());
        if(currentData.getRating() != null && !currentData.getRating().trim().isEmpty()){
            rating = Double.parseDouble(currentData.getRating());
        }

         if(rating>=1&&rating<2) {
            Glide.with(holder.star1.getContext()).load(R.drawable.fill_star).into(holder.star1);

        }
        else if(rating>=2&&rating<3) {
            Glide.with(holder.star1.getContext()).load(R.drawable.fill_star).into(holder.star1);
            Glide.with(holder.star2.getContext()).load(R.drawable.fill_star).into(holder.star2);
            Glide.with(holder.star3.getContext()).load(R.drawable.unfill_star).into(holder.star3);
            Glide.with(holder.star4.getContext()).load(R.drawable.unfill_star).into(holder.star4);
            Glide.with(holder.star5.getContext()).load(R.drawable.unfill_star).into(holder.star5);
        }
        else if(rating>=3&&rating<4) {
            Glide.with(holder.star1.getContext()).load(R.drawable.fill_star).into(holder.star1);
            Glide.with(holder.star2.getContext()).load(R.drawable.fill_star).into(holder.star2);
            Glide.with(holder.star3.getContext()).load(R.drawable.fill_star).into(holder.star3);
            Glide.with(holder.star4.getContext()).load(R.drawable.unfill_star).into(holder.star4);
            Glide.with(holder.star5.getContext()).load(R.drawable.unfill_star).into(holder.star5);
        }
        else if(rating>=4&&rating<5) {
            Glide.with(holder.star1.getContext()).load(R.drawable.fill_star).into(holder.star1);
            Glide.with(holder.star2.getContext()).load(R.drawable.fill_star).into(holder.star2);
            Glide.with(holder.star3.getContext()).load(R.drawable.fill_star).into(holder.star3);
            Glide.with(holder.star4.getContext()).load(R.drawable.fill_star).into(holder.star4);
            Glide.with(holder.star5.getContext()).load(R.drawable.unfill_star).into(holder.star5);
        }
        else if(rating>=5) {
            Glide.with(holder.star1.getContext()).load(R.drawable.fill_star).into(holder.star1);
            Glide.with(holder.star2.getContext()).load(R.drawable.fill_star).into(holder.star2);
            Glide.with(holder.star3.getContext()).load(R.drawable.fill_star).into(holder.star3);
            Glide.with(holder.star4.getContext()).load(R.drawable.fill_star).into(holder.star4);
            Glide.with(holder.star5.getContext()).load(R.drawable.fill_star).into(holder.star5);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(),ProfileViewChefActivity.class);
                intent.putExtra("Chef_Id", currentData.getUserId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
       /* Glide
                .with(holder.chefImage.getContext())
                .asBitmap()
                .load(currentData.getUserImage())
                .into(new SimpleTarget<Bitmap>(88,88) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable dr = new BitmapDrawable(resource);
                        holder.chefImage.setBackgroundDrawable(dr);
                    }
                });*/
        if(!currentData.getUserImage().equals("")) {
            Glide.with(holder.chefImage.getContext()).load(currentData.getUserImage()).into(holder.chefImage);
        }
        else if(currentData.getUserImage().equals(""))
        {
            Glide.with(holder.chefImage.getContext()).load(R.drawable.profile_pic).into(holder.chefImage);

        }
        Glide
                .with(holder.mealImage.getContext())
                .asBitmap()
                .load(currentData.getMeal_image())
                .into(new SimpleTarget<Bitmap>(120,170) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable dr = new BitmapDrawable(resource);
                        holder.mealImage.setBackgroundDrawable(dr);
                    }
                });
        holder.orderMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.orderMeal.getContext(),ProfileViewChefActivity.class);
                intent.putExtra("Chef_Id", currentData.getUserId());
                holder.orderMeal.getContext().startActivity(intent);
            }
        });
        holder.rating_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.rating_section.getContext(),SeeReviewAndRatingActivity.class);
                intent.putExtra("ChefId", currentData.getUserId());
                holder.rating_section.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return homeList.size();
    }
    public class HomeViewHolder extends RecyclerView.ViewHolder{
        View mealImage,rating_section;
        TextView chefName,chefAddress,chefDescription,mealName,mealDescription,sellerType,chefRating;
        ImageView chefImage,orderMeal,star1,star2,star3,star4,star5;
        private HomeViewHolder(View itemView) {
            super(itemView);
            chefName=itemView.findViewById(R.id.home_chef_name);
            chefAddress=itemView.findViewById(R.id.home_chef_address);
            chefDescription=itemView.findViewById(R.id.home_chef_description);
            mealName=itemView.findViewById(R.id.home_meal_name);
            mealDescription=itemView.findViewById(R.id.home_meal_description);
            mealImage=itemView.findViewById(R.id.home_meal_image);
            chefImage=itemView.findViewById(R.id.home_chef_image);
            orderMeal=itemView.findViewById(R.id.home_order_your_meal);
            sellerType=itemView.findViewById(R.id.home_chef_seller_type);
            chefRating=itemView.findViewById(R.id.home_chef_rating);
            star1=itemView.findViewById(R.id.home_star_one);
            star2=itemView.findViewById(R.id.home_star_two);
            star3=itemView.findViewById(R.id.home_star_three);
            star4=itemView.findViewById(R.id.home_star_four);
            star5=itemView.findViewById(R.id.home_star_five);
            rating_section=itemView.findViewById(R.id.rating_section);
            }
    }
}