package com.fiserv.fiservappfiservdeveloperchallenge;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class LogInActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String ENGLISH_LANGUAGE = "en";
    public static final String SPANISH_LANGUAGE = "es";

    private String language;
    private TextView helloTextView;
    private EditText enterUserEmailEditText,enterUserPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        language = Locale.getDefault().getLanguage();

        helloTextView = findViewById(R.id.hello_text_view_in_log_in);
        enterUserEmailEditText = findViewById(R.id.user_email_edit_text_log_in);
        enterUserPasswordEditText = findViewById(R.id.user_password_edit_text_log_in);

        //Setting the text of graphic components based on device language
        setTextForGraphicComponents();
    }

    /**
     * According to language, graphic components are assigned the text.
     * In case the device's language is not supported, the text will be assigned to english.
     * Languages Supported: English, Spanish.
     */
    private void setTextForGraphicComponents(){
        switch(language){
            case ENGLISH_LANGUAGE:
                helloTextView.setText("Hello");
                enterUserEmailEditText.setText("E-mail");
            break;
            case SPANISH_LANGUAGE:
                helloTextView.setText("Hola");
                enterUserEmailEditText.setText("Correo Electrónico");
            break;
            default:
                helloTextView.setText("Hello");
        }

    }

}
