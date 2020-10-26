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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class CompleteMenu extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    private String userEmail;
    private TextView paymentsTextView,schedulePaymentTextView,myScheduledPaymentsTextView;
    private TextView myPaymentsHistoryTextView,customersTextView,generatePaymentUrlTextView;
    private TextView myCustomersTextView,registerNewCustomerTextView,profileTextView;
    private TextView registerNewAccountTextView,communicateTextView,contactFiservRepresentativeTextView;
    private TextView whyUseFiservTextView,rateUsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        userEmail = getIntent().getExtras().getString(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT);

        // Getting graphic elements references from XML file
        paymentsTextView = findViewById(R.id.payments_tag_complete_menu);
        schedulePaymentTextView = findViewById(R.id.schedule_payment_in_complete_menu);
        myScheduledPaymentsTextView = findViewById(R.id.my_schedule_payments_in_complete_menu);
        myPaymentsHistoryTextView = findViewById(R.id.history_payments_in_complete_menu);
        customersTextView = findViewById(R.id.customers_tag_complete_menu);
        generatePaymentUrlTextView = findViewById(R.id.generate_payment_url_in_complete_menu);
        myCustomersTextView = findViewById(R.id.my_customers_in_complete_menu);
        registerNewCustomerTextView = findViewById(R.id.register_new_customer_in_complete_menu);
        profileTextView = findViewById(R.id.profile_tag_complete_menu);
        registerNewAccountTextView = findViewById(R.id.register_new_account_in_complete_menu);
        communicateTextView = findViewById(R.id.communicate_tag_complete_menu);
        contactFiservRepresentativeTextView = findViewById(R.id.contact_fiserv_representative_in_complete_menu);
        whyUseFiservTextView = findViewById(R.id.why_fiserv_in_complete_menu);
        rateUsTextView = findViewById(R.id.rate_us_in_complete_menu);

        // Setting the text for graphic elements, according to language.
        setTextForGraphicElements();
    }

    /**
     * When user touches the register new customer icon, the RegisterNewCustomerActivity.java will
     * start.
     * @param view - the register new customer icon
     */
    public void startRegisterNewCustomerActivity(View view){
        Intent intent = new Intent(this,RegisterNewCustomerActivity.class);
        startActivity(intent);
    }

    /**
     * When user touches  the myPaymentsHistoryActivity icon, the MyPaymentsHistoryActivity.java
     * will start.
     * @param view - the myPaymentsHistoryActivity icon
     */
    public void startMyPaymentsHistoryActivity(View view){
        Intent intent = new Intent(this,MyPaymentsHistoryActivity.class);
        startActivity(intent);
    }

    /**
     * When user touches the mySchedulePayments icon, the MyScheduledPaymentsActivity.java will start
     * @param view the mySchedulePayments icon
     */
    public void startMyScheduledPaymentsActivity(View view){
        Intent intent = new Intent(this,MyScheduledPaymentsActivity.class);
        startActivity(intent);
    }

    /**
     * When user touches the icon to register a new bank account the RegisterNewBankAccountActivty.java
     * @param view - icon to register a new bank account.
     */
    public void startRegisterNewBankAccountActivity(View view){
        Intent intent = new Intent(this,RegisterNewBankAccountActivity.class);
        startActivity(intent);
    }

    /**
     * When user touches the icon of the help_icon the help Activity will start.
     * @param view - help_icon
     */
    public void startHelpActivityFromCompleteMenuActivity(View view){
        Intent intent = new Intent(this,HelpActivity.class);
        startActivity(intent);
    }

    /**
     * When user touches the why_fiserv_icon (the question mark), the WhyUsActivity.java will show.
     * @param view - The why_fiserv_icon
     */
    public void startWhyUsActivity(View view){
        Intent intent = new Intent(this,WhyUsActivity.class);
        startActivity(intent);
    }

    /**
     * When user touches the generatePaymentURL icon, the GeneratePaymentURLActivity will start.
     * @param view - The generatePaymentURL icon
     */
    public void startGeneratePaymentURLActivity(View view){
        Intent intent = new Intent(this,GeneratePaymentURLActivity.class);
        startActivity(intent);
    }

    /**
     * When user touches the schedulePaymentIcon (the little calendar), the SchedulePaymentActivity
     * shall start.
     * @param view - the schedulePaymentIcon
     */
    public void startSchedulePaymentActivity(View view){
        Intent intent =new Intent(this,SchedulePaymentActivity.class);
        intent.putExtra(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT,userEmail);
        startActivity(intent);
    }

    /**
     * Places the text for graphic elements according to the language, the app only supports spanish
     * an english right now.
     */
    private void setTextForGraphicElements(){
        String language = Locale.getDefault().getLanguage();

        switch(language){
            case AppGlobalConstants.ENGLISH_LANGUAGE:
                paymentsTextView.setText("Payments");
                schedulePaymentTextView.setText("Schedule Payment");
                myScheduledPaymentsTextView.setText("My Schedule Payments");
                myPaymentsHistoryTextView.setText("My Payments History");
                customersTextView.setText("Customers");
                generatePaymentUrlTextView.setText("Generate Payment URL");
                myCustomersTextView.setText("My Customers");
                registerNewCustomerTextView.setText("Register New Customer");
                profileTextView.setText("Profile");
                registerNewAccountTextView.setText("Register New Account");
                communicateTextView.setText("Contact");
                contactFiservRepresentativeTextView.setText("Help");
                whyUseFiservTextView.setText("Why Fiserv?");
                rateUsTextView.setText("Rate Us");
            break;
            case AppGlobalConstants.SPANISH_LANGUAGE:
                paymentsTextView.setText("Pagos");
                schedulePaymentTextView.setText("Agendar Pago");
                myScheduledPaymentsTextView.setText("Mis Pagos Agendados");
                myPaymentsHistoryTextView.setText("Mis Historial de Pagos");
                customersTextView.setText("Clientes");
                generatePaymentUrlTextView.setText("Generar URL de Pago");
                myCustomersTextView.setText("Mis Clientes");
                registerNewCustomerTextView.setText("Registrar Nuevo Cliente");
                profileTextView.setText("Perfil");
                registerNewAccountTextView.setText("Registrar una Nueva Cuenta");
                communicateTextView.setText("Contáctanos");
                contactFiservRepresentativeTextView.setText("Ayuda");
                whyUseFiservTextView.setText("¿Por qué Fiserv?");
                rateUsTextView.setText("Califícanos");
            break;
            default:
                paymentsTextView.setText("Payments");
                schedulePaymentTextView.setText("Schedule Payment");
                myScheduledPaymentsTextView.setText("My Schedule Payments");
                myPaymentsHistoryTextView.setText("My Payments History");
                customersTextView.setText("Customers");
                generatePaymentUrlTextView.setText("Generate Payment URL");
                myCustomersTextView.setText("My Customers");
                registerNewCustomerTextView.setText("Register New Customer");
                profileTextView.setText("Profile");
                registerNewAccountTextView.setText("Register New Account");
                communicateTextView.setText("Contact");
                contactFiservRepresentativeTextView.setText("Help");
                whyUseFiservTextView.setText("Why Fiserv?");
                rateUsTextView.setText("Rate Us");
        }
    }

}
