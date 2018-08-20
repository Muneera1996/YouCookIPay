package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewsAndRatingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu;
    ImageView image,filter,submit,quality_star1,quality_star2,quality_star3,quality_star4,quality_star5;
    ImageView taste_star1,taste_star2,taste_star3,taste_star4,taste_star5;
    ImageView star1,star2,star3,star4,star5;
    EditText comment_box;
    String quality,taste;
    TextView name,address,type,rating;
    ArrayList<Chef_Profile> arrayList;
    ArrayList<User> arrayList1;
    private double rate = 0;
    ProgressBar mProgressBar;
    View rating_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_and_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrayList=OrderScreeen2Activity.profile;
        arrayList1=LoginInActivity.users;
        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        submit=findViewById(R.id.ReviewAndRating_submit);
        comment_box=findViewById(R.id.ReviewAndRating_comment_box);
        image=findViewById(R.id.reviews_and_rating_image);
        name=findViewById(R.id.reviews_and_rating_name);
        address=findViewById(R.id.reviews_and_rating_address);
        type=findViewById(R.id.reviews_and_rating_type);
        rating=findViewById(R.id.reviews_and_rating_rating);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        rating_view=findViewById(R.id.ReviewsRating_ratingSection);
        star1=findViewById(R.id.star_one);
        star2=findViewById(R.id.star_two);
        star3=findViewById(R.id.star_three);
        star4=findViewById(R.id.star_four);
        star5=findViewById(R.id.star_five);

        name.setText(arrayList.get(0).getName());
        address.setText(arrayList.get(0).getAddress());
        type.setText(arrayList.get(0).getType());
        Glide.with(image.getContext()).load(arrayList.get(0).getImage()).into(image);
        rating.setText(arrayList.get(0).getRating());
        rate = Double.parseDouble(arrayList.get(0).getRating());
        if(rate>=1&&rate<2) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
        }
        else if(rate>=2&&rate<3) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.unfill_star).into(star3);
            Glide.with(star4.getContext()).load(R.drawable.unfill_star).into(star4);
            Glide.with(star5.getContext()).load(R.drawable.unfill_star).into(star5);
        }
        else if(rate>=3&&rate<4) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
            Glide.with(star4.getContext()).load(R.drawable.unfill_star).into(star4);
            Glide.with(star5.getContext()).load(R.drawable.unfill_star).into(star5);
        }
        else if(rate>=4&&rate<5) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
            Glide.with(star4.getContext()).load(R.drawable.fill_star).into(star4);
            Glide.with(star5.getContext()).load(R.drawable.unfill_star).into(star5);
        }
        else if(rate>=5) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
            Glide.with(star4.getContext()).load(R.drawable.fill_star).into(star4);
            Glide.with(star5.getContext()).load(R.drawable.fill_star).into(star5);
        }
        quality_star1=findViewById(R.id.star_one1);
        quality_star2=findViewById(R.id.star_two1);
        quality_star3=findViewById(R.id.star_three1);
        quality_star4=findViewById(R.id.star_four1);
        quality_star5=findViewById(R.id.star_five1);

        taste_star1=findViewById(R.id.star_one2);
        taste_star2=findViewById(R.id.star_two2);
        taste_star3=findViewById(R.id.star_three2);
        taste_star4=findViewById(R.id.star_four2);
        taste_star5=findViewById(R.id.star_five2);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/review_seller";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                // JSONObject parser = new JSONObject();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    mProgressBar.setVisibility(View.GONE);
                                    String message=obj.getString("message");
                                    Toast.makeText(ReviewsAndRatingActivity.this, message, Toast.LENGTH_SHORT).show();
                                    if (message.equalsIgnoreCase("review posted successfully")) {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
                                // error
                                Log.d("Error.Response", error.toString());
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id",arrayList1.get(0).getUser_id());
                        params.put("session_id",arrayList1.get(0).getSession_id());
                        params.put("seller_id",arrayList.get(0).getId());
                        Log.i("seller_id",arrayList.get(0).getId());
                        params.put("quality",quality);
                        params.put("taste",taste);
                        params.put("text",comment_box.getText().toString());
                        return params;
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        //TODO if you want to use the status code for any other purpose like to handle 401, 403, 404
                        String statusCode = String.valueOf(response.statusCode);
                        //Handling logic
                        return super.parseNetworkResponse(response);
                    }

                };
                VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(postRequest);
                postRequest.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 20000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 20000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });




            }
        });

        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/review_seller?user_id="+arrayList1.get(0).getUser_id()+"&session_id="+arrayList1.get(0).getSession_id()+"&seller_id="+ProfileViewChefActivity.iid+"&quality="+quality+"&taste="+taste+"&text="+comment_box.getText().toString();
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject obj = new JSONObject(response.toString());
                                    String message=obj.getString("message");
                                    Toast.makeText(ReviewsAndRatingActivity.this, message, Toast.LENGTH_SHORT).show();


                                } catch (Throwable t) {
                                    Log.e("Reviews and Rating", "Could not parse malformed JSON: \"" + response + "\"");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());

                            }
                        });

                queue.add(getRequest);
            }
        });*/

        quality_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality="1";
                quality_star1.setImageResource(R.drawable.fill_star);
                quality_star2.setImageResource(R.drawable.unfill_star);
                quality_star3.setImageResource(R.drawable.unfill_star);
                quality_star4.setImageResource(R.drawable.unfill_star);
                quality_star5.setImageResource(R.drawable.unfill_star);
            }
        });
        quality_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality="2";
                quality_star1.setImageResource(R.drawable.fill_star);
                quality_star2.setImageResource(R.drawable.fill_star);
                quality_star3.setImageResource(R.drawable.unfill_star);
                quality_star4.setImageResource(R.drawable.unfill_star);
                quality_star5.setImageResource(R.drawable.unfill_star);
            }
        });
        quality_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality="3";
                quality_star1.setImageResource(R.drawable.fill_star);
                quality_star2.setImageResource(R.drawable.fill_star);
                quality_star3.setImageResource(R.drawable.fill_star);
                quality_star4.setImageResource(R.drawable.unfill_star);
                quality_star5.setImageResource(R.drawable.unfill_star);
            }
        });
        quality_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality="4";
                quality_star1.setImageResource(R.drawable.fill_star);
                quality_star2.setImageResource(R.drawable.fill_star);
                quality_star3.setImageResource(R.drawable.fill_star);
                quality_star4.setImageResource(R.drawable.fill_star);
                quality_star5.setImageResource(R.drawable.unfill_star);
            }
        });
        quality_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quality="5";
                quality_star1.setImageResource(R.drawable.fill_star);
                quality_star2.setImageResource(R.drawable.fill_star);
                quality_star3.setImageResource(R.drawable.fill_star);
                quality_star4.setImageResource(R.drawable.fill_star);
                quality_star5.setImageResource(R.drawable.fill_star);
            }
        });


        taste_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taste="1";
                taste_star1.setImageResource(R.drawable.fill_star);
                taste_star2.setImageResource(R.drawable.unfill_star);
                taste_star3.setImageResource(R.drawable.unfill_star);
                taste_star4.setImageResource(R.drawable.unfill_star);
                taste_star5.setImageResource(R.drawable.unfill_star);
            }
        });
        taste_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taste="2";
                taste_star1.setImageResource(R.drawable.fill_star);
                taste_star2.setImageResource(R.drawable.fill_star);
                taste_star3.setImageResource(R.drawable.unfill_star);
                taste_star4.setImageResource(R.drawable.unfill_star);
                taste_star5.setImageResource(R.drawable.unfill_star);
            }
        });
        taste_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taste="3";
                taste_star1.setImageResource(R.drawable.fill_star);
                taste_star2.setImageResource(R.drawable.fill_star);
                taste_star3.setImageResource(R.drawable.fill_star);
                taste_star4.setImageResource(R.drawable.unfill_star);
                taste_star5.setImageResource(R.drawable.unfill_star);
            }
        });
        taste_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taste="4";
                taste_star1.setImageResource(R.drawable.fill_star);
                taste_star2.setImageResource(R.drawable.fill_star);
                taste_star3.setImageResource(R.drawable.fill_star);
                taste_star4.setImageResource(R.drawable.fill_star);
                taste_star5.setImageResource(R.drawable.unfill_star);
            }
        });
        taste_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taste="5";
                taste_star1.setImageResource(R.drawable.fill_star);
                taste_star2.setImageResource(R.drawable.fill_star);
                taste_star3.setImageResource(R.drawable.fill_star);
                taste_star4.setImageResource(R.drawable.fill_star);
                taste_star5.setImageResource(R.drawable.fill_star);
            }
        });
        rating_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeeReviewAndRatingActivity.class);
                intent.putExtra("ChefId", arrayList.get(0).getId());
                startActivity(intent);
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
}
