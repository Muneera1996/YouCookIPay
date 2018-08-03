package com.example.h2_12.youcookipay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewYourAdAdapter extends RecyclerView.Adapter<ViewYourAdAdapter.ViewYourAdHolder>{

    private Context context;
    private ArrayList<ViewYourAd> mealList;
    ArrayList<User> arrayList;

    public ViewYourAdAdapter(@NonNull Context context, ArrayList<ViewYourAd> mealList) {
        this.context = context;
        this.mealList = mealList;
        arrayList=LoginInActivity.users;
    }
    public ViewYourAdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_view_your_ad,parent,false);
        return new ViewYourAdHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewYourAdHolder holder, final int position) {
        final ViewYourAd currentMeal=mealList.get(position);
        holder.dishName.setText(currentMeal.getMealName());
        holder.dishPrice.setText(currentMeal.getPortionPrice());
        // Glide.with(holder.dishPic.getContext()).load(currentMeal.getMealImageUrl()).into((ImageView) holder.dishPic);
        Glide
                .with(holder.dishPic.getContext())
                .asBitmap()
                .load(currentMeal.getMealImages())
                .into(new SimpleTarget<Bitmap>(120,170) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable dr = new BitmapDrawable(resource);
                        holder.dishPic.setBackgroundDrawable(dr);
                    }
                });
        holder.slidingOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewYrAdActivity.meal_Id=currentMeal.getMealId();
                PopupMenu popup = new PopupMenu(holder.slidingOption.getContext(), holder.slidingOption);

                popup.getMenuInflater()
                        .inflate(R.menu.profile_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.action_delete){
                            final RequestQueue queue = Volley.newRequestQueue(context);

                            final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/delete_ad?user_id=" + arrayList.get(0).getUser_id() + "&session_id=" + arrayList.get(0).getSession_id() + "&meal_id=" + currentMeal.getMealId();
                            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // display response
                                            Log.d("Response", response.toString());
                                            try {
                                                JSONObject obj = new JSONObject(response.toString());
                                                String message=obj.getString("message");
                                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                if (message.equalsIgnoreCase("Meal ad deleted successfully"))
                                                {

                                                    mealList.remove(position);
                                                    notifyItemRemoved(position);
                                                    //this line below gives you the animation and also updates the
                                                    //list items after the deleted item
                                                    notifyItemRangeChanged(position, getItemCount());
                                                    ProfileActivity.check=true;
                                                }
                                                } catch (Throwable t) {
                                                Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d("Error.Response", error.toString());
                                        }});
                            queue.add(getRequest);
                        }
                        if(item.getItemId()==R.id.action_edit){
                            Intent intent=new Intent(holder.slidingOption.getContext(),AddMealADActivity.class);
                            intent.putExtra("Activity", "Edit");
                            intent.putExtra("Chef_Id",currentMeal.getMealId());
                            holder.slidingOption.getContext().startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
    }
    @Override
    public int getItemCount() {
        return mealList.size();
    }
    public class ViewYourAdHolder extends RecyclerView.ViewHolder{

        View dishPic;
        TextView dishName,dishPrice;
        ImageView slidingOption;
        public ViewYourAdHolder(View itemView) {
            super(itemView);
            dishName=itemView.findViewById(R.id.viewyourmeal_dish_name);
            dishPrice=itemView.findViewById(R.id.viewyourmeal_dish_price);
            dishPic=itemView.findViewById(R.id.viewyourmeal_dish_image);
            slidingOption=itemView.findViewById(R.id.viewyourmeal_sliding_btn);
            }}
}
