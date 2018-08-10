package com.example.h2_12.youcookipay;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class NewOrdersActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View home_menu;
    ImageView filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        home_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilterViewPopUp.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            Intent intent = new Intent(getApplicationContext(), OrderHistory1Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_delivery_address) {
            Intent intent = new Intent(getApplicationContext(), UpdateDeliveryAddressActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_how_use_app) {
            Intent intent = new Intent(getApplicationContext(), HowToUseAppActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_new_orders) {
            Intent intent = new Intent(getApplicationContext(), NewOrdersActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_reviews) {
            Intent intent = new Intent(getApplicationContext(), SeeReviewAndRatingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
