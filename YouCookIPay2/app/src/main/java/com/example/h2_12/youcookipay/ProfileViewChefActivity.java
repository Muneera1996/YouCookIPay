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
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ProfileViewChefActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu;
    ImageView image,star1,star2,star3,star4,star5;
    RecyclerView recyclerView;
    private ArrayList<Meal> mealsList;
    ArrayList<User> arrayList;
    static ArrayList<Chef_Profile> chef_profile;
    TextView name,address,type,description,rating;
    private double rate=0;
    static String iid;
    LinearLayout layout1,layout2;
    ProgressBar mProgressBar;
    View rating_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view_chef);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        home_menu=findViewById(R.id.home_menu);
        mealsList=new ArrayList<>();
        chef_profile=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview_profile_view_chef);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        name=findViewById(R.id.profileviewchef_name);
        address=findViewById(R.id.profileviewchef_address);
        type=findViewById(R.id.profileviewchef_sellertype);
        description=findViewById(R.id.profileviewchef_description);
        rating=findViewById(R.id.profileviewchef_rating);
        image=findViewById(R.id.profileviewchef_image);
        star1=findViewById(R.id.profile_star_one);
        star2=findViewById(R.id.profile_star_two);
        star3=findViewById(R.id.profile_star_three);
        star4=findViewById(R.id.profile_star_four);
        star5=findViewById(R.id.profile_star_five);
        layout1 = (LinearLayout) findViewById(R.id.progressbar_view);
        layout2 = (LinearLayout) findViewById(R.id.layout_profileViewChef);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.VISIBLE);
        rating_view=findViewById(R.id.profile_chef_rating_view);
        Intent intent = getIntent();
        String id = intent.getStringExtra("Chef_Id");
        iid=id;

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        arrayList=LoginInActivity.users;
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
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else {
            final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/chef_profile?user_id=" + arrayList.get(0).getUser_id() + "&session_id=" + arrayList.get(0).getSession_id() + "&chef_id=" + id;
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            mProgressBar.setVisibility(View.GONE);
                            layout1.setVisibility(View.GONE);
                            layout2.setVisibility(View.VISIBLE);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            ProfileViewChef profileViewChef  = gson.fromJson(response.toString(),ProfileViewChef.class);
                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(response.toString());
                                JSONObject user=obj.getJSONObject("data");
                                String id=user.getString("user_id");
                                name.setText(user.getString("user_name"));
                                address.setText(user.getString("user_adress"));
                                type.setText(user.getString("seller_type"));
                                description.setText(user.getString("user_description"));
                                rating.setText(user.getString("rating"));
                                String imageUrl=user.getString("user_image");

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
                                chef_profile.add(new Chef_Profile(id,name.getText().toString(),address.getText().toString(),type.getText().toString(),description.getText().toString(),rating.getText().toString(),imageUrl));

                                } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            boolean success = profileViewChef.getStatus();
                            try {
                                if (success) {
                                    JSONObject jsonObject=response.getJSONObject("data");
                                    JSONArray jsonArray = jsonObject.getJSONArray("meals");
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject meal=jsonArray.getJSONObject(i);
                                        String dish_id=meal.getString("meal_id");
                                        String dish_name=meal.getString("meal_name");
                                        String dish_price=meal.getString("portion_price");
                                        String dish_img_url= meal.getJSONArray("meal_images").getString(0);
                                        mealsList.add(new Meal(dish_id,dish_name,dish_price,dish_img_url));
                                        }
                                    recyclerView.setAdapter(new ChefProfileAdapter(getApplicationContext(),mealsList));

                                }

                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            layout1.setVisibility(View.GONE);
                            Log.d("Error.Response", error.toString());
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
        home_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        rating_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SeeReviewAndRatingActivity.class);
                intent.putExtra("ChefId", iid);
                startActivity(intent);
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
            intent.putExtra("ChefId",HomeActivity.arrayList.get(0).getUser_id());
            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            LogoutApi();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
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
