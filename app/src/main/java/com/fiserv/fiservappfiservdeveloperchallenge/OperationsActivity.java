package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class OperationsActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String USER_NAME = "USER_NAME";

    private String userName,language;
    private TextView greetUserTextView,transferTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        language = Locale.getDefault().getLanguage();

        // Getting graphic components from xml file
        greetUserTextView = findViewById(R.id.greet_user_text_view_operations);
        transferTextView = findViewById(R.id.operations_transfer_text_view);

        // Getting the userName
        userName = getIntent().getExtras().getString(USER_NAME);

        setTextForGraphicComponents();
    }

    /**
     * According to language, graphic components are assigned the text.
     * In case the device's language is not supported, the text will be assigned to english.
     * Languages Supported: English, Spanish.
     */
    private void setTextForGraphicComponents(){
        switch(language){
            case AppGlobalConstants.ENGLISH_LANGUAGE:
                greetUserTextView.setText("Hello, " + userName);
                transferTextView.setText("Transfer");
            break;
            case AppGlobalConstants.SPANISH_LANGUAGE:
                greetUserTextView.setText("Hola, " + userName);
                transferTextView.setText("Transferir");
            break;
            default:
                greetUserTextView.setText("Hello, " + userName);
                transferTextView.setText("Transfer");
        }
    }

}
