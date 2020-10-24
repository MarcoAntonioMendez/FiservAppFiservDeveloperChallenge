package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class SchedulePaymentActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    private String userEmail;
    private TextView scheduleYourPaymentTitleTextView,beautifulMessageTextView;
    private TextView schedulePaymentFirstInstructionTextView;
    private Spinner monthSpinner,daySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_payment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        userEmail = getIntent().getExtras().getString(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT);

        // Getting graphic elements references from XML file
        scheduleYourPaymentTitleTextView = findViewById(R.id.schedule_your_payment_title_in_schedule_payment_activity);
        beautifulMessageTextView = findViewById(R.id.beautiful_message_in_schedule_payment_activity);
        schedulePaymentFirstInstructionTextView = findViewById(R.id.schedule_payment_first_instruction_in_schedule_payment_activity);
        monthSpinner = findViewById(R.id.month_spinner_in_schedule_payment_activity);
        daySpinner = findViewById(R.id.day_spinner_in_schedule_payment_activity);


        // Setting texts for graphic elements
        setTextForGraphicElements();
    }

    /**
     * Sets the text for all graphic elements according to the language.
     * Supported Languages: English and Spanish.
     */
    private void setTextForGraphicElements(){
        String language = Locale.getDefault().getLanguage();
        ArrayAdapter<CharSequence> monthSpinnerAdapter = null;

        ArrayAdapter<CharSequence> daySpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.days_for_spinner, android.R.layout.simple_spinner_item);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(daySpinnerAdapter);

        switch(language){
            case AppGlobalConstants.ENGLISH_LANGUAGE:
                scheduleYourPaymentTitleTextView.setText("Schedule your Payment");
                beautifulMessageTextView.setText(R.string.text_for_in_english_beautiful_message_in_schedule_payment_activity);
                schedulePaymentFirstInstructionTextView.setText(R.string.text_in_english_for_schedule_payment_first_instruction_in_schedule_payment_activity);
                monthSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.months_in_english, android.R.layout.simple_spinner_item);
                break;
            case AppGlobalConstants.SPANISH_LANGUAGE:
                scheduleYourPaymentTitleTextView.setText("Agenda tu Pago");
                beautifulMessageTextView.setText(R.string.text_for_in_spanish_beautiful_message_in_schedule_payment_activity);
                schedulePaymentFirstInstructionTextView.setText(R.string.text_in_spanish_for_schedule_payment_first_instruction_in_schedule_payment_activity);
                monthSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.months_in_spanish, android.R.layout.simple_spinner_item);
                break;
            default:
                scheduleYourPaymentTitleTextView.setText("Schedule your Payment");
                beautifulMessageTextView.setText(R.string.text_for_in_english_beautiful_message_in_schedule_payment_activity);
                schedulePaymentFirstInstructionTextView.setText(R.string.text_in_english_for_schedule_payment_first_instruction_in_schedule_payment_activity);
                monthSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.months_in_english, android.R.layout.simple_spinner_item);
        }

        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthSpinnerAdapter);
    }

}
