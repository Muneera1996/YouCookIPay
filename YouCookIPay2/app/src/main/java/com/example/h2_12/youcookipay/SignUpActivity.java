package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    View signup_btn,signup_header,payment_method,gender_male,gender_female,seller_option,consumer_option,seller_type,homemade_option,restaurant_option;
    ImageView checkBox_male,checkBox_female,checkBox_seller,checkBox_consumer,checkbox_homemade,checkbox_restaurant;
    EditText name,email,password,confirm_password,phone;
    String gender="",type="",sellerType="";
    public static String id;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(!isConnected()){
            Toast.makeText(this, "Not connected with the Internet", Toast.LENGTH_SHORT).show();
        }


        signup_header=findViewById(R.id.signup_header);
        gender_male=findViewById(R.id.gender_male);
        gender_female=findViewById(R.id.gender_female);
        checkBox_male=findViewById(R.id.checkbox_male);
        checkBox_female=findViewById(R.id.checkbox_female);
        signup_btn=findViewById(R.id.signup_btn);
        checkBox_consumer=findViewById(R.id.checkbox_Consumer);
        checkBox_seller=findViewById(R.id.checkbox_seller);
        seller_option=findViewById(R.id.seller_option);
        consumer_option=findViewById(R.id.consumer_option);
        signup_header.setBackgroundColor(getResources().getColor(android.R.color.white));
        name=findViewById(R.id.signup_name);
        email=findViewById(R.id.signup_email);
        password=findViewById(R.id.signup_password);
        confirm_password=findViewById(R.id.signup_confirm_password);
        phone=findViewById(R.id.signup_phone);
        seller_type=findViewById(R.id.sign_seller_type);
        payment_method=findViewById(R.id.payment_method);
        homemade_option=findViewById(R.id.homemade_option);
        restaurant_option=findViewById(R.id.restaurant_option);
        checkbox_homemade=findViewById(R.id.checkbox_Homemade);
        checkbox_restaurant=findViewById(R.id.checkbox_Restaurant);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


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

        gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="male";
                checkBox_male.setImageResource(R.drawable.ic_check_box);
                checkBox_female.setImageResource(R.drawable.checkbox);

            }
        });
        gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="female";
                checkBox_male.setImageResource(R.drawable.checkbox);
                checkBox_female.setImageResource(R.drawable.ic_check_box);
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().equals("")||email.getText().toString().trim().equals("")||
                        password.getText().toString().equals("")|| phone.getText().toString().equals("")||gender.equals("")||type.equals("") )
                    Toast.makeText(SignUpActivity.this, "Please Fill all the details", Toast.LENGTH_SHORT).show();
                else if(!name.getText().toString().trim().matches("[a-zA-Z ]+"))
                    Toast.makeText(SignUpActivity.this, "Please enter valid user name", Toast.LENGTH_SHORT).show();
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
                    Toast.makeText(SignUpActivity.this, "please enter valid email address", Toast.LENGTH_SHORT).show();
                else if (password.getText().toString().trim().length() < 6)
                    Toast.makeText(SignUpActivity.this, "password must contain atleast 6 character", Toast.LENGTH_SHORT).show();
                else if (!password.getText().toString().trim().equals(confirm_password.getText().toString().trim()))
                    Toast.makeText(SignUpActivity.this, "please confirm your password again", Toast.LENGTH_SHORT).show();
                else if (!isValidMobile())
                    Toast.makeText(SignUpActivity.this, "please enter valid phone number", Toast.LENGTH_SHORT).show();
                else signUp();
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
    }
    private boolean isValidMobile()
    {
        if(phone.getText().toString().trim().length() < 6 || phone.getText().toString().trim().length() > 13)
        {
            return false;

        }

        return true;
    }

    private void signUp() {
        mProgressBar.setVisibility(View.VISIBLE);
        String url = "http://www.businessmarkaz.com/test/ucookipayws/user/sign_up";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            mProgressBar.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            JSONObject data=obj.getJSONObject("data");
                            String user_id=data.getString("user_id");
                            id=user_id;
                            if(message.equalsIgnoreCase("signup successfull"))
                            {
                                Intent intent=new Intent(getApplicationContext(),ThankYouPopUpActivity.class);
                                intent.putExtra("Email",email.getText().toString());
                                startActivity(intent);
                                name.setText("");
                                email.setText("");
                                password.setText("");
                                confirm_password.setText("");
                                phone.setText("");
                                gender="";
                                type="";
                                sellerType="";
                                checkBox_seller.setImageResource(R.drawable.ic_check_box);
                                checkBox_consumer.setImageResource(R.drawable.ic_check_box);

                            }

                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("phone", phone.getText().toString());
                params.put("gender", gender);
                params.put("type", type);
                params.put("payment_method", "");
                if (!sellerType.equals("")){
                    params.put("seller_type", sellerType);

                }

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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


}
