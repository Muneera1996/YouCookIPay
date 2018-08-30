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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistory1Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu;
    ProgressBar mProgressBar;
    RecyclerView recyclerView;
    ArrayList<OrderHistory> historyList;
    TextView order_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        home_menu=findViewById(R.id.home_menu);
        historyList=new ArrayList<>();
        order_history=findViewById(R.id.no_history);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerview_orderHistory1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView user_name = (TextView) header.findViewById(R.id.nav_header_home_name);
        TextView user_email = (TextView) header.findViewById(R.id.nav_header_home_email);
        user_name.setText(LoginInActivity.users.get(0).getName());
        user_email.setText(LoginInActivity.users.get(0).getEmail());
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

        if (isConnected()){
            mProgressBar.setVisibility(View.VISIBLE);
            final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/order_history?user_id="+LoginInActivity.users.get(0).getUser_id()+"&session_id="+LoginInActivity.users.get(0).getSession_id();
            final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            try {
                                mProgressBar.setVisibility(View.GONE);

                                JSONObject obj = new JSONObject(response.toString());
                                Boolean status=obj.getBoolean("status");
                                String message=obj.getString("message");
                                if (status) {
                                    JSONArray jsonArray=obj.getJSONArray("data");
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject user=jsonArray.getJSONObject(i);
                                        String order_id=user.getString("order_id");
                                        String chef_id=user.getString("chef_id");
                                        String user_id=user.getString("user_id");
                                        Log.v("user_id",user_id);
                                        String meal_id=user.getString("meal_id");
                                        String meal_name=user.getString("meal_name");
                                        String meal_classification=user.getString("meal_classification");
                                        String meal_type=user.getString("meal_type");
                                        String meal_category=user.getString("meal_category");
                                        String portion_price=user.getString("portion_price");
                                        String quantity=user.getString("quantity");
                                        String total_quantity_cost=user.getString("total_quantity_cost");
                                        String service_charges=user.getString("service_charges");
                                        String delivery_charges=user.getString("delivery_charges");
                                        String grand_total=user.getString("grand_total");
                                        String user_name=user.getString("user_name");
                                        String user_email=user.getString("user_email");
                                        String user_contact=user.getString("user_contact");
                                        String date=user.getString("date");
                                        String time=user.getString("time");
                                        String delivery_options=user.getString("delivery_options");
                                        String street=user.getString("street");
                                        String city=user.getString("city");
                                        String area=user.getString("area");
                                        String payment_method=user.getString("payment_method");
                                        String transaction_id=user.getString("transaction_id");
                                        String order_status=user.getString("order_status");
                                        String chef_name=user.getString("chef_name");

                                        historyList.add(new OrderHistory(order_id,chef_id,user_id,meal_id,meal_name,meal_classification,meal_type,meal_category,portion_price,quantity,total_quantity_cost,service_charges,delivery_charges,grand_total,user_name,user_email,user_contact,date,time,delivery_options,street,city,area,payment_method,transaction_id,order_status,chef_name));

                                    }

                                    recyclerView.setAdapter(new OrderHistoryAdapter(getApplicationContext(),historyList));
                                }
                                else
                                {
                                    order_history.setVisibility(View.VISIBLE);
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
            intent.putExtra("ChefId",LoginInActivity.users.get(0).getUser_id());
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
                        Log.v("Response", response.toString());
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
