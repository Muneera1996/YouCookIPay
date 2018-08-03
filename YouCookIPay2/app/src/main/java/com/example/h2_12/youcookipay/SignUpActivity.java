package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    View signup_header,payment_method,gender_male,gender_female,seller_option,consumer_option,seller_type,homemade_option,restaurant_option;
    ImageView checkBox_male,checkBox_female,signup_btn,checkBox_seller,checkBox_consumer,checkbox_homemade,checkbox_restaurant;
    EditText name,email,password,confirm_password,phone,paymentMethod;
    String gender="",type="",sellerType="";
    public static String id;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final RequestQueue queue=AppController.getInstance().getRequestQueue();

        if(isConnected()){

        }
        else{
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
        paymentMethod=findViewById(R.id.signup_payment);
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
        payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PaymentMethodPopUp.class);
                startActivity(intent);
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
                int num=validate();
                if(num==1)
                    Toast.makeText(getBaseContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                    // call AsynTask to perform network operation on separate thread
                else if(num==2)
                    Toast.makeText(getBaseContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                else if (num==3)
                    Toast.makeText(getBaseContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                else if (num==4)
                    Toast.makeText(getBaseContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                else if (num==5)
                    Toast.makeText(getBaseContext(), "Rewrite your password", Toast.LENGTH_LONG).show();
                else if (num==6)
                    Toast.makeText(getBaseContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                else if (num==7)
                    Toast.makeText(getBaseContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                else if (num==8)
                    Toast.makeText(getBaseContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                else if (num==9)
                    Toast.makeText(getBaseContext(), "Fill all the details!", Toast.LENGTH_LONG).show();
                else if(num==0)
                {
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
                                        JSONObject data=obj.getJSONObject("data");
                                        String user_id=data.getString("user_id");
                                        id=user_id;

                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                        if(message.equalsIgnoreCase("signup successfull"))
                                        {
                                            Intent intent=new Intent(getApplicationContext(),ThankYouPopUpActivity.class);
                                            startActivity(intent);
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
                            params.put("payment_method", paymentMethod.getText().toString());
                            if (!sellerType.equals("")){
                                params.put("seller_type", sellerType);

                            }

                            return params;
                        }
                    };
                    queue.add(postRequest);
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
                type="consumer";
                sellerType="";
                checkBox_seller.setImageResource(R.drawable.checkbox);
                checkBox_consumer.setImageResource(R.drawable.ic_check_box);
                seller_type.setVisibility(View.GONE);
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

    private int validate(){
        if(name.getText().toString().trim().equals(""))
            return 1;
        if(email.getText().toString().trim().equals(""))
            return 2;
        if(password.getText().toString().equals(""))
            return 3;
        if(confirm_password.getText().toString().equals(""))
            return 4;
        if(!password.getText().toString().equals(confirm_password.getText().toString()))
            return 5;
        if(phone.getText().toString().equals(""))
            return 6;
        if (paymentMethod.getText().toString().equals(""))
            return 7;
        if(gender.equals(""))
            return 8;
        if(type.equals(""))
            return 9;
        return  0;
    }


}
