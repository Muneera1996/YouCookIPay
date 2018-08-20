package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMealADActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu,sell,donate,commercial_food,private_food,food,beverages,image_visibilty;
    ImageView sell_checkbox,donate_checkbox,commercial_food_checkbox,private_food_checkbox,food_checkbox,beverages_checkbox;
    ImageView filter;
    ImageView publish,image1,image2,image3;
    String type1="",type2="",type3="",meal_id;
    EditText meal,resturant,price,description;
    ImageView upload_img1,upload_img2,upload_img3;
    private final int IMG_REQUEST1=1;
    private final int IMG_REQUEST2=2;
    private final int IMG_REQUEST3=3;
    int imageCount = 0;

    private Bitmap bitmap1,bitmap2,bitmap3;
    ArrayList<User> arrayList;
    LinearLayout layout1;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_ad2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        publish=findViewById(R.id.addMealAd_publish_btn);
        sell=findViewById(R.id.pick_sell);
        donate=findViewById(R.id.pick_donate);
        commercial_food=findViewById(R.id.pick_commercial_food);
        private_food=findViewById(R.id.pick_private_food);
        food=findViewById(R.id.pick_food);
        beverages=findViewById(R.id.pick_beverages);
        sell_checkbox=findViewById(R.id.checkbox_sell);
        donate_checkbox=findViewById(R.id.checkbox_donate);
        commercial_food_checkbox=findViewById(R.id.checkbox_commercial);
        private_food_checkbox=findViewById(R.id.checkbox_private_food);
        food_checkbox=findViewById(R.id.checkbox_food);
        beverages_checkbox=findViewById(R.id.checkbox_beverages);
        meal=findViewById(R.id.addMeal_meal_name);
        resturant=findViewById(R.id.addMeal_resturant_name);
        price=findViewById(R.id.addMeal_price);
        description=findViewById(R.id.addMeal_description);
        upload_img1=findViewById(R.id.addMealAd_upload_image1);
        upload_img2=findViewById(R.id.addMealAd_upload_image2);
        upload_img3=findViewById(R.id.addMealAd_upload_image3);
        image1=findViewById(R.id.addMealAd_image1);
        image2=findViewById(R.id.addMealAd_image2);
        image3=findViewById(R.id.addMealAd_image3);
        image_visibilty=findViewById(R.id.add_meals_image_visibility);
        arrayList=LoginInActivity.users;
        mProgressBar=findViewById(R.id.progressBar);
        layout1=findViewById(R.id.layout_addMeal);
        Intent intent = getIntent();
        String activity = intent.getStringExtra("Activity");
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        if(LoginInActivity.users.get(0).getType().equalsIgnoreCase("buyer")) {
            MenuItem homeItem = menu.findItem(R.id.nav_home);
            MenuItem deliveryItem = menu.findItem(R.id.nav_delivery_address);
            MenuItem useAppItem = menu.findItem(R.id.nav_how_use_app);
            MenuItem aboutUsItem = menu.findItem(R.id.nav_about_us);
            MenuItem historyItem = menu.findItem(R.id.nav_order_history);
            MenuItem newOrderItem = menu.findItem(R.id.nav_new_orders);
            historyItem.setVisible(true);
            newOrderItem.setVisible(true);
            homeItem.setVisible(true);
            deliveryItem.setVisible(true);
            useAppItem.setVisible(true);
            aboutUsItem.setVisible(true);
        }
        else if (LoginInActivity.users.get(0).getType().equalsIgnoreCase("seller")) {
            MenuItem homeItem = menu.findItem(R.id.nav_home);
            MenuItem profileItem = menu.findItem(R.id.nav_Profile);
            MenuItem useAppItem = menu.findItem(R.id.nav_how_use_app);
            MenuItem aboutUsItem = menu.findItem(R.id.nav_about_us);
            MenuItem historyItem = menu.findItem(R.id.nav_order_history);
            MenuItem newOrderItem = menu.findItem(R.id.nav_new_orders);
            MenuItem reviewItem = menu.findItem(R.id.nav_reviews);
            homeItem.setVisible(true);
            profileItem.setVisible(true);
            useAppItem.setVisible(true);
            aboutUsItem.setVisible(true);
            historyItem.setVisible(true);
            newOrderItem.setVisible(true);
            reviewItem.setVisible(true);
        }
        navigationView.setNavigationItemSelectedListener(this);

        home_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FilterViewPopUp.class);
                startActivity(intent);
            }
        });
        if(activity.equalsIgnoreCase("Edit")){
            mProgressBar.setVisibility(View.VISIBLE);
            String id=intent.getStringExtra("Chef_Id");
            if (isConnected()){
                final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/view_detail?user_id=" + arrayList.get(0).getUser_id() + "&session_id=" + arrayList.get(0).getSession_id() + "&meal_id=" + id;
                // final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/view_detail?user_id=13&session_id=13l7c0d0ep4vdv6rtvecm531p4&meal_id=8;
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d("Response", response.toString());
                                try {
                                    mProgressBar.setVisibility(View.GONE);
                                    JSONObject obj = new JSONObject(response.toString());
                                    JSONObject user=obj.getJSONObject("data");
                                    meal_id = user.getString("meal_id");
                                    meal.setText(user.getString("meal_name"));
                                    resturant.setText(user.getString("place_name"));
                                    price.setText(user.getString("portion_price"));
                                    description.setText(user.getString("description"));
                                    String classification=user.getString("classification");
                                    String category = user.getString("category");
                                    String type = user.getString("type");
                                    String meal_images1 = user.getJSONArray("meal_images").getString(0);
                                    String meal_images2 = user.getJSONArray("meal_images").getString(1);
                                    String meal_images3 = user.getJSONArray("meal_images").getString(2);
                                    Glide.with(image1.getContext()).load(meal_images1).into(image1);
                                    Glide.with(image2.getContext()).load(meal_images2).into(image2);
                                    Glide.with(image3.getContext()).load(meal_images3).into(image3);

                                    image_visibilty.setVisibility(View.VISIBLE);
                                    if(classification.equalsIgnoreCase("sell")){
                                        type1="sell";
                                        sell_checkbox.setImageResource(R.drawable.circle);
                                        donate_checkbox.setImageResource(R.drawable.button_bg);
                                    }
                                    if(classification.equalsIgnoreCase("donate")){
                                        type1="donate";
                                        sell_checkbox.setImageResource(R.drawable.button_bg);
                                        donate_checkbox.setImageResource(R.drawable.circle);

                                    }
                                    if(category.equalsIgnoreCase("commercial")){
                                        type2="commercial";
                                        commercial_food_checkbox.setImageResource(R.drawable.circle);
                                        private_food_checkbox.setImageResource(R.drawable.button_bg);
                                    }
                                    if(category.equalsIgnoreCase("private")){
                                        type2="private";
                                        private_food_checkbox.setImageResource(R.drawable.circle);
                                        commercial_food_checkbox.setImageResource(R.drawable.button_bg);
                                    }
                                    if(type.equalsIgnoreCase("food")){
                                        type3="food";
                                        food_checkbox.setImageResource(R.drawable.circle);
                                        beverages_checkbox.setImageResource(R.drawable.button_bg);
                                    }
                                    if(type.equalsIgnoreCase("beverages")){
                                        type3="beverages";
                                        beverages_checkbox.setImageResource(R.drawable.circle);
                                        food_checkbox.setImageResource(R.drawable.button_bg);
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
                VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(getRequest);
                getRequest.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 30000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 30000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {
                    }
                });
                publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout1.setEnabled(false);
                        if(isConnected()) {
                            if (meal.getText().toString().trim().equals("") || resturant.getText().toString().trim().equals("") ||
                                    price.getText().toString().trim().equals("") || description.getText().toString().trim().equals("") ||
                                    type1.equals("") || type2.equals("") || type3.equals("")){
                                Toast.makeText(getApplicationContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                            layout1.setEnabled(true);
                        }

                            else {
                                mProgressBar.setVisibility(View.VISIBLE);
                                String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/edit_details";
                                VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        String resultResponse = new String(response.data);
                                        try {
                                            mProgressBar.setVisibility(View.GONE);
                                            JSONObject result = new JSONObject(resultResponse);
                                            String message = result.getString("message");
                                            Log.d("onResponseEditMeal", resultResponse);
                                            Toast.makeText(AddMealADActivity.this, message, Toast.LENGTH_SHORT).show();
                                            if (message.equalsIgnoreCase("Ad updated successfully")) {
                                                Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Log.e("Unexpected", message);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        NetworkResponse networkResponse = error.networkResponse;
                                        String errorMessage = "Unknown error";
                                        if (networkResponse == null) {
                                            if (error.getClass().equals(TimeoutError.class)) {
                                                errorMessage = "Request timeout";
                                            } else if (error.getClass().equals(NoConnectionError.class)) {
                                                errorMessage = "Failed to connect server";
                                            }
                                        } else {
                                            String result = new String(networkResponse.data);
                                            try {
                                                JSONObject response = new JSONObject(result);
                                                String status = response.getString("status");
                                                String message = response.getString("message");

                                                Log.e("Error Status", status);
                                                Log.e("Error Message", message);

                                                if (networkResponse.statusCode == 404) {
                                                    errorMessage = "Resource not found";
                                                } else if (networkResponse.statusCode == 401) {
                                                    errorMessage = message+" Please login again";
                                                } else if (networkResponse.statusCode == 400) {
                                                    errorMessage = message+ " Check your inputs";
                                                } else if (networkResponse.statusCode == 500) {
                                                    errorMessage = message+" Something is getting wrong";
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        Log.i("Error", errorMessage);
                                        error.printStackTrace();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("user_id", arrayList.get(0).getUser_id());
                                        params.put("session_id", arrayList.get(0).getSession_id());
                                        params.put("meal_id", meal_id);
                                        params.put("classification", type1);
                                        params.put("category", type2);
                                        params.put("type", type3);
                                        params.put("meal_name", meal.getText().toString());
                                        params.put("place_name", resturant.getText().toString());
                                        params.put("portion_price", price.getText().toString());
                                        params.put("meal_description", description.getText().toString());
                                        return params;
                                    }

                                    @Override
                                    protected Map<String, DataPart> getByteData() {
                                        Map<String, DataPart> params = new HashMap<>();
                                        // file name could found file base or direct access from real path
                                        // for now just get bitmap data from ImageView
                                            params.put("meal_images[0]", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), image1.getDrawable()), "image/jpeg"));
                                            params.put("meal_images[1]", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), image2.getDrawable()), "image/jpeg"));
                                            params.put("meal_images[2]", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), image3.getDrawable()), "image/jpeg"));

                                        return params;
                                    }
                                };

                                VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
                                multipartRequest.setRetryPolicy(new RetryPolicy() {
                                    @Override
                                    public int getCurrentTimeout() {
                                        return 30000;
                                    }

                                    @Override
                                    public int getCurrentRetryCount() {
                                        return 30000;
                                    }

                                    @Override
                                    public void retry(VolleyError error) throws VolleyError {

                                    }
                                });





                            }
                        }
                        else
                        {
                            Toast.makeText(AddMealADActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else if(activity.equalsIgnoreCase("Add")) {

            publish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isConnected()) {
                        if (meal.getText().toString().trim().equals("") || resturant.getText().toString().trim().equals("") ||
                                price.getText().toString().trim().equals("") || description.getText().toString().trim().equals("") ||
                                type1.equals("") || type2.equals("") || type3.equals(""))
                            Toast.makeText(getApplicationContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                        else if (imageCount!=3){
                            Toast.makeText(AddMealADActivity.this, "Meal Images are Missing", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mProgressBar.setVisibility(View.VISIBLE);
                            addMeal();
                        }
                    } else {
                        Toast.makeText(AddMealADActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        upload_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST1);
            }
        });
        upload_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST2);
            }
        });
        upload_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST3);
            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type1="sell";
                sell_checkbox.setImageResource(R.drawable.circle);
                donate_checkbox.setImageResource(R.drawable.button_bg);
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type1="donate";
                sell_checkbox.setImageResource(R.drawable.button_bg);
                donate_checkbox.setImageResource(R.drawable.circle);
            }
        });
        commercial_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type2="commercial";
                commercial_food_checkbox.setImageResource(R.drawable.circle);
                private_food_checkbox.setImageResource(R.drawable.button_bg);
            }
        });
        private_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type2="private";
                private_food_checkbox.setImageResource(R.drawable.circle);
                commercial_food_checkbox.setImageResource(R.drawable.button_bg);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type3="food";
                food_checkbox.setImageResource(R.drawable.circle);
                beverages_checkbox.setImageResource(R.drawable.button_bg);
            }
        });
        beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type3="beverages";
                beverages_checkbox.setImageResource(R.drawable.circle);
                food_checkbox.setImageResource(R.drawable.button_bg);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Profile) {
            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            Intent intent=new Intent(getApplicationContext(),OrderHistory1Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_delivery_address) {
            Intent intent=new Intent(getApplicationContext(),UpdateDeliveryAddressActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_about_us) {
            Intent intent=new Intent(getApplicationContext(),AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_how_use_app) {
            Intent intent=new Intent(getApplicationContext(),HowToUseAppActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_new_orders) {
            Intent intent = new Intent(getApplicationContext(), NewOrdersActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_reviews) {
            Intent intent=new Intent(getApplicationContext(),SeeReviewAndRatingActivity.class);
            intent.putExtra("ChefId",arrayList.get(0).getUser_id());
            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            LogoutApi();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST1&&resultCode==RESULT_OK&&data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                image1.setImageBitmap(bitmap1);
                image_visibilty.setVisibility(View.VISIBLE);
                imageCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode==IMG_REQUEST2&&resultCode==RESULT_OK&&data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                image2.setImageBitmap(bitmap2);
                image_visibilty.setVisibility(View.VISIBLE);
                imageCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode==IMG_REQUEST3&&resultCode==RESULT_OK&&data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                image3.setImageBitmap(bitmap3);
                image_visibilty.setVisibility(View.VISIBLE);
                imageCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private void addMeal() {
        // loading or check internet connection or something...
        // ... then
        String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/publish";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    mProgressBar.setVisibility(View.GONE);
                    JSONObject result = new JSONObject(resultResponse);
                    String message = result.getString("message");
                    Toast.makeText(AddMealADActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (message.equalsIgnoreCase("Ad successfully published")) {
                        Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e("Unexpected", message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", arrayList.get(0).getUser_id());
                params.put("session_id", arrayList.get(0).getSession_id());
                params.put("classification", type1);
                params.put("category", type2);
                params.put("type", type3);
                params.put("meal_name", meal.getText().toString());
                params.put("place_name", resturant.getText().toString());
                params.put("portion_price", price.getText().toString());
                params.put("meal_description", description.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("meal_images[0]", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), image1.getDrawable()), "image/jpeg"));
                params.put("meal_images[1]", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), image2.getDrawable()), "image/jpeg"));
                params.put("meal_images[2]", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), image3.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
        multipartRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

    }
    public void LogoutApi() {
        final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/logout?user_id=" + LoginInActivity.users.get(0).getUser_id() + "&session_id=" + LoginInActivity.users.get(0).getSession_id();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            JSONObject obj = new JSONObject(response.toString());
                            String message = obj.getString("message");
                            Boolean status = obj.getBoolean("status");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            if (status) {
                                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }

        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
        getRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }
}
