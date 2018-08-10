package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button add_meals,view_meal_add,update;
    View home_menu;
    ImageView filter,profile_info,star1,star2,star3,star4,star5,image;
    TextView profile_description,name,place,type,rating;
    String message;
    private double rate = 0;
    ArrayList<User> arrayList;
    static ArrayList<Chef_Profile> myProfile;
    RecyclerView recyclerView;
    ArrayList<ViewYourAd> mealsList;
    LinearLayout layout,profile_layout;
    ProgressBar mProgressBar;
    public static boolean check= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recyclerview_view_my_ad);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=LoginInActivity.users;
        myProfile=new ArrayList<>();
        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        profile_description=findViewById(R.id.profile_Description);
        layout = (LinearLayout) findViewById(R.id.progressbar_view);
        profile_layout = (LinearLayout) findViewById(R.id.profile_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);
        profile_info=findViewById(R.id.myProfile_edit);
        image=findViewById(R.id.myProfile_image);
        name=findViewById(R.id.myProfile_name);
        place=findViewById(R.id.myProfile_place);
        type=findViewById(R.id.myProfile_type);
        rating=findViewById(R.id.myProfile_rating);
        star1=findViewById(R.id.myProfile_star_one);
        star2=findViewById(R.id.myProfile_star_two);
        star3=findViewById(R.id.myProfile_star_three);
        star4=findViewById(R.id.myProfile_star_four);
        star5=findViewById(R.id.myProfile_star_five);

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "There is no Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else {
            check=false;
            callingAPI();
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        add_meals=findViewById(R.id.add_meals_ad);
        add_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddMealADActivity.class);
                intent.putExtra("Activity", "Add");
                startActivity(intent);
            }
        });
        view_meal_add=findViewById(R.id.view_ad);
        view_meal_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewYrAdActivity.class);
                startActivity(intent);
            }
        });

        update=findViewById(R.id.profile_updateDescription);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UpdateDescriptionActivity.class);
                startActivity(i);
            }
        });
        profile_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UpdateDescriptionActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        if(check) {
            callingAPI();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    profile_description.setText(data.getStringExtra("message"));
                }
                break;
            }
        }
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
            intent.putExtra("ChefId",HomeActivity.arrayList.get(0).getUser_id());
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public void callingAPI(){
        RequestQueue queue=AppController.getInstance().getRequestQueue();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealsList=new ArrayList<>();
        final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/chef_profile?user_id=" + arrayList.get(0).getUser_id() + "&session_id=" + arrayList.get(0).getSession_id() + "&chef_id=" + arrayList.get(0).getUser_id();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // display response
                        Log.d("Response", response.toString());
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        ProfileViewChef profileViewChef  = gson.fromJson(response.toString(),ProfileViewChef.class);
                        JSONObject obj = null;
                        try {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.GONE);
                            obj = new JSONObject(response.toString());
                            JSONObject user=obj.getJSONObject("data");
                            String id=user.getString("user_id");
                            name.setText(user.getString("user_name"));
                            place.setText(user.getString("user_adress"));
                            type.setText(user.getString("seller_type"));
                            profile_description.setText(user.getString("user_description"));
                            rating.setText(user.getString("rating"));
                            String imageUrl=user.getString("user_image");
                            profile_layout.setVisibility(View.VISIBLE);


                            if(!rating.getText().toString().trim().isEmpty()){
                                rate = Double.parseDouble(rating.getText().toString());
                            }

                            if(rate>=1&&rate<2) {
                                Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);

                            }
                            else if(rate>=2&&rate<3) {
                                Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
                                Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
                            }
                            else if(rate>=3&&rate<4) {
                                Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
                                Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
                                Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);

                            }
                            else if(rate>=4&&rate<5) {
                                Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
                                Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
                                Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
                                Glide.with(star4.getContext()).load(R.drawable.fill_star).into(star4);
                            }
                            else if(rate>=5){
                                Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
                                Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
                                Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
                                Glide.with(star4.getContext()).load(R.drawable.fill_star).into(star4);
                                Glide.with(star5.getContext()).load(R.drawable.fill_star).into(star5);
                            }
                            Glide.with(image.getContext()).load(imageUrl).into(image);
                            myProfile.add(new Chef_Profile(id,name.getText().toString(),place.getText().toString(),type.getText().toString(),profile_description.getText().toString(),rating.getText().toString(),imageUrl));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        boolean success = profileViewChef.getStatus();
                        String message = profileViewChef.getMessage();
                        try {
                            if (success) {
                                JSONObject jsonObject=response.getJSONObject("data");
                                JSONArray jsonArray = jsonObject.getJSONArray("meals");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject user=jsonArray.getJSONObject(i);
                                    String meal_id=user.getString("meal_id");
                                    String meal_name=user.getString("meal_name");
                                    String place_name=user.getString("place_name");
                                    String meal_description=user.getString("meal_description");
                                    String classification=user.getString("classification");
                                    String category=user.getString("category");
                                    String type=user.getString("type");
                                    String portion_price=user.getString("portion_price");
                                    String meal_images= user.getJSONArray("meal_images").getString(0);
                                    mealsList.add(new ViewYourAd(meal_id,meal_name,place_name,meal_description,classification,category,type,portion_price,meal_images));
                                }
                                recyclerView.setAdapter(new ViewYourAdAdapter(getApplicationContext(),mealsList));
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
                        mProgressBar.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.GONE);
                    }
                });

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

    }
}
