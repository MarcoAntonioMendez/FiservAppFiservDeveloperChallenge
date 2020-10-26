package com.fiserv.fiservappfiservdeveloperchallenge;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TokenVerificationActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    private String clientRequestId,apiTraceId,orderId,paymentTokenLast4Digits,paymentTokenBrand;
    private TextView clientRequestIdTextView,apiTraceIdTextView,orderIdTextView;
    private TextView lastFourDigitsTextView,brandTextView;

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

        // Getting graphic elements references from XML files
        clientRequestIdTextView = findViewById(R.id.client_request_id_token_verification);
        apiTraceIdTextView = findViewById(R.id.api_trace_id_token_verification);
        orderIdTextView = findViewById(R.id.order_id_token_verification);
        lastFourDigitsTextView = findViewById(R.id.last_four_digits_token_verification);
        brandTextView = findViewById(R.id.brand_token_verification);

        // Setting complete text fot the textViews
        clientRequestIdTextView.setText("clientRequestId: " + clientRequestId);
        apiTraceIdTextView.setText("apiTraceId: "+apiTraceId);
        orderIdTextView.setText("orderId: "+orderId);
        lastFourDigitsTextView.setText("last4: "+paymentTokenLast4Digits);
        brandTextView.setText("brand: "+paymentTokenBrand);
    }

    /**
     * When user touches the press here to continue button, the OperationsActivity.java will start.
     * @param view - the button, press here to continue
     */
    public void goBackToOperationsActivityFromTokenVerificationActivity(View view){
        Intent intent = new Intent(this,OperationsActivity.class);
        intent.putExtra(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT,
                LogInActivity.USER_EMAIL_FOR_TESTING_PURPOSES);
        intent.putExtra(OperationsActivity.USER_NAME,"Marco");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}
