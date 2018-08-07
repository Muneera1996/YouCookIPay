package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateDeliveryAddressActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu;
    ImageView filter,update;
    EditText street,area,city,code;
    LinearLayout layout1,layout2;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        update=findViewById(R.id.updateDeliveryAddress_update);
        street=findViewById(R.id.updateDeliveryAddress_street);
        area=findViewById(R.id.updateDeliveryAddress_area);
        city=findViewById(R.id.updateDeliveryAddress_city);
        code=findViewById(R.id.updatedeliveryAddress_postalCode);

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
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(isConnected()){
                    if (street.getText().toString().trim().equals("") || area.getText().toString().trim().equals("") ||
                        city.getText().toString().trim().equals("") || code.getText().toString().trim().equals(""))
                        Toast.makeText(getApplicationContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                    else {
                        String url = "http://www.businessmarkaz.com/test/ucookipayws/user/sign_up";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                     Log.d("Response", response);
                                     try {

                                            JSONObject obj = new JSONObject(response);
                                            String message = obj.getString("message");
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                            if (message.equalsIgnoreCase("signup successfull")) {
                                                Intent intent = new Intent(getApplicationContext(), OrderScreen1Activity.class);
                                                startActivity(intent);
                                         }

                                        } catch (Throwable t) {
                                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                                        }

                                    }

                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error
                                        Log.d("Error.Response", error.getMessage());
                                        Toast.makeText(getApplicationContext(), "Some error occur", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();

                                params.put("street", street.getText().toString());
                                params.put("resturant", area.getText().toString());
                                params.put("price", code.getText().toString());
                                params.put("description", city.getText().toString());
                                return params;
                            }
                        };
                        queue.add(postRequest);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Check Your Network Connection", Toast.LENGTH_SHORT).show();
                }*/
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
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
