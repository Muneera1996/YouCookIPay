package com.example.h2_12.youcookipay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocialViewPopUpActivity extends AppCompatActivity {
    View signup_btn,seller_option,consumer_option,seller_type,homemade_option,restaurant_option;
    ImageView checkBox_seller,checkBox_consumer,checkbox_homemade,checkbox_restaurant;
    String sellerType="";
    String type = "";
    public static ArrayList<User> users;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_view_pop_up);

        signup_btn=findViewById(R.id.signup_btn);
        checkBox_consumer=findViewById(R.id.checkbox_Consumer);
        checkBox_seller=findViewById(R.id.checkbox_seller);
        seller_option=findViewById(R.id.seller_option);
        consumer_option=findViewById(R.id.consumer_option);
        seller_type=findViewById(R.id.sign_seller_type);
        homemade_option=findViewById(R.id.homemade_option);
        restaurant_option=findViewById(R.id.restaurant_option);
        checkbox_homemade=findViewById(R.id.checkbox_Homemade);
        checkbox_restaurant=findViewById(R.id.checkbox_Restaurant);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        Intent intent = getIntent();
        final String social_type = intent.getStringExtra("Social_Type");
        final String social_id = intent.getStringExtra("Social_Id");
        Log.v("social_type",social_type);
        Log.v("social_id",social_id);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9),(int)(height*.3));
        homemade_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerType="Homemade";
                checkbox_homemade.setImageResource(R.drawable.ic_check_box);
                checkbox_restaurant.setImageResource(R.drawable.checkbox);
            }
        });
        restaurant_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerType="Restaurant";
                checkbox_homemade.setImageResource(R.drawable.checkbox);
                checkbox_restaurant.setImageResource(R.drawable.ic_check_box);
            }
        });
        seller_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="seller";
                checkBox_consumer.setImageResource(R.drawable.checkbox);
                checkBox_seller.setImageResource(R.drawable.ic_check_box);
                seller_type.setVisibility(View.VISIBLE);
            }
        });
        consumer_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="buyer";
                sellerType="";
                checkBox_seller.setImageResource(R.drawable.checkbox);
                checkBox_consumer.setImageResource(R.drawable.ic_check_box);
                seller_type.setVisibility(View.GONE);
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                String url = "http://www.businessmarkaz.com/test/ucookipayws/user/social_login";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                mProgressBar.setVisibility(View.GONE);
                                Log.d("Response", response);
                                // JSONObject parser = new JSONObject();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    String message = obj.getString("message");
                                    boolean status = obj.getBoolean("status");
                                    if (status) {
                                        JSONObject data = obj.getJSONObject("data");
                                        String user_id = data.getString("user_id");
                                        String session_id = data.getString("session_id");
                                        String type = data.getString("type");
                                        String name = data.getString("name");
                                        String email = data.getString("email");
                                        users = new ArrayList<>();
                                        users.add(new User(user_id,session_id,type,name,email));
                                        LoginInActivity.users=users;
                                        Toast.makeText(SocialViewPopUpActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent); }
                                    else {
                                        Toast.makeText(SocialViewPopUpActivity.this, message, Toast.LENGTH_SHORT).show();
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
                                Log.d("Error.Response", error.toString());
                                mProgressBar.setVisibility(View.GONE);
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("social_type", social_type);
                        params.put("social_id", social_id);
                        params.put("device_type", "android");
                        params.put("device_token", "token");
                        params.put("user_type",type);
                        if(type.equalsIgnoreCase("seller"))
                        params.put("seller_type", sellerType);
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
        });
    }
}
