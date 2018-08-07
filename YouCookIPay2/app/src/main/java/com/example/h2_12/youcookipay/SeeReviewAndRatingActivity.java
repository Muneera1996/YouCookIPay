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

public class SeeReviewAndRatingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu;
    ImageView filter;
    RecyclerView recyclerView;
    ArrayList<Reviews_Rating> ratingList;
    ArrayList<User> arrayList;
    ProgressBar mProgressBar;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_review_and_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        recyclerView=findViewById(R.id.recyclerview_SeeReviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        layout=findViewById(R.id.progressbar_view);
        layout.setVisibility(View.VISIBLE);
        arrayList=LoginInActivity.users;
        ratingList=new ArrayList<>();
        Intent intent = getIntent();
        String id = intent.getStringExtra("ChefId");
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
                startActivity(intent); }
        });
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "There is no Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else {
            final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/view_seller_reviews?user_id="+arrayList.get(0).getUser_id()+"&session_id="+arrayList.get(0).getSession_id()+"&seller_id="+id;
            final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            mProgressBar.setVisibility(View.GONE);
                            layout.setVisibility(View.GONE);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            Home home  = gson.fromJson(response.toString(),Home.class);
                            boolean success = home.getStatus();
                            try {
                                if (success) {
                                    JSONObject obj = new JSONObject(response.toString());
                                    JSONArray jsonArray=obj.getJSONArray("data");
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject user=jsonArray.getJSONObject(i);
                                        String user_id=user.getString("user_id");
                                        String user_name=user.getString("user_name");
                                        String user_image=user.getString("user_image");
                                        String rating=user.getString("rating");
                                        String comments=user.getString("text");
                                        ratingList.add(new Reviews_Rating(user_id,user_name,rating,comments,user_image));
                                    }
                                    recyclerView.setAdapter(new ReviewsRatingAdapter(getApplicationContext(),ratingList));
                                }
                            } catch (Throwable t) {
                                Log.e("see Reviews and Rating", "Could not parse malformed JSON: \"" + response + "\"");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response",error.toString());
                            mProgressBar.setVisibility(View.GONE);
                            layout.setVisibility(View.GONE);

                        }});
            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(getRequest);
            getRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 20000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 20000;
                }
                @Override
                public void retry(VolleyError error) throws VolleyError {
                }
            });
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
