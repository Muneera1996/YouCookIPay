package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewYrAdActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu;
    ImageView filter;
    EditText search_bar;
    RecyclerView recyclerView;
    private ArrayList<ViewYourAd> mealsList;
    ArrayList<User> arrayList;
    public static String meal_Id;
    LinearLayout layout1;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_yr_ad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mealsList=new ArrayList<>();
        arrayList=LoginInActivity.users;
        ProfileActivity.check=true;
        recyclerView=findViewById(R.id.recyclerview_view_your_Ad);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RequestQueue queue=AppController.getInstance().getRequestQueue();
        layout1 = (LinearLayout) findViewById(R.id.progressbar_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.VISIBLE);
        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        search_bar=findViewById(R.id.search_bar);
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        else {

            final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/view_all?user_id=" + arrayList.get(0).getUser_id() + "&session_id=" + arrayList.get(0).getSession_id();
           // final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/view_all?user_id=21&session_id="+arrayList.get(0).getSession_id();
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            try {
                                mProgressBar.setVisibility(View.GONE);
                                layout1.setVisibility(View.GONE);
                                    JSONObject obj = new JSONObject(response.toString());
                                    JSONArray jsonArray=obj.getJSONArray("data");
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

                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                            mProgressBar.setVisibility(View.GONE);
                            layout1.setVisibility(View.GONE);
                        }});
            queue.add(getRequest);
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
        /*search_bar.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String searchData = search_bar.getText().toString();

                if (isConnected()) {
                    Toast.makeText(getApplicationContext(), "There is no Internet Connection", Toast.LENGTH_SHORT).show();

                }
                else {
                    final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/search?user_id=5&session_id=kdu9606kuq3amgg9tcn6rdgl63&classification=sell&category=commercial&type=food&search_term=" +searchData;

                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // display response
                                    Log.d("Response", response.toString());
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();
                                    ForgetPassword forgetPasswords = gson.fromJson(response.toString(), ForgetPassword.class);
                                    boolean success = forgetPasswords.getStatus();
                                    String message = forgetPasswords.getMessage();
                                    try {
                                        if (!success) {
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Throwable t) {
                                        Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Error.Response", error.getMessage());
                                }});
                    queue.add(getRequest);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });*/

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

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        } }
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
        else if (id == R.id.nav_promotional_videos) {

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
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
