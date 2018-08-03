package com.example.h2_12.youcookipay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThankYouPopUpActivity extends AppCompatActivity {
    TextView resend_email,change_email_address;
    ImageView thanks_btn;
    LinearLayout layout1,layout2;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_pop_up);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9),(int)(height*.5));
        resend_email=findViewById(R.id.resend_email);
        change_email_address=findViewById(R.id.change_your_email_address);
        thanks_btn=findViewById(R.id.thankyou_btn);


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
                finish();
               Intent intent=new Intent(getApplicationContext(),ResendEmailPopUp.class);
               startActivity(intent);
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

        thanks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(getApplicationContext(),LoginInActivity.class);
                startActivity(intent);
            }
        });
    }
}
