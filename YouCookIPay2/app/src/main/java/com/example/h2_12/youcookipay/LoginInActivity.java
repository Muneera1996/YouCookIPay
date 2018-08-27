package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.steelkiwi.instagramhelper.InstagramHelper;
import com.steelkiwi.instagramhelper.InstagramHelperConstants;
import com.steelkiwi.instagramhelper.model.InstagramUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.*;

public class LoginInActivity extends AppCompatActivity {
    Button signUp;
    EditText email, password;
    TextView forget_password;
    public  static ArrayList<User> users;
    ImageView fb_btn,insta_btn;
    CallbackManager callbackManager;
    ProgressBar mProgressBar;
    LoginButton loginButton;
    String id,name,mail;
    InstagramHelper instagramHelper;
    View signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login_in);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.register_btn);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        forget_password = findViewById(R.id.forget_password);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        callbackManager = CallbackManager.Factory.create();
        fb_btn = findViewById(R.id.fb_btn);
        loginButton = findViewById(R.id.login_button);
        insta_btn=findViewById(R.id.insta_btn);
        String scope = "basic+public_content+follower_list+comments+relationships+likes";
        instagramHelper = new InstagramHelper.Builder()
                .withClientId("8bfd4a26802a422ab2df08d6301da09e")
                .withRedirectUrl("http://incisivesoft.com/")
                .withScope(scope)
                .build();

        insta_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // signInWithInstagram();
               // checkForInstagramData();
                instagramHelper.loginFromActivity(LoginInActivity.this);
            }
        });

        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback < LoginResult > () {@Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    final String accessToken = loginResult.getAccessToken()
                            .getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {@Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {

                                Log.i("LoginActivity",
                                        response.toString());
                                try {
                                    id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL(
                                                "http://graph.facebook.com/" + id + "/picture?type=large");
                                        Log.i("profile_pic",
                                                profile_pic + "");

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    name = object.getString("name");
                                    mail = object.getString("email");
                                    socialLogin("facebook",accessToken);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });



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
                    if (!isConnected()) {
                        Toast.makeText(LoginInActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                    } else {

                        if (email.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")) {
                            Toast.makeText(LoginInActivity.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                        } else {
                            mProgressBar.setVisibility(View.VISIBLE);
                            signIn();
                        }
                    }
                }
            });
            forget_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (email.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(LoginInActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                        } else {
                          forgetPassword();
                    }
                }
            });
        }
    }
    private void signIn(){
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
                            boolean status = obj.getBoolean("status");
                            Toast.makeText(LoginInActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (status) {
                                JSONObject data = obj.getJSONObject("data");
                                String user_id = data.getString("user_id");
                                String session_id = data.getString("session_id");
                                String type = data.getString("type");
                                users = new ArrayList<>();
                                users.add(new User(user_id, session_id, type));
                                if (message.equalsIgnoreCase("Sign In successfull")) {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }
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
    private void socialLogin(String social_type,String social_id) {
       Intent intent = new Intent(getApplicationContext(),SocialViewPopUpActivity.class);
       intent.putExtra("Social_Type",social_type);
       intent.putExtra("Social_Id",social_id);
       startActivity(intent);
    }
    private void forgetPassword(){
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
                            Log.e("Login", "Could not parse malformed JSON: \"" + response + "\"");
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


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == InstagramHelperConstants.INSTA_LOGIN && resultCode == RESULT_OK) {
            InstagramUser user = instagramHelper.getInstagramUser(this);
            Log.i("Insta Data",user.getData().getId());
            socialLogin("instagram",user.getData().getId());
        }
    }
    public void onClick(View v) {
        if (v == fb_btn) {
            loginButton.performClick();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),LoginInActivity.class);
        startActivity(intent);
    }
}
