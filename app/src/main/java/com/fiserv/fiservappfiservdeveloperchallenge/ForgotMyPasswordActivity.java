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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class ForgotMyPasswordActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    private TextView dontWorryTetView;
    private EditText emailEditText;
    private Button sendEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_my_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        // Getting graphic elements references from XML file.
        dontWorryTetView = findViewById(R.id.dont_worry_text_view_forgot_my_password_activity);
        emailEditText = findViewById(R.id.enter_email_edit_text_forgot_my_password_activity);
        sendEmailButton = findViewById(R.id.send_email_button_forgot_my_password_activity);

        // Sets the text for all graphic components
        setGraphicComponentsText();
    }

    /**
     *
     */
    public void sendEmailAndStartLogInActivity(View view){
       Intent intent = new Intent(this,LogInActivity.class);
       startActivity(intent);
    }

    /**
     * Sets the text for all graphic components according to the language.
     * Supported languages: English, Spanish.
     */
    private void setGraphicComponentsText(){
        String language = Locale.getDefault().getLanguage();

        switch(language){
            case AppGlobalConstants.ENGLISH_LANGUAGE:
                dontWorryTetView.setText("Don't worry if you forgot your password, just enter your" +
                        " email we'll send you en email with the instructions to follow :D.");
                emailEditText.setText("E-mail");
                sendEmailButton.setText("Send");
            break;
            case AppGlobalConstants.SPANISH_LANGUAGE:
                dontWorryTetView.setText("No te preocupes si olvidaste tu contraseña, sólo ingresa" +
                        " tu correo electrónico y te enviaremos un correo electrónico con las " +
                        " instrucciones a seguir :D.");
                emailEditText.setText("Correo Electrónico");
                sendEmailButton.setText("Enviar");
            break;
            default:
                dontWorryTetView.setText("Don't worry if you forgot your password, just enter your" +
                        " email we'll send you en email with the instructions to follow :D.");
                emailEditText.setText("E-mail");
                sendEmailButton.setText("Send");
        }

        // Setting a listener for emailEditText
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus){
                if(hasFocus){
                   switch(Locale.getDefault().getLanguage()){
                       case AppGlobalConstants.ENGLISH_LANGUAGE:
                           if(emailEditText.getText().toString().equals("E-mail")){
                               emailEditText.setText("");
                           }
                       break;
                       case AppGlobalConstants.SPANISH_LANGUAGE:
                           if(emailEditText.getText().toString().equals("Correo Electrónico")){
                               emailEditText.setText("");
                           }
                       break;
                       default:
                           if(emailEditText.getText().toString().equals("E-mail")){
                               emailEditText.setText("");
                           }
                   }
                }
            }
        });
    }

}
