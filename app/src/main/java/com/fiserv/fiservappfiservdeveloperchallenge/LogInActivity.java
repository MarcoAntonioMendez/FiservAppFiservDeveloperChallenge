package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class LogInActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String ENGLISH_LANGUAGE = "en";
    public static final String SPANISH_LANGUAGE = "es";
    public static final String USER_EMAIL_FOR_TESTING_PURPOSES = "admin@admin.com";
    public static final String USER_PASSWORD_FOR_TESTING_PURPOSES = "admin";
    public static final int INFORMATION_INCORRECT_SNACK_BAR_DURATION = 6000;

    private String language;
    private TextView helloTextView;
    private EditText enterUserEmailEditText,enterUserPasswordEditText;
    private Button enterInformationButton;
    private Snackbar informationIncorrectSnackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        language = Locale.getDefault().getLanguage();

        helloTextView = findViewById(R.id.hello_text_view_in_log_in);
        enterUserEmailEditText = findViewById(R.id.user_email_edit_text_log_in);
        enterUserPasswordEditText = findViewById(R.id.user_password_edit_text_log_in);
        enterInformationButton = findViewById(R.id.enter_button_log_in);

        //Setting the text of graphic components based on device language
        setTextForGraphicComponents();
    }

    /**
     * Method called when user presses the button that says "ENTER", the enterInformationButton
     * It checks if user email and password are correct so the user can enter Fiserv's System.
     * @param view - enterInformationButton, a Button.
     */
    public void accessFiservSystem(View view){
        if(isUserEnteredInformationCorrect()){
            Intent intent = new Intent(LogInActivity.this,OperationsActivity.class);
            startActivity(intent);
            finish();
        }else{
            // Inform user he/she didn't enter the correct information
            informationIncorrectSnackBar.show();
        }
    }

    /**
     * This method checks if user information is correct connecting to a database.
     *
     * WARNING:
     *      For the Fiserv Developer Challenge, this method will not ACTUALLY retrieve information
     *      from a remote database, since it's a hackathon, the "TrekingDevs" team didn't want to
     *      spend any money in an actual remote database.
     *      Although this method is intended to do so.
     * @return True or False, signifying the user's information is correct or incorrect
     */
    private boolean isUserEnteredInformationCorrect(){
        String userEmail = enterUserEmailEditText.getText().toString();
        String userPassword = enterUserPasswordEditText.getText().toString();

        if(userEmail.equals(USER_EMAIL_FOR_TESTING_PURPOSES) &&
                userPassword.equals(USER_PASSWORD_FOR_TESTING_PURPOSES)){
            return true;
        }
        return false;
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
                enterInformationButton.setText("Enter");
                informationIncorrectSnackBar = Snackbar.make(findViewById(R.id.log_in_coordinator_layout),
                        "Sorry :(, your e-mail or password are incorrect, please try again.",
                        INFORMATION_INCORRECT_SNACK_BAR_DURATION);
            break;
            case SPANISH_LANGUAGE:
                helloTextView.setText("Hola");
                enterUserEmailEditText.setText("Correo Electrónico");
                enterInformationButton.setText("Entrar");
                informationIncorrectSnackBar = Snackbar.make(findViewById(R.id.log_in_coordinator_layout),
                        "Lo sentimos :(, su correo electrónico o contraseña son incorrectos," +
                             " por favor inténtelo de nuevo.",
                        INFORMATION_INCORRECT_SNACK_BAR_DURATION);
            break;
            default:
                helloTextView.setText("Hello");
                enterUserEmailEditText.setText("E-mail");
                enterInformationButton.setText("Enter");
                informationIncorrectSnackBar = Snackbar.make(findViewById(R.id.log_in_coordinator_layout),
                        "Sorry :(, your e-mail or password are incorrect, please try again.",
                        INFORMATION_INCORRECT_SNACK_BAR_DURATION);
        }

        // Clears the e-mail EditText when user touches it.
        enterUserEmailEditText.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                enterUserEmailEditText.setText("");
                return false;
            }
        });

        // Clears the password EditText when user touches it.
        enterUserPasswordEditText.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                enterUserPasswordEditText.setText("");
                return false;
            }
        });
    }

}
