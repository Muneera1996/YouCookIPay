package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

public class FilterViewPopUp extends AppCompatActivity {
    View commercial,beverages,privte,food,purchase,donation;
    ImageView commercial_checkbox,beverages_checkbox,privte_checkbox,food_checkbox,purchase_checkbox,donation_checkbox;
    String classification="",category="",type="";
    ImageView search;
    ArrayList<User> arrayList;
    static ArrayList<Datum> homeList;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view_pop_up);

        final RequestQueue queue=AppController.getInstance().getRequestQueue();

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.9),(int)(height*.5));

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        commercial=findViewById(R.id.filter_commercial);
        beverages=findViewById(R.id.filter_beverages);
        privte=findViewById(R.id.filter_private);
        food=findViewById(R.id.filter_food);
        purchase=findViewById(R.id.filter_purchase);
        donation=findViewById(R.id.filter_donation);
        commercial_checkbox=findViewById(R.id.checkbox_commercial_food);
        beverages_checkbox=findViewById(R.id.checkbox_beverage);
        privte_checkbox=findViewById(R.id.checkbox_private_food);
        food_checkbox=findViewById(R.id.checkbox_food);
        purchase_checkbox=findViewById(R.id.checkbox_purchase);
        donation_checkbox=findViewById(R.id.checkbox_donation);
        arrayList=LoginInActivity.users;
        search=findViewById(R.id.filter_view_search_btn);
        commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="commercial";
                commercial_checkbox.setImageResource(R.drawable.ic_check_box);
                privte_checkbox.setImageResource(R.drawable.checkbox);

            }
        });
        beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="beverage";
                beverages_checkbox.setImageResource(R.drawable.ic_check_box);
                food_checkbox.setImageResource(R.drawable.checkbox);
            }
        });
        privte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="private";
                commercial_checkbox.setImageResource(R.drawable.checkbox);
                privte_checkbox.setImageResource(R.drawable.ic_check_box);

            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="food";
                beverages_checkbox.setImageResource(R.drawable.checkbox);
                food_checkbox.setImageResource(R.drawable.ic_check_box);

            }
        });
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classification="sell";
                purchase_checkbox.setImageResource(R.drawable.ic_check_box);
                donation_checkbox.setImageResource(R.drawable.checkbox);
            }
        });
        donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classification="donate";
                purchase_checkbox.setImageResource(R.drawable.checkbox);
                donation_checkbox.setImageResource(R.drawable.ic_check_box);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isConnected()) {
                    Toast.makeText(getApplicationContext(), "There is no Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    homeList=new ArrayList<>();
                    final String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/home?user_id="+arrayList.get(0).getUser_id()+"&session_id="+arrayList.get(0).getSession_id()+"&classification="+classification+"&category="+category+"&type="+type;
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    mProgressBar.setVisibility(View.GONE);
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
                                                String  meal_images = user.getJSONArray("meal_images").getString(0);

                                             /*  ArrayList<String> mylist = new ArrayList<String>();

                                                for(int ii=0;ii<3;ii++) {
                                                    String  meal_images = user.getJSONArray("meal_images").getString(ii);
                                                    if(meal_images.equalsIgnoreCase("")){
                                                        break;
                                                    }
                                                    mylist.add(meal_images); //this adds an element to the list.
                                                }*/
                                                homeList.add(new Datum(user_id,user_name,user_description,user_address,user_image,rating,seller_type,meal_id,meal_name,place_name,meal_description,classification,category,type,portion_price,meal_images));
                                            }
                                        }
                                        if (homeList.size()>0) {
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                            intent.putExtra("Filter","filter");
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(FilterViewPopUp.this, "No Result Found", Toast.LENGTH_SHORT).show();
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
                                    mProgressBar.setVisibility(View.GONE);


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
        });
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
