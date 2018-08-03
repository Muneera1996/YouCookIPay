package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginInActivity extends AppCompatActivity {
    Button signIn;
    Button signUp;
    EditText email, password;
    TextView forget_password;
    public  static ArrayList<User> users;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);
        final RequestQueue queue=AppController.getInstance().getRequestQueue();
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.register_btn);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        forget_password = findViewById(R.id.forget_password);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (!isConnected()) {
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        else {
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                }
            });
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    if (email.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")) {
                        Toast.makeText(LoginInActivity.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                    } else {
                        String url = "http://www.businessmarkaz.com/test/ucookipayws/user/sign_in";
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
                                            JSONObject data = obj.getJSONObject("data");
                                            String user_id = data.getString("user_id");
                                            String session_id = data.getString("session_id");
                                            users = new ArrayList<>();
                                            users.add(new User(user_id, session_id));
                                            Toast.makeText(LoginInActivity.this, message, Toast.LENGTH_SHORT).show();
                                            if (message.equalsIgnoreCase("Sign In successfull")) {
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
                                        Log.d("Error.Response", error.toString());
                                        mProgressBar.setVisibility(View.GONE);

                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("email", email.getText().toString());
                                params.put("password", password.getText().toString());
                                params.put("device_type", "android");
                                params.put("device_token", "token");
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
            forget_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (email.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(LoginInActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();

                    } else {
                        final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/forget_password?email=" + email.getText().toString();
                        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // display response
                                        Log.d("Response", response.toString());
                                        GsonBuilder gsonBuilder = new GsonBuilder();
                                        Gson gson = gsonBuilder.create();
                                        ForgetPassword forgetPasswords = gson.fromJson(response.toString(), ForgetPassword.class);
                                        boolean success = forgetPasswords.getStatus();
                                        String message = forgetPasswords.getMessage();

                                        try {

                                            if (!success) {
                                                Toast.makeText(LoginInActivity.this, message, Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (Throwable t) {
                                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Error.Response", error.getMessage());
                                    }
                                }

                        );
                        queue.add(getRequest);
                    }
                }
            });
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
