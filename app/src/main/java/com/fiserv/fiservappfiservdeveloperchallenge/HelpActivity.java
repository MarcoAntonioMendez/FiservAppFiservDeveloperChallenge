package com.fiserv.fiservappfiservdeveloperchallenge;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * The HelpActivity class will offer the user options to contact Fiserv's customer service by phone
 * or e-mail.
 *
 * @author  MarcoAntonioMéndez
 * @version 1.0
 * @since   2020-10-26
 */

public class HelpActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String INSIDE_OF_THE_REPUBLIC_PHONE_NUMBER = "tel:8002155733";
    public static final String MEXICO_CITY_PHONE_NUMBER = "tel:3311020660";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));
    }

    /**
     * When user touches the textview containing the phone number from mexico city, a phone call
     * will start.
     * @param view
     */
    public void callFromMexicoCity(View view){
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(HelpActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(MEXICO_CITY_PHONE_NUMBER));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(MEXICO_CITY_PHONE_NUMBER));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * When user touches the textview containing phone call from inside of the republic, a phone call
     * shall start.
     * @param view -
     */
    public void callFromInsideRepublic(View view){
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(HelpActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(INSIDE_OF_THE_REPUBLIC_PHONE_NUMBER));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(INSIDE_OF_THE_REPUBLIC_PHONE_NUMBER));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * When user touches the e-mail icon the gmail app will open allowing the user the send an email
     * regarding his/her doubts.
     * @param view - the e-mail icon
     */
    public void sendHelpEmail(View view){
        String[] TO = {"contacto@firstdata.com"};
        //String[] CC = {"marco.mendez.ing@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Duda sobre los servicios de Fiserv.");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
