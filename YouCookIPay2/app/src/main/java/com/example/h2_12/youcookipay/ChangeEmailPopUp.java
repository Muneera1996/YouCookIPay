package com.example.h2_12.youcookipay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeEmailPopUp extends AppCompatActivity {

    EditText email;
    ImageView verify;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_pop_up);

        id=SignUpActivity.id;
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;



        getWindow().setLayout((int) (width*.9),(int)(height*.3));
        email=findViewById(R.id.change_your_email_address);
        verify=findViewById(R.id.email_verifier_btn);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://www.businessmarkaz.com/test/ucookipayws/user/change_email?user_id="+id+"&email_id="+email.getText().toString();

                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d("Response", response.toString());
                                try {
                                    JSONObject obj = new JSONObject(response.toString());
                                    Boolean status=obj.getBoolean("status");
                                    String message=obj.getString("message");
                                    Toast.makeText(ChangeEmailPopUp.this, message, Toast.LENGTH_SHORT).show();
                                    if(status){
                                        Intent intent = new Intent(getApplicationContext(),LoginInActivity.class);
                                        startActivity(intent);
                                    }

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

                VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(getRequest);
            }
        });


    }
}
