package com.example.h2_12.youcookipay;

import android.app.DatePickerDialog;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OrderScreeen2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    View home_menu,delivery,yourself,area_street,city_view,placeOrder;
    ImageView delivery_checkbox,yourself_checkbox,image,star1,star2,star3,star4,star5;
    EditText name,email,number,date,time;
    ProgressBar mProgressBar;
    TextView chef_name,chef_address,chef_type,chef_rating;
    public static ArrayList<Chef_Profile> profile;
    ArrayList<User> user;
    public static ArrayList<OrderScreen> orderScreens;
    View rating_view;
    private double rate=0;
    String delivery_method = "";
    public static String Token = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    static ArrayList<String> streets;
    ArrayList<String> areas=new ArrayList<>();
    ArrayList<String> cities=new ArrayList<>();
    private AutoCompleteTextView street_txt;
    private AutoCompleteTextView area_txt;
    private AutoCompleteTextView city_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screeen2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String screen = intent.getStringExtra("Screen");
        final String id = intent.getStringExtra("MealId");
        final String nam = intent.getStringExtra("MealName");
        final String price = intent.getStringExtra("MealPrice");
        final String img = intent.getStringExtra("MealImage");


        user = LoginInActivity.users;
        streets = new ArrayList<>();
        if (screen.equalsIgnoreCase("profile"))
            profile = ProfileViewChefActivity.chef_profile;
        if (screen.equalsIgnoreCase("home")) {
            final String chef_id = intent.getStringExtra("Chef_Id");
            final String chef_name = intent.getStringExtra("Chef_Name");
            final String chef_address = intent.getStringExtra("Chef_Address");
            final String chef_type = intent.getStringExtra("Chef_Type");
            final String rating = intent.getStringExtra("Rating");
            final String description = intent.getStringExtra("Description");
            final String imageUrl = intent.getStringExtra("ImageUrl");

            ArrayList<Chef_Profile> chef_profile = new ArrayList<>();
            chef_profile.add(new Chef_Profile(chef_id,chef_name,chef_address,chef_type,description,rating,imageUrl));
            profile=chef_profile;


        }
        home_menu = findViewById(R.id.home_menu);
        area_street = findViewById(R.id.streetAndAreaView);
        city_view = findViewById(R.id.cityView);
        rating_view = findViewById(R.id.orderScreen_ratingSection);
        yourself = findViewById(R.id.pick_option_yourself);
        delivery = findViewById(R.id.pick_option_dispatch);
        delivery_checkbox = findViewById(R.id.checkbox_dispatch);
        yourself_checkbox = findViewById(R.id.checkbox_pick);
        name = findViewById(R.id.orderScreen_name);
        email = findViewById(R.id.orderScreen_email_address);
        number = findViewById(R.id.orderScreen_number);
        date = findViewById(R.id.orderScreen_date);
        time = findViewById(R.id.orderScreen_time);
        street_txt = findViewById(R.id.orderScreen_street);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        orderScreens = new ArrayList<>();
        area_txt = findViewById(R.id.orderScreen_area);
        city_txt = findViewById(R.id.orderScreen_city);
        chef_name = findViewById(R.id.orderScreen1_chef_name);
        chef_address = findViewById(R.id.orderScreen1_chef_place);
        chef_rating = findViewById(R.id.orderScreen1_rating);
        chef_type = findViewById(R.id.orderScreen1_seller_type);
        image = findViewById(R.id.orderScreen1_image);
        star1 = findViewById(R.id.orderScreen1_star_one);
        star2 = findViewById(R.id.orderScreen1_star_two);
        star3 = findViewById(R.id.orderScreen1_star_three);
        star4 = findViewById(R.id.orderScreen1_star_four);
        star5 = findViewById(R.id.orderScreen1_star_five);

        chef_name.setText(profile.get(0).getName());
        chef_address.setText(profile.get(0).getAddress());
        chef_type.setText(profile.get(0).getType());
        chef_rating.setText(profile.get(0).getRating());
        if (!chef_rating.getText().toString().trim().isEmpty()) {
            rate = Double.parseDouble(chef_rating.getText().toString());
        }
        if (rate >= 1 && rate < 2) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
        } else if (rate >= 2 && rate < 3) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
        } else if (rate >= 3 && rate < 4) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);

        } else if (rate >= 4 && rate < 5) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
            Glide.with(star4.getContext()).load(R.drawable.fill_star).into(star4);
        } else if (rate >= 5) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
            Glide.with(star4.getContext()).load(R.drawable.fill_star).into(star4);
            Glide.with(star5.getContext()).load(R.drawable.fill_star).into(star5);
        }
        if(!profile.get(0).getImage().equals("")) {
            Glide.with(image.getContext()).load(profile.get(0).getImage()).into(image);
        }
        else if(profile.get(0).getImage().equals(""))
        {
            Glide.with(image.getContext()).load(R.drawable.profile_pic).into(image);
        }
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
        if (LoginInActivity.users.get(0).getType().equalsIgnoreCase("buyer")) {
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
        } else if (LoginInActivity.users.get(0).getType().equalsIgnoreCase("seller")) {
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

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        OrderScreeen2Activity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String str1 = Integer.toString(month);
                if (str1.length() == 1)
                    str1 = "0" + str1;
                String str2 = Integer.toString(day);
                if (str2.length() == 1)
                    str2 = "0" + str2;
                String dates = year + "-" + str1 + "-" + str2;
                date.setText(dates);
            }
        };
        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(OrderScreeen2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        if (isConnected()) {
            final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/delivery_addresses?user_id=" + LoginInActivity.users.get(0).getUser_id() + "&session_id=" + LoginInActivity.users.get(0).getSession_id();
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            try {

                                JSONObject obj = new JSONObject(response.toString());
                                Boolean status = obj.getBoolean("status");
                                JSONObject data = obj.getJSONObject("data");
                                if (status) {
                                    JSONArray street = data.getJSONArray("streets");
                                    for (int i = 0; i < street.length(); i++) {
                                        if (!streets.contains(street.getString(i)))
                                            streets.add(street.getString(i));
                                    }
                                    JSONArray area = data.getJSONArray("areas");
                                    for (int i = 0; i < area.length(); i++) {
                                        if (!areas.contains(area.getString(i)))
                                            areas.add(area.getString(i));
                                    }
                                    JSONArray city = data.getJSONArray("cities");
                                    for (int i = 0; i < city.length(); i++) {
                                        if (!cities.contains(city.getString(i)))
                                            cities.add(city.getString(i));
                                    }
                                    setSpinner(streets, areas, cities);

                                }

                            } catch (Throwable t) {
                                Log.e("Order Screen", "Could not parse malformed JSON: \"" + response + "\"");
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
        } else {
            Toast.makeText(this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }
        if (isConnected()) {
            final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/generate_token?user_id=" + user.get(0).getUser_id() + "&session_id=" + user.get(0).getSession_id();

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            try {
                                JSONObject obj = new JSONObject(response.toString());
                                Boolean status = obj.getBoolean("status");
                                String message=obj.getString("message");
                                Log.v("token message",message);
                                JSONObject data = obj.getJSONObject("data");
                                if (status) {
                                    Token = data.getString("token");
                                    Log.v("token generated", Token);

                                }
                            } catch (Throwable t) {
                                Log.e("Order Screen", "Could not parse malformed JSON: \"" + response + "\"");
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
            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(getRequest);
        } else
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        placeOrder = findViewById(R.id.orderScreen_place_order);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    if (delivery_method.equalsIgnoreCase("")) {
                        Toast.makeText(OrderScreeen2Activity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    } else if (delivery_method.equalsIgnoreCase("take_away")) {
                        if (name.getText().toString().trim().equals("") || email.getText().toString().trim().equals("") ||
                                number.getText().toString().trim().equals("") || date.getText().toString().trim().equals("") ||
                                time.getText().toString().trim().equals("") || delivery_method.equals(""))
                            Toast.makeText(getApplicationContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                        else if(!name.getText().toString().trim().matches("[a-zA-Z ]+"))
                            Toast.makeText(OrderScreeen2Activity.this, "Please enter valid user name", Toast.LENGTH_SHORT).show();
                        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
                            Toast.makeText(OrderScreeen2Activity.this, "please enter valid email address", Toast.LENGTH_SHORT).show();
                        else if (!isValidMobile())
                            Toast.makeText(OrderScreeen2Activity.this, "please enter valid phone number", Toast.LENGTH_SHORT).show();
                        else {
                            mProgressBar.setVisibility(View.VISIBLE);
                            orderScreens.add(new OrderScreen(name.getText().toString(), email.getText().toString(), number.getText().toString(), date.getText().toString(), time.getText().toString(), delivery_method, id, nam, price, img));
                            Intent intent = new Intent(getApplicationContext(), OrderScreen1Activity.class);
                            startActivity(intent);
                            mProgressBar.setVisibility(View.GONE);


                        }
                    } else if (delivery_method.equalsIgnoreCase("home_delivery")) {
                        if (name.getText().toString().trim().equals("") || email.getText().toString().trim().equals("") ||
                                number.getText().toString().trim().equals("") || date.getText().toString().trim().equals("") ||
                                time.getText().toString().trim().equals("") || street_txt.getText().toString().trim().equals("") ||
                                area_txt.getText().toString().trim().equals("") || city_txt.getText().toString().trim().equals("") || delivery_method.equals(""))
                            Toast.makeText(getApplicationContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                        else if(!name.getText().toString().trim().matches("[a-zA-Z ]+"))
                            Toast.makeText(OrderScreeen2Activity.this, "Please enter valid user name", Toast.LENGTH_SHORT).show();
                        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
                            Toast.makeText(OrderScreeen2Activity.this, "please enter valid email address", Toast.LENGTH_SHORT).show();
                        else if (!isValidMobile())
                            Toast.makeText(OrderScreeen2Activity.this, "please enter valid phone number", Toast.LENGTH_SHORT).show();
                        else {
                            mProgressBar.setVisibility(View.VISIBLE);
                            orderScreens.add(new OrderScreen(name.getText().toString(), email.getText().toString(), number.getText().toString(), date.getText().toString(), time.getText().toString(), delivery_method, id, nam, price, img, street_txt.getText().toString(), area_txt.getText().toString(), city_txt.getText().toString()));
                            Intent intent = new Intent(getApplicationContext(), OrderScreen1Activity.class);
                            startActivity(intent);
                            mProgressBar.setVisibility(View.GONE);

                        }
                    }
                } else {
                    Toast.makeText(OrderScreeen2Activity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

        });
        yourself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delivery_method = "take_away";
                yourself_checkbox.setImageResource(R.drawable.option_select_circle);
                delivery_checkbox.setImageResource(R.drawable.button_bg);
                area_street.setVisibility(View.GONE);
                city_view.setVisibility(View.GONE);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delivery_method = "home_delivery";
                yourself_checkbox.setImageResource(R.drawable.button_bg);
                delivery_checkbox.setImageResource(R.drawable.option_select_circle);
                area_street.setVisibility(View.VISIBLE);
                city_view.setVisibility(View.VISIBLE);
            }
        });
        rating_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeeReviewAndRatingActivity.class);
                intent.putExtra("ChefId", profile.get(0).getId());
                startActivity(intent);
            }
        });
    }


    private void setSpinner(ArrayList<String> streets, ArrayList<String> areas, ArrayList<String> cities) {
        ArrayAdapter<String> streetAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,streets);
        street_txt.setAdapter(streetAdapter);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,areas);
        area_txt.setAdapter(areaAdapter);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,cities);
        city_txt.setAdapter(cityAdapter);


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
    private boolean isValidMobile()
    {
        if(number.getText().toString().trim().length() < 6 || number.getText().toString().trim().length() > 13)
        {
            return false;

        }
        return true;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        orderScreens=new ArrayList<>();
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
    public boolean isConnected(){
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
                                Intent intent = new Intent(OrderScreeen2Activity.this, SplashScreen.class);
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}
