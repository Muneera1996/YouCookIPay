package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
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
import java.util.List;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     View home_menu;
     ImageView filter;
     RecyclerView recyclerView;
     private ArrayList<Datum> homeList;
     static ArrayList<User> arrayList;
     EditText search;
    LinearLayout layout;
    ProgressBar mProgressBar;
    private Menu menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        homeList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview_home);
        search=findViewById(R.id.search_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=LoginInActivity.users;
        homeList=new ArrayList<>();
        layout = (LinearLayout) findViewById(R.id.progressbar_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        String activity = intent.getStringExtra("Filter");

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "There is no Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else if (activity!=null&&activity.equalsIgnoreCase("filter"))
        {
            mProgressBar.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.GONE);
            homeList=new ArrayList<>();
            homeList=FilterViewPopUp.homeList;
            recyclerView.setAdapter(new HomeAdapter(getApplicationContext(),homeList));
        }
        else {
            final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/home?user_id=" + arrayList.get(0).getUser_id() + "&session_id=" + arrayList.get(0).getSession_id();
            final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            mProgressBar.setVisibility(View.INVISIBLE);
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
                                        String user_description=user.getString("user_description");
                                        String user_address=user.getString("user_address");
                                        String user_image=user.getString("user_image");
                                        String rating=user.getString("rating");
                                        String seller_type=user.getString("seller_type");
                                        String meal_id=user.getString("meal_id");
                                        String meal_name=user.getString("meal_name");
                                        String place_name=user.getString("place_name");
                                        String meal_description=user.getString("meal_description");
                                        String classification=user.getString("classification");
                                        String category=user.getString("category");
                                        String type=user.getString("type");
                                        String portion_price=user.getString("portion_price");
                                       ArrayList<String> mylist = new ArrayList<String>();
                                       int images=user.getJSONArray("meal_images").length();
                                        for(int ii=0;ii<images;ii++) {
                                            String  meal_images = user.getJSONArray("meal_images").getString(ii);
                                            if(meal_images != null && !meal_images.isEmpty())
                                            {
                                                mylist.add(meal_images); //this adds an element to the list.
                                                Log.i("onResponseImage", meal_images);
                                            }
                                            else
                                            {
                                                break;
                                            }
                                        }
                                        homeList.add(new Datum(user_id,user_name,user_description,user_address,user_image,rating,seller_type,meal_id,meal_name,place_name,meal_description,classification,category,type,portion_price,mylist));

                                    }
                                    recyclerView.setAdapter(new HomeAdapter(getApplicationContext(),homeList));
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
                            layout.setVisibility(View.GONE);
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
        }

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
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                final ArrayList<Datum> homeSearchData=new ArrayList<>();
                String searchData=search.getText().toString();
                if (!isConnected()) {
                    Toast.makeText(getApplicationContext(), "There is no Internet Connection", Toast.LENGTH_SHORT).show();

                }
                else {
                    final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/home?user_id=" + arrayList.get(0).getUser_id() + "&session_id=" + arrayList.get(0).getSession_id() + "&search_term=" + searchData;
                    final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // display response
                                    Log.d("Response", response.toString());
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    Gson gson = gsonBuilder.create();
                                    Home home  = gson.fromJson(response.toString(),Home.class);
                                    boolean success = home.getStatus();
                                    String message = home.getMessage();
                                    try {
                                        if (success) {
                                            JSONObject obj = new JSONObject(response.toString());
                                            JSONArray jsonArray=obj.getJSONArray("data");
                                            for(int i=0;i<jsonArray.length();i++){
                                                JSONObject user=jsonArray.getJSONObject(i);
                                                String user_id=user.getString("user_id");
                                                String user_name=user.getString("user_name");
                                                String user_description=user.getString("user_description");
                                                String user_address=user.getString("user_address");
                                                String user_image=user.getString("user_image");
                                                String rating=user.getString("rating");
                                                String seller_type=user.getString("seller_type");
                                                String meal_id=user.getString("meal_id");
                                                String meal_name=user.getString("meal_name");
                                                String place_name=user.getString("place_name");
                                                String meal_description=user.getString("meal_description");
                                                String classification=user.getString("classification");
                                                String category=user.getString("category");
                                                String type=user.getString("type");
                                                String portion_price=user.getString("portion_price");
                                              // String  meal_images = user.getJSONArray("meal_images").getString(0);
                                                ArrayList<String> mylist = new ArrayList<String>();
                                                int images=user.getJSONArray("meal_images").length();
                                                for(int ii=0;ii<images;ii++) {
                                                    String  meal_images = user.getJSONArray("meal_images").getString(ii);
                                                    if(meal_images != null && !meal_images.isEmpty())
                                                    {
                                                        mylist.add(meal_images); //this adds an element to the list.
                                                        Log.i("onResponseImage", meal_images);
                                                    }
                                                    else
                                                    {
                                                        break;
                                                    }
                                                }
                                                homeSearchData.add(new Datum(user_id,user_name,user_description,user_address,user_image,rating,seller_type,meal_id,meal_name,place_name,meal_description,classification,category,type,portion_price,mylist));
                                            }
                                            recyclerView.setAdapter(new HomeAdapter(getApplicationContext(),homeSearchData));
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

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Create your menu...
        if(LoginInActivity.users.get(0).getType().equalsIgnoreCase("buyer")) {
            MenuItem homeItem = menu.findItem(R.id.nav_home);
            MenuItem deliveryItem = menu.findItem(R.id.nav_delivery_address);
            MenuItem useAppItem = menu.findItem(R.id.nav_how_use_app);
            MenuItem aboutUsItem = menu.findItem(R.id.nav_about_us);

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
        return true;
    }*/


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
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
     private void displaySelectedScreen(int itemId) {

        //initializing the fragment object which is selected
        Toast.makeText(this, LoginInActivity.users.get(0).getType(), Toast.LENGTH_SHORT).show();

       if(LoginInActivity.users.get(0).getType().equalsIgnoreCase("buyer")){
            MenuItem homeItem = menu.findItem(R.id.nav_home);
            MenuItem deliveryItem = menu.findItem(R.id.nav_delivery_address);
            MenuItem useAppItem = menu.findItem(R.id.nav_how_use_app);
            MenuItem aboutUsItem = menu.findItem(R.id.nav_about_us);

            homeItem.setVisible(true);
            deliveryItem.setVisible(true);
            useAppItem.setVisible(true);
            aboutUsItem.setVisible(true);
            switch (itemId) {
                case R.id.nav_home:
                    break;
                case R.id.nav_delivery_address:
                    Intent intent2=new Intent(getApplicationContext(),UpdateDeliveryAddressActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.nav_about_us:
                    Intent intent3=new Intent(getApplicationContext(),AboutUsActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.nav_how_use_app:
                    Intent intent4=new Intent(getApplicationContext(),HowToUseAppActivity.class);
                    startActivity(intent4);
                    break;

            }
        }
        else if (LoginInActivity.users.get(0).getType().equalsIgnoreCase("seller")){
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

            switch (itemId) {
                case R.id.nav_home:
                    break;
                case R.id.nav_Profile:
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_order_history:
                    Intent intent1 = new Intent(getApplicationContext(), OrderHistory1Activity.class);
                    startActivity(intent1);
                    break;

                case R.id.nav_about_us:
                    Intent intent3 = new Intent(getApplicationContext(), AboutUsActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.nav_how_use_app:
                    Intent intent4 = new Intent(getApplicationContext(), HowToUseAppActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.nav_new_orders:
                    Intent intent5 = new Intent(getApplicationContext(), NewOrdersActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.nav_reviews:
                    Intent intent6 = new Intent(getApplicationContext(), SeeReviewAndRatingActivity.class);
                    intent6.putExtra("ChefId", arrayList.get(0).getUser_id());
                    startActivity(intent6);
                    break;

            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
