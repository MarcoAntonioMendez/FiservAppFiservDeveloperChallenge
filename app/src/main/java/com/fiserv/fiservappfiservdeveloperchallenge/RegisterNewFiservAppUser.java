package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class RegisterNewFiservAppUser extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final int PASSWORDS_DONT_MATCH_SNACK_BAR_DURATION = 4000;

    private TextView welcomeTextView,awesomeExperienceTextView;
    private EditText completeNameEditText,emailEditText,phoneNumberEditText,passwordEditText;
    private EditText repeatPasswordEditText;
    private Snackbar passwordsDontMatchtSnackBar;
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

        // Sets the text for all graphic components
        setTextForGraphicComponents();

        setListenerForEditTexts();
    }

    /**
     * Button pressed when user's data is ready to be registered.
     * It will check if the passwords are correct.
     *
     * WARNING: This method is supposed to do some queries to a remote database, since this app was
     * made for the FiservDeveloperChallenge, the team "TrekingDevs" did not want to spend any money
     * in a remote database, so right now it justs checks that the passwords are right and then it
     * transitions to OperationsActivity
     * @param view - The button registerButton
     */
    public void registerNewFiservAppUser(View view){
        // Checking if the content in passwordEditText and repeatPasswordEditText is the same.
        if(!passwordEditText.getText().toString().equals(repeatPasswordEditText.getText().toString())){
            passwordsDontMatchtSnackBar.show();
            return;
        }

        // Starting OperationsActivity
        Intent intent = new Intent(this,OperationsActivity.class);
        // The hardcoded string "Marco" is for testing, since there is no database at this point
        intent.putExtra(OperationsActivity.USER_NAME,"Marco");
        intent.putExtra(AppGlobalConstants.USER_EMAIL_PUT_EXTRA_CONSTANT,
                        emailEditText.getText().toString());
        startActivity(intent);
        finish();
    }

    /**
     * Sets the a listener for each editText, those listener will bring the editTexts back to their
     * initial states if they are left empty after user tried to type something.
     */
    private void setListenerForEditTexts(){
        String spanishText;

        // Setting the listeners for each editText
        spanishText = "Nombre Completo";
        setEditTextListenerIndividually(completeNameEditText,spanishText);

        spanishText = "Correo Electrónico";
        setEditTextListenerIndividually(emailEditText,spanishText);

        spanishText = "Número Telefónico";
        setEditTextListenerIndividually(phoneNumberEditText,spanishText);

        spanishText = "Contraseña";
        setEditTextListenerIndividually(passwordEditText,spanishText);

        spanishText = "Repetir Contraseña";
        setEditTextListenerIndividually(repeatPasswordEditText,spanishText);
    }

    /**
     * Sets the listener to an specific editText, if the user is typing on the editText and then
     * leaves it empty, that editText will go back to its initial state.
     * @param editText - The edit text to set its focus listener.
     * @param spanishText - The text that should be placed in an empty editText (in spanish).
     */
    private void setEditTextListenerIndividually(final EditText editText,final String spanishText){
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus){
                if(!hasFocus){
                    // Checking if user left the editText empty
                    if(editText.getText().toString().isEmpty()){
                        editText.setText(spanishText);

                        // If that editText was for passwords, it goes back to normal input
                        if(editText.equals(passwordEditText)){
                            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }else if(editText.equals(repeatPasswordEditText)){
                            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }else{
                    // If the user is typing his/her password, the input type should change.
                    if(editText.equals(passwordEditText)){
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }else if(editText.equals(repeatPasswordEditText)){
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            }
        });
    }

    /**
     * Sets the text for graphic elements
     */
    private void setTextForGraphicComponents(){
        welcomeTextView.setText("Bienvenido");
        awesomeExperienceTextView.setText("¡Regístrate para tener la mejor experiencia bancaria!");
        completeNameEditText.setText("Nombre completo");
        emailEditText.setText("Correo Electrónico");
        phoneNumberEditText.setText("Número Telefónico");
        passwordEditText.setText("Contraseña");
        repeatPasswordEditText.setText("Repetir Contraseña");
        passwordsDontMatchtSnackBar = Snackbar.make(findViewById(R.id.register_new_fiserv_app_user_coordinator_layout_id),
                "Lo sentimos :(, parece ser que las contraseñas que ingresó no son iguales.",
                PASSWORDS_DONT_MATCH_SNACK_BAR_DURATION);
        registerButton.setText("¡Regístrame!");
    }

}
