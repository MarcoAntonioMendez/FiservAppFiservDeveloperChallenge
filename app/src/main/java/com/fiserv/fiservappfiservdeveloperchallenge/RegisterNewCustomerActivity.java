package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * The RegisterNewCustomerActivity will allow the user to register a new customer so its information
 * can be used in other app0s sections.
 *
 * @author  MarcoAntonioMéndez
 * @version 1.0
 * @since   2020-10-26
 */

public class RegisterNewCustomerActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final int NEW_CUSTOMER_SNACK_BAR_DURATION = 4000;

    private Snackbar registerNewCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_customer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        // Setting the snack bar
        registerNewCustomer = Snackbar.make(findViewById(R.id.register_new_customer_coordinator_layout),
                "Cliente Registrado Exitósamente.",
                NEW_CUSTOMER_SNACK_BAR_DURATION);

        final Context context = this;
        registerNewCustomer.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event){
                Intent intent = new Intent(context,CompleteMenu.class);
                intent.putExtra(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT,"admin@admin.com");
                finish();
            }
            @Override
            public void onShown(Snackbar snackbar) { }
        });
    }

    /**
     * When user touches the register new customer button, the snack bar will show sating how
     * successfully the customer was registered.
     * @param view -
     */
    public void registerNewCustomer(View view){
        registerNewCustomer.show();
    }

}
