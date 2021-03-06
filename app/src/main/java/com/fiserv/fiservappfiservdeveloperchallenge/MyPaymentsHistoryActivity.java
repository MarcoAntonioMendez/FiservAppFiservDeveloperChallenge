package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * The MyPaymentsHistoryActivity class will display user's history when it comes to banking
 * movements with his/her payment cards.
 *
 * @author  MarcoAntonioMéndez
 * @version 1.0
 * @since   2020-10-26
 */

public class MyPaymentsHistoryActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payments_history);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));
    }

}
