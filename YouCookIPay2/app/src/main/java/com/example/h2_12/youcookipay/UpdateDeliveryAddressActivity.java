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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateDeliveryAddressActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu,update;
    EditText street,area,city,code;
    ProgressBar mProgressBar,mProgressBar2;
    RecyclerView recyclerView;
    ArrayList<String> streets;
    ArrayList<String> areas;
    ArrayList<String> cities;
    ArrayList<String> codes;

    private AutoCompleteTextView street_txt;
    private AutoCompleteTextView area_txt;
    private AutoCompleteTextView city_txt;
    private AutoCompleteTextView code_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        recyclerView=findViewById(R.id.recyclerview_updateDelivery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        streets=new ArrayList<>();
        areas=new ArrayList<>();
        cities=new ArrayList<>();
        codes=new ArrayList<>();

        home_menu=findViewById(R.id.home_menu);
        update=findViewById(R.id.updateDeliveryAddress_update);
        street_txt=findViewById(R.id.updateDeliveryAddress_street);
        area_txt=findViewById(R.id.updateDeliveryAddress_area);
        city_txt=findViewById(R.id.updateDeliveryAddress_city);
        code_txt=findViewById(R.id.updatedeliveryAddress_postalCode);

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
        updateDeliveryAddress();

        navigationView.setNavigationItemSelectedListener(this);
        if (isConnected()){
            mProgressBar.setVisibility(View.VISIBLE);
            updateAddress();
        }
        else {
            Toast.makeText(this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        home_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    if (street_txt.getText().toString().trim().equals("") || area_txt.getText().toString().trim().equals("") ||
                        city_txt.getText().toString().trim().equals("") || code_txt.getText().toString().trim().equals(""))
                        Toast.makeText(getApplicationContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                    else {
                        mProgressBar2.setVisibility(View.VISIBLE);
                        update();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }

    private void update() {
        String url = "http://www.businessmarkaz.com/test/ucookipayws/user/add_delivery_address";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            mProgressBar2.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            if (message.equalsIgnoreCase("delivery addresses added successfully")) {
                                street_txt.setText("");
                                city_txt.setText("");
                                area_txt.setText("");
                                code_txt.setText("");
                                updateAddress();

                            }

                        } catch (Throwable t) {
                            mProgressBar2.setVisibility(View.GONE);
                            Log.e("Update Delivery Address", "Could not parse malformed JSON: \"" + response + "\"");
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        mProgressBar2.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Some error occur", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",LoginInActivity.users.get(0).getUser_id());
                params.put("session_id",LoginInActivity.users.get(0).getSession_id());
                params.put("street", street_txt.getText().toString());
                params.put("area", area_txt.getText().toString());
                params.put("postal_code", code_txt.getText().toString());
                params.put("city", city_txt.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(postRequest);
        postRequest.setRetryPolicy(new RetryPolicy() {
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
    private void updateDeliveryAddress(){
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
                                JSONArray code = data.getJSONArray("postal_codes");
                                for (int i = 0; i < code.length(); i++) {
                                    if (!codes.contains(code.getString(i)))
                                        codes.add(code.getString(i));
                                }
                                setSpinner(streets, areas, cities,codes);

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
    }

    private void updateAddress(){
        final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/delivery_addresses?user_id="+LoginInActivity.users.get(0).getUser_id()+"&session_id="+LoginInActivity.users.get(0).getSession_id();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            ArrayList<String> streets=new ArrayList<>();
                            ArrayList<String> areas=new ArrayList<>();
                            ArrayList<String> cities=new ArrayList<>();
                            ArrayList<String> codes=new ArrayList<>();

                            JSONObject obj = new JSONObject(response.toString());
                            Boolean status=obj.getBoolean("status");
                            JSONObject data=obj.getJSONObject("data");
                            if(status){
                                mProgressBar.setVisibility(View.GONE);
                                JSONArray street=data.getJSONArray("streets");
                                for(int i=0;i<street.length();i++)
                                    streets.add(street.getString(i));
                                JSONArray area=data.getJSONArray("areas");
                                for(int i=0;i<area.length();i++)
                                    areas.add(area.getString(i));
                                JSONArray city=data.getJSONArray("cities");
                                for(int i=0;i<city.length();i++)
                                    cities.add(city.getString(i));
                                JSONArray code=data.getJSONArray("postal_codes");
                                for(int i=0;i<code.length();i++)
                                    codes.add(code.getString(i));
                            }
                            recyclerView.setAdapter(new DeliveryAddressAdapter(getApplicationContext(),streets,areas,cities,codes));


                        } catch (Throwable t) {
                            mProgressBar.setVisibility(View.GONE);
                            Log.e("Order Screen", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        mProgressBar.setVisibility(View.GONE);

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
    }
    private void setSpinner(ArrayList<String> streets, ArrayList<String> areas, ArrayList<String> cities,ArrayList<String> codes) {
        ArrayAdapter<String> streetAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,streets);
        street_txt.setAdapter(streetAdapter);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,areas);
        area_txt.setAdapter(areaAdapter);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,cities);
        city_txt.setAdapter(cityAdapter);
        ArrayAdapter<String> codeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,codes);
        code_txt.setAdapter(codeAdapter);

    }
}
