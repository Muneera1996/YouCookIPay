package com.example.h2_12.youcookipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.example.h2_12.youcookipay.OrderScreeen2Activity.Token;

public class
OrderScreen1Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button orderScreen;
    View home_menu,quant,plus_quanity,minus_quantity,dish_image_view;
    ImageView image,star1,star2,star3,star4,star5;
    int quantity=1;
    TextView txt_quanity,meal_name,meal_price;
    TextView total_amount;
    TextView chef_name,chef_address,chef_type,chef_rating,dish_name,dish_price;
    ArrayList<Chef_Profile> profile;
    private double rate=0;
    ProgressBar mProgressBar;
    View rating_view,delivery_view;
    public static int REQUEST_CODE = 1;
    public static String nonce_str = "";
    public static String total_bill = "";
    boolean check=false;
    boolean back_check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile=OrderScreeen2Activity.profile;
        home_menu=findViewById(R.id.home_menu);
        quant=findViewById(R.id.select_quality);
        plus_quanity=findViewById(R.id.plus_btn);
        minus_quantity=findViewById(R.id.minus_btn);
        orderScreen=findViewById(R.id.orderScreen_btn);
        delivery_view=findViewById(R.id.delivery_view);
        txt_quanity=findViewById(R.id.quantity);
        chef_name=findViewById(R.id.orderScreen1_chef_name);
        chef_address=findViewById(R.id.orderScreen1_chef_place);
        chef_rating=findViewById(R.id.orderScreen1_rating);
        chef_type=findViewById(R.id.orderScreen1_seller_type);
        rating_view=findViewById(R.id.orderScreen2_ratingSection);
        dish_image_view=findViewById(R.id.orderScreen1_dish_pic);
        dish_name=findViewById(R.id.orderScreen1_dish_name);
        dish_price=findViewById(R.id.orderScreen1_dish_price);
        meal_name=findViewById(R.id.meal_name);
        meal_price=findViewById(R.id.meal_price);
        total_amount=findViewById(R.id.total_amount);

        image=findViewById(R.id.orderScreen1_image);
        star1=findViewById(R.id.orderScreen1_star_one);
        star2=findViewById(R.id.orderScreen1_star_two);
        star3=findViewById(R.id.orderScreen1_star_three);
        star4=findViewById(R.id.orderScreen1_star_four);
        star5=findViewById(R.id.orderScreen1_star_five);
        chef_name.setText(profile.get(0).getName());
        chef_address.setText(profile.get(0).getAddress());
        chef_type.setText(profile.get(0).getType());
        chef_rating.setText(profile.get(0).getRating());
        dish_name.setText(OrderScreeen2Activity.orderScreens.get(0).getMeal_name());
        dish_price.setText(OrderScreeen2Activity.orderScreens.get(0).getMeal_price());
        meal_name.setText(OrderScreeen2Activity.orderScreens.get(0).getMeal_name());
        meal_price.setText(OrderScreeen2Activity.orderScreens.get(0).getMeal_price());
        if(OrderScreeen2Activity.orderScreens.get(0).getDelivery_method().equalsIgnoreCase("home_delivery")){
            delivery_view.setVisibility(View.VISIBLE);
            check=true;
            total_bill = Double.toString((quantity * Double.parseDouble(OrderScreeen2Activity.orderScreens.get(0).getMeal_price())) + 2.00);

        }
        else{
            delivery_view.setVisibility(View.GONE);
            check=false;
            total_bill = Double.toString(quantity * Double.parseDouble(OrderScreeen2Activity.orderScreens.get(0).getMeal_price()));

        }
        total_amount.setText(total_bill);


        Glide.with(dish_image_view.getContext())
                .asBitmap()
                .load(OrderScreeen2Activity.orderScreens.get(0).getMeal_image())
                .into(new SimpleTarget<Bitmap>(120,170) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable dr = new BitmapDrawable(resource);
                        dish_image_view.setBackgroundDrawable(dr);
                    }
                 });
        if(!chef_rating.getText().toString().trim().isEmpty()){
            rate = Double.parseDouble(chef_rating.getText().toString());
        }
        if(rate>=1&&rate<2) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
        }
        else if(rate>=2&&rate<3) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
        }
        else if(rate>=3&&rate<4) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
        }
        else if(rate>=4&&rate<5) {
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
            Glide.with(star4.getContext()).load(R.drawable.fill_star).into(star4);
        }
        else if(rate>=5){
            Glide.with(star1.getContext()).load(R.drawable.fill_star).into(star1);
            Glide.with(star2.getContext()).load(R.drawable.fill_star).into(star2);
            Glide.with(star3.getContext()).load(R.drawable.fill_star).into(star3);
            Glide.with(star4.getContext()).load(R.drawable.fill_star).into(star4);
            Glide.with(star5.getContext()).load(R.drawable.fill_star).into(star5);
        }
        Glide.with(image.getContext()).load(profile.get(0).getImage()).into(image);
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

        orderScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           if (isConnected()){
              onBraintreeSubmit();
           }
           else {
               Toast.makeText(OrderScreen1Activity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
           }

            }
        });
        plus_quanity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              quantity++;
              txt_quanity.setText(Integer.toString(quantity));
              if(check)
                  total_bill = Double.toString((quantity * Double.parseDouble(OrderScreeen2Activity.orderScreens.get(0).getMeal_price())) + 2.00);
              else
                  total_bill = Double.toString(quantity * Double.parseDouble(OrderScreeen2Activity.orderScreens.get(0).getMeal_price()));

              total_amount.setText(total_bill);
            }
        });
        minus_quantity.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(quantity>1) {
                    quantity--;
                }
                txt_quanity.setText(Integer.toString(quantity));
                if(check)
                    total_bill=Double.toString((quantity*Double.parseDouble(OrderScreeen2Activity.orderScreens.get(0).getMeal_price()))+2.00);
                else
                    total_bill=Double.toString(quantity*Double.parseDouble(OrderScreeen2Activity.orderScreens.get(0).getMeal_price()));
                     total_amount.setText(total_bill);
            }
        });
        rating_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SeeReviewAndRatingActivity.class);
                intent.putExtra("ChefId", profile.get(0).getId());
                startActivity(intent);
            }
        });
    }
    private void placeOrder() {
       // mProgressBar.setVisibility(View.VISIBLE);
        String url = "http://www.businessmarkaz.com/test/ucookipayws/meal_ads/place_order";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (response != null && response.length() > 0) {

                            Log.e("Response", response);
                            try {
                                // mProgressBar.setVisibility(View.GONE);
                                JSONObject obj = new JSONObject(response);
                                String message = obj.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                if (message.equalsIgnoreCase("payment proccessed successfully")) {
                                    back_check=true;
                                    Intent intent = new Intent(getApplicationContext(), PaymentMethodPopUpActivity.class);
                                    startActivity(intent);
                                }

                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                        if(error!=null && error.getMessage() !=null){
                            Toast.makeText(getApplicationContext(),"error VOLLEY "+error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                            }
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", LoginInActivity.users.get(0).getUser_id());
                Log.e("user",LoginInActivity.users.get(0).getUser_id());

                params.put("session_id", LoginInActivity.users.get(0).getSession_id());
                Log.e("session id",LoginInActivity.users.get(0).getSession_id());

                params.put("name", OrderScreeen2Activity.orderScreens.get(0).getName());
                Log.e("name",OrderScreeen2Activity.orderScreens.get(0).getName());

                params.put("email", OrderScreeen2Activity.orderScreens.get(0).getEmail());
                Log.e("email",OrderScreeen2Activity.orderScreens.get(0).getEmail());

                params.put("contact", OrderScreeen2Activity.orderScreens.get(0).getContact());
                Log.e("contact",OrderScreeen2Activity.orderScreens.get(0).getContact());

                params.put("date", OrderScreeen2Activity.orderScreens.get(0).getDate());
                Log.e("date",OrderScreeen2Activity.orderScreens.get(0).getDate());

                params.put("time", OrderScreeen2Activity.orderScreens.get(0).getTime());
                Log.e("time",OrderScreeen2Activity.orderScreens.get(0).getTime());

                params.put("delivery_method", OrderScreeen2Activity.orderScreens.get(0).getDelivery_method());
                Log.e("delivery method",OrderScreeen2Activity.orderScreens.get(0).getDelivery_method());

                params.put("meal_id", OrderScreeen2Activity.orderScreens.get(0).getMeal_id());
                Log.e("meal id",OrderScreeen2Activity.orderScreens.get(0).getMeal_id());

                params.put("quantity", txt_quanity.getText().toString());
                Log.e("quantity",txt_quanity.getText().toString());
                params.put("payment_nonce",nonce_str);
                Log.e("payment nonce",nonce_str);

                if(OrderScreeen2Activity.orderScreens.get(0).getDelivery_method().equalsIgnoreCase("home_delivery")) {
                    params.put("street", OrderScreeen2Activity.orderScreens.get(0).getStreet());
                    Log.e("street", OrderScreeen2Activity.orderScreens.get(0).getStreet());

                    params.put("area", OrderScreeen2Activity.orderScreens.get(0).getArea());
                    Log.e("area", OrderScreeen2Activity.orderScreens.get(0).getArea());

                    params.put("city", OrderScreeen2Activity.orderScreens.get(0).getCity());
                    Log.e("city", OrderScreeen2Activity.orderScreens.get(0).getCity());
                }

                Log.e("params", "params checked");
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
            if(!back_check)
            super.onBackPressed();
            else
            {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
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
    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(OrderScreeen2Activity.Token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
                PaymentMethodNonce nonce=result.getPaymentMethodNonce();
                assert nonce != null;
                nonce_str=nonce.getNonce();
                Log.e("nonce",nonce_str);
                placeOrder();
                //Nonce=result.toString();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Toast.makeText(this, "Payment is cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
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
