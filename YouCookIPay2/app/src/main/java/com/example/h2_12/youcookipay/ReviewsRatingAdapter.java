package com.example.h2_12.youcookipay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ReviewsRatingAdapter extends RecyclerView.Adapter<ReviewsRatingAdapter.ReviewsRatingHolder>{
    private Context context;
    private ArrayList<Reviews_Rating> List;
    private double rating = 0;

    public ReviewsRatingAdapter(Context context, ArrayList<Reviews_Rating> list) {
        this.context = context;
        this.List = list;
    }

    @NonNull
    @Override
    public ReviewsRatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_see_reviews_and_ratings,parent,false);
        return new ReviewsRatingAdapter.ReviewsRatingHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsRatingHolder holder, int position) {
        final Reviews_Rating currentData=List.get(position);
        holder.name.setText(currentData.getName());
        holder.comment.setText(currentData.getComments());
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
        if(!currentData.getImage().equals("")) {
            Glide.with(holder.image.getContext()).load(currentData.getImage()).into(holder.image);
        }
        else if(currentData.getImage().equals(""))
        {
            Glide.with(holder.image.getContext()).load(R.drawable.profile_pic).into(holder.image);
        }
    }
    @Override
    public int getItemCount() {
        return List.size();
    }
    public class ReviewsRatingHolder extends RecyclerView.ViewHolder {
        TextView name,comment;
        ImageView image,star1,star2,star3,star4,star5;

        public ReviewsRatingHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.See_Reviews_username);
            comment=itemView.findViewById(R.id.See_Reviews_comments);
            star1=itemView.findViewById(R.id.see_rating_star1);
            star2=itemView.findViewById(R.id.see_rating_star2);
            star3=itemView.findViewById(R.id.see_rating_star3);
            star4=itemView.findViewById(R.id.see_rating_star4);
            star5=itemView.findViewById(R.id.see_rating_star5);
            image=itemView.findViewById(R.id.see_reviews_image);
        }
    }
}
