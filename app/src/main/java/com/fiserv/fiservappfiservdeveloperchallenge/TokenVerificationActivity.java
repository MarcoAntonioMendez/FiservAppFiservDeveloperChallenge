package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TokenVerificationActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    private String clientRequestId,apiTraceId,orderId,paymentTokenLast4Digits,paymentTokenBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_verification);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        // Getting extras
        clientRequestId = getIntent().getExtras().getString(AppGlobalConstants.CLIENT_REQUEST_ID_CONSTANT);
        apiTraceId = getIntent().getExtras().getString(AppGlobalConstants.API_TRACE_ID_CONSTANT);
        orderId = getIntent().getExtras().getString(AppGlobalConstants.ORDER_ID_CONSTANT);
        paymentTokenLast4Digits = getIntent().getExtras().getString(AppGlobalConstants.PAYMENT_TOKEN_INDEX_FOR_LAST_4_DATA_CONSTANT);
        paymentTokenBrand = getIntent().getExtras().getString(AppGlobalConstants.PAYMENT_TOKEN_INDEX_FOR_BRAND_DATA_CONSTANT);
    }

}
