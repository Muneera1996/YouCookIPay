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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderScreeen2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button placeOrder;
    View home_menu,delivery,yourself;
    ImageView filter,delivery_checkbox,yourself_checkbox,image,star1,star2,star3,star4,star5;
    EditText name,email,number,date,time,street,area,city;
    String pick_option="";
    ProgressBar mProgressBar;
    TextView chef_name,chef_address,chef_type,chef_rating;
    ArrayList<Chef_Profile> profile;
    View rating_view;
    private double rate=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screeen2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile=ProfileViewChefActivity.chef_profile;
        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        rating_view=findViewById(R.id.orderScreen_ratingSection);
        yourself=findViewById(R.id.pick_option_yourself);
        delivery=findViewById(R.id.pick_option_dispatch);
        delivery_checkbox=findViewById(R.id.checkbox_dispatch);
        yourself_checkbox=findViewById(R.id.checkbox_pick);
        name=findViewById(R.id.orderScreen_name);
        email=findViewById(R.id.orderScreen_email_address);
        number=findViewById(R.id.orderScreen_number);
        date=findViewById(R.id.orderScreen_date);
        time=findViewById(R.id.orderScreen_time);
        street=findViewById(R.id.orderScreen_street);
        area=findViewById(R.id.orderScreen_area);
        city=findViewById(R.id.orderScreen_city);
        chef_name=findViewById(R.id.orderScreen1_chef_name);
        chef_address=findViewById(R.id.orderScreen1_chef_place);
        chef_rating=findViewById(R.id.orderScreen1_rating);
        chef_type=findViewById(R.id.orderScreen1_seller_type);
        image=findViewById(R.id.orderScreen1_image);
        star1=findViewById(R.id.orderScreen1_star_one);
        star2=findViewById(R.id.orderScreen1_star_two);
        star3=findViewById(R.id.orderScreen1_star_three);
        star4=findViewById(R.id.orderScreen1_star_four);
        star5=findViewById(R.id.orderScreen1_star_five);
        chef_name.setText(profile.get(0).getName());
        chef_address.setText(profile.get(0).getAddress());
        chef_type.setText(profile.get(0).getType());
        chef_rating.setText(profile.get(0).getRating());
        if(!chef_rating.getText().toString().trim().isEmpty()){
            rate = Double.parseDouble(chef_rating.getText().toString());
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
        Glide.with(image.getContext()).load(profile.get(0).getImage()).into(image);
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
        placeOrder=findViewById(R.id.orderScreen_place_order);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (isConnected()) {
                    if (name.getText().toString().trim().equals("") || email.getText().toString().trim().equals("") ||
                            number.getText().toString().trim().equals("") || date.getText().toString().trim().equals("") ||
                            time.getText().toString().trim().equals("") || street.getText().toString().trim().equals("") ||
                            area.getText().toString().trim().equals("") || city.getText().toString().trim().equals("") || pick_option.equals(""))
                        Toast.makeText(getApplicationContext(), "Fill all the details!", Toast.LENGTH_LONG).show();

                        // call AsynTask to perform network operation on separate thread
                    else {
                        RequestQueue queue = Volley.newRequestQueue(this);
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
                                params.put("name", name.getText().toString());
                                params.put("email", email.getText().toString());
                                params.put("number", number.getText().toString());
                                params.put("date", date.getText().toString());
                                params.put("time", time.getText().toString());
                                params.put("street", street.getText().toString());
                                params.put("area", area.getText().toString());
                                params.put("city", city.getText().toString());

                                return params;
                            }
                        };
                        queue.add(postRequest);
                    }

                }

                else {
                    Toast.makeText(OrderScreeen2Activity.this, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
                }*/
                Intent intent = new Intent(getApplicationContext(), OrderScreen1Activity.class);
                startActivity(intent);
            }

        });
        yourself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pick_option="Pick By yourself";
                yourself_checkbox.setImageResource(R.drawable.option_select_circle);
                delivery_checkbox.setImageResource(R.drawable.button_bg);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick_option="Dispatch at your doorstep";
                yourself_checkbox.setImageResource(R.drawable.button_bg);
                delivery_checkbox.setImageResource(R.drawable.option_select_circle);

            }
        });
        rating_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SeeReviewAndRatingActivity.class);
                intent.putExtra("ChefId", ProfileViewChefActivity.iid);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
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
