package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.fiserv.fiservappfiservdeveloperchallenge.banking.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpResponseException;

public class RegisterNewBankAccountActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    private Spinner monthSpinner,yearSpinner;
    private EditText cardNumberEditText,cvvCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_bank_account);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        // Getting references for graphic elements from XML file
        monthSpinner = findViewById(R.id.month_spinner_in_register_new_bank_account_activity);
        yearSpinner = findViewById(R.id.year_spinner_in_register_new_bank_account_activity);
        cardNumberEditText = findViewById(R.id.card_number_edit_text_in_register_new_bank_account_activity);
        cvvCodeEditText = findViewById(R.id.cvv_code_edit_text_in_register_new_bank_account_activity);

        setSpinnersAndEditText();
    }

    /**
     * When user touches the registerNewBankAccount button, a token for the payment card will be
     * generated.
     * @param view Th button registerNewBankAccount
     */
    public void registerNewBankAccount(View view){
        generatePaymentCardToken();
    }

    /**
     * Sets the information for spinners and some listeners to the editTexts to make more readable
     * and usable for users.
     */
    private void setSpinnersAndEditText(){
        ArrayAdapter<CharSequence> daySpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.years_for_spinners, android.R.layout.simple_spinner_item);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(daySpinnerAdapter);

        ArrayAdapter<CharSequence> monthSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.months_in_spanish, android.R.layout.simple_spinner_item);
        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthSpinnerAdapter);

        // Setting listeners for editTexts
        cardNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus){
                if(hasFocus){
                    if(cardNumberEditText.getText().toString().equals("Ingrese el número de su tarjeta")){
                        cardNumberEditText.setText("");
                    }
                }else{
                    if(cardNumberEditText.getText().toString().isEmpty()){
                        cardNumberEditText.setText("Ingrese el número de su tarjeta");
                    }
                }
            }
        });

        cvvCodeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus){
                if(hasFocus){
                    if(cvvCodeEditText.getText().toString().equals("Ingrese el código de seguridad (CVV):")){
                        cvvCodeEditText.setText("");
                    }
                }else{
                    if(cvvCodeEditText.getText().toString().isEmpty()){
                        cvvCodeEditText.setText("Ingrese el código de seguridad (CVV):");
                    }
                }
            }
        });
    }

    /**
     * This methods generates a token for a card to be saved
     * The information hardcoded belongs to credentials given by Fiserv for the FiservDeveloperChallenge
     */
    private void generatePaymentCardToken(){
        RestClient client = new RestClient(getApplicationContext());

        try {
            //Build JSON
            JSONObject saleTransaction = new JSONObject();
            saleTransaction.put("requestType","PaymentCardPaymentTokenizationRequest");

            saleTransaction.put("paymentCard",
                    (new JSONObject())
                            .put("number", "4004430000000007")
                            .put("securityCode","111")
                            .put("expiryDate",(new JSONObject())
                                    .put("month", "12")
                                    .put("year", "24"))
            );
            saleTransaction.put("createToken",
                    (new JSONObject())
                            .put("reusable", "true")
                            .put("declineDuplicates", "false")
            );

            //send POST HTTP REQUEST
            client.post("payment-tokens", saleTransaction, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.d("SuccessObj", response.toString());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    //handle success when response is an array of objects
                    Log.d("SuccessArr", response.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    //handle error when response is an array of objects
                    Log.d("ErrorArr", errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    //handle success when response is a single object
                    Log.d("ErrorObj", errorResponse.toString());
                }
            });

        }catch (HttpResponseException ex){
            Log.d("HttpResponseException", ex.getMessage());
        }
        catch (JSONException ex){
            Log.d("JSONException", ex.getMessage());
        }
        catch (Exception ex){
            Log.d("Exception", ex.getMessage());
        }

    }

}
