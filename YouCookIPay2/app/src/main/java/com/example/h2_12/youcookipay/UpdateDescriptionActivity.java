package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
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

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateDescriptionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu;
    ImageView filter,update_description,image;
    EditText description,name,address;
    ArrayList<User> users;
    private final int IMG_REQUEST=1;
    private Bitmap bitmap;
    ArrayList<Chef_Profile> myProfile;
    ProgressBar mProgressBar;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        description=findViewById(R.id.updateDescription_info);
        home_menu=findViewById(R.id.home_menu);
        name=findViewById(R.id.updateDescription_name);
        address=findViewById(R.id.updateDescription_address);
        image=findViewById(R.id.updateDescription_upload_image);
        filter=findViewById(R.id.filter);
        update_description=findViewById(R.id.updateDescription_update);
        users=LoginInActivity.users;
        myProfile=ProfileActivity.myProfile;
        name.setText(myProfile.get(0).getName());
        address.setText(myProfile.get(0).getAddress());
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        layout=findViewById(R.id.layout_updateDescription);
        description.setText(myProfile.get(0).getDescription());
        Glide.with(image.getContext()).load(myProfile.get(0).getImage()).into(image);
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
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });
        update_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                update_description.setEnabled(false);
                updateDescription();
            }
        });
    }

    private void updateDescription() {
        // loading or check internet connection or something...
        // ... then
        String url = "http://www.businessmarkaz.com/test/ucookipayws/user/update_profile_seller";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    mProgressBar.setVisibility(View.GONE);
                    update_description.setEnabled(true);
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    if(message.equalsIgnoreCase("profile updated successfully"))
                    {
                        Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Log.i("Unexpected", message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",users.get(0).getUser_id());
                params.put("session_id",users.get(0).getSession_id());
                params.put("user_name",name.getText().toString());
                params.put("user_address",address.getText().toString());
                params.put("user_description",description.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("user_image", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), image.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
        multipartRequest.setRetryPolicy(new RetryPolicy() {
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
            intent.putExtra("ChefId",HomeActivity.arrayList.get(0).getUser_id());
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST&&resultCode==RESULT_OK&&data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
