package com.example.h2_12.youcookipay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrderScreen1Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView orderScreen;
    View home_menu,quant,plus_quanity,minus_quantity;
    ImageView filter,image,star1,star2,star3,star4,star5;
    int quantity;
    TextView txt_quanity;
    LinearLayout layout1,layout2;
    TextView chef_name,chef_address,chef_type,chef_rating;
    ArrayList<Chef_Profile> profile;
    private double rate=0;
    ProgressBar mProgressBar;
    View rating_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile=ProfileViewChefActivity.chef_profile;
        home_menu=findViewById(R.id.home_menu);
        filter=findViewById(R.id.filter);
        quant=findViewById(R.id.select_quality);
        plus_quanity=findViewById(R.id.plus_btn);
        minus_quantity=findViewById(R.id.minus_btn);
        orderScreen=findViewById(R.id.orderScreen_btn);
        txt_quanity=findViewById(R.id.quantity);
        chef_name=findViewById(R.id.orderScreen1_chef_name);
        chef_address=findViewById(R.id.orderScreen1_chef_place);
        chef_rating=findViewById(R.id.orderScreen1_rating);
        chef_type=findViewById(R.id.orderScreen1_seller_type);
        rating_view=findViewById(R.id.orderScreen2_ratingSection);
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
        orderScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PaymentMethodPopUpActivity.class);
                startActivity(intent);
            }
        });
        plus_quanity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              quantity++;
              txt_quanity.setText(Integer.toString(quantity));
            }
        });
        minus_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity>1) {
                    quantity--;
                }
                txt_quanity.setText(Integer.toString(quantity));
            }
        });
        rating_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SeeReviewAndRatingActivity.class);
                intent.putExtra("ChefId", ProfileViewChefActivity.iid);
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
}
