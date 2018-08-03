package com.example.h2_12.youcookipay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ResendEmailPopUp extends AppCompatActivity {

    TextView resend_email,change_email_address;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend_email_pop_up);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        id=SignUpActivity.id;
        final RequestQueue queue=AppController.getInstance().getRequestQueue();

        getWindow().setLayout((int) (width*.9),(int)(height*.5));
        resend_email=findViewById(R.id.resend_email);
        change_email_address=findViewById(R.id.change_your_email_address);


        String mystring=new String("Resend Email");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        resend_email.setText(content);


        String mystring1=new String("Change Your Email Address");
        SpannableString content1 = new SpannableString(mystring1);
        content1.setSpan(new UnderlineSpan(), 0, mystring1.length(), 0);
        change_email_address.setText(content1);

        resend_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/resend_email?user_id="+id;

                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d("Response", response.toString());
                                try {
                                    JSONObject obj = new JSONObject(response.toString());
                                    String message=obj.getString("message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        }
                );

                queue.add(getRequest);

            }
        });
        change_email_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(getApplicationContext(),ChangeEmailPopUp.class);
                startActivity(intent);
            }
        });

    }
}
