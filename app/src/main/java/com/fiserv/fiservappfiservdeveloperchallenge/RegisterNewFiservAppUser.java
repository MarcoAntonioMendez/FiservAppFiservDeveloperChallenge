package com.fiserv.fiservappfiservdeveloperchallenge;

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

public class RegisterNewFiservAppUser extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    private TextView welcomeTextView,awesomeExperienceTextView;
    private EditText completeNameEditText,emailEditText,phoneNumberEditText,passwordEditText;
    private EditText repeatPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_fiserv_app_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        // Getting graphic elements references from XML
        welcomeTextView = findViewById(R.id.welcome_sign_in_register_new_fiserv_app_user);
        awesomeExperienceTextView = findViewById(R.id.sign_up_to_start_using_the_best_banking_experience);
        completeNameEditText = findViewById(R.id.full_name_in_register_new_fiserv_app_user);
        emailEditText = findViewById(R.id.user_email_register_new_fiserv_app_user);
        phoneNumberEditText = findViewById(R.id.phone_number_register_new_fiserv_app_user);
        passwordEditText = findViewById(R.id.password_register_new_fiserv_app_user);
        repeatPasswordEditText = findViewById(R.id.repeat_password_register_new_fiserv_app_user);
        registerButton = findViewById(R.id.register_me_button_in_register_new_fiserv_app_user);

        setTextForGraphicComponents();
    }

    /**
     * Sets the text for all graphic components according to the language,
     * Supported Languages for now: English and Spanish.
     */
    private void setTextForGraphicComponents(){
        String language = Locale.getDefault().getLanguage();

        switch(language){
            case AppGlobalConstants.ENGLISH_LANGUAGE:
                welcomeTextView.setText("Welcome");
                awesomeExperienceTextView.setText("Sign Up to have the best banking experience!");
                completeNameEditText.setText("Full Legal Name");
                emailEditText.setText("E-mail");
                phoneNumberEditText.setText("Phone Number");
                passwordEditText.setText("Password");
                repeatPasswordEditText.setText("Repeat Password");
            break;
            case AppGlobalConstants.SPANISH_LANGUAGE:
                welcomeTextView.setText("Bienvenido");
                awesomeExperienceTextView.setText("¡Regístrate para tener la mejor experiencia bancaria!");
                completeNameEditText.setText("Nombre completo");
                emailEditText.setText("Correo Electrónico");
                phoneNumberEditText.setText("Número Telefónico");
                passwordEditText.setText("Contraseña");
                repeatPasswordEditText.setText("Repetir Contraseña");
            break;
            default:
                welcomeTextView.setText("Welcome");
                awesomeExperienceTextView.setText("Sign Up to have the best banking experience!");
                completeNameEditText.setText("Full Legal Name");
                emailEditText.setText("E-mail");
                phoneNumberEditText.setText("Phone Number");
                passwordEditText.setText("Password");
                repeatPasswordEditText.setText("Repeat Password");
        }
    }

}
