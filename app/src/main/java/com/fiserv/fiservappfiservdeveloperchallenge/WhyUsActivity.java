package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class WhyUsActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String FISERV_OFFICIAL_WEBSITE_URL = "https://www.firstdata.com/es_mx/home.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why_us);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));
    }

    /**
     * When user touhes the icon of the phone_operator_icon the help Activity will start.
     * @param view - phone_operator_icon
     */
    public void startHelpActivityFromWhyUsActivity(View view){
        Intent intent = new Intent(this,HelpActivity.class);
        startActivity(intent);
    }

    /**
     * When user touches any icon (), the app will take him/her to the Fiserv official website.
     * @param view All icons except for the phone_operator_icon
     */
    public void openFiservWebPageInBrowser(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FISERV_OFFICIAL_WEBSITE_URL));
        startActivity(browserIntent);
    }

}
