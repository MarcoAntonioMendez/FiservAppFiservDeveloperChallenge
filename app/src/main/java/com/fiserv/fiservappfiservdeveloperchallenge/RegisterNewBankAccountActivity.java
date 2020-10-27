package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiserv.fiservappfiservdeveloperchallenge.banking.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpResponseException;

/**
 * The RegisterNewBankAccountActivity will allow the user to register a new payment card using
 * a token so the banking information is safe.
 *
 * The class will send appropriate HTTP petitions to Fiserv server to a payment card token can be
 * generated.
 *
 * @author  MarcoAntonioMéndez
 * @version 1.0
 * @since   2020-10-26
 */

public class RegisterNewBankAccountActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String CLIENT_REQUEST_ID_CONSTANT = "clientRequestId";
    public static final String API_TRACE_ID_CONSTANT = "apiTraceId";
    public static final String ORDER_ID_CONSTANT = "orderId";
    public static final String PAYMENT_TOKEN_CONSTANT_FOR_ARRAY = "paymentToken";
    public static final String PAYMENT_TOKEN_LAST_4_DIGITS_CONSTANTS = "last4";
    public static final String PAYMENT_TOKEN_BRAND_CONSTANT = "brand";

    private Spinner monthSpinner,yearSpinner;
    private EditText cardNumberEditText,cvvCodeEditText;
    private boolean tokenHasBeenCreatedCorrectly;
    private String clientRequestId,apiTraceId,orderId,paymentTokenLast4Digits,paymentTokenBrand;
    private Context context;
    private TextView generatingTokenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_bank_account);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));
        tokenHasBeenCreatedCorrectly = false;
        context = this;

        // Getting references for graphic elements from XML file
        monthSpinner = findViewById(R.id.month_spinner_in_register_new_bank_account_activity);
        yearSpinner = findViewById(R.id.year_spinner_in_register_new_bank_account_activity);
        cardNumberEditText = findViewById(R.id.card_number_edit_text_in_register_new_bank_account_activity);
        cvvCodeEditText = findViewById(R.id.cvv_code_edit_text_in_register_new_bank_account_activity);
        generatingTokenTextView = findViewById(R.id.generating_card_token_in_register_new_bank_account);

        generatingTokenTextView.setVisibility(View.INVISIBLE);

        setSpinnersAndEditText();
        startCheckingLoop();
    }

    /**
     * This endless loop will be useful to start a new activity once the token has been created.
     */
    private void startCheckingLoop(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{Thread.sleep(30);}catch(InterruptedException e){e.printStackTrace();}
                    checkIfTokenWasCreatedCorrectly();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Checks if the token was created correctly, if it was created correctly, the
     * TokenVerificationActivity will start.
     */
    private void checkIfTokenWasCreatedCorrectly(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(tokenHasBeenCreatedCorrectly){
                    tokenHasBeenCreatedCorrectly = false;
                    // Starting the TokenVerificationActivity
                    Intent intent = new Intent(context,TokenVerificationActivity.class);
                    intent.putExtra(AppGlobalConstants.CLIENT_REQUEST_ID_CONSTANT,clientRequestId);
                    intent.putExtra(AppGlobalConstants.API_TRACE_ID_CONSTANT,apiTraceId);
                    intent.putExtra(AppGlobalConstants.ORDER_ID_CONSTANT,orderId);
                    intent.putExtra(AppGlobalConstants.PAYMENT_TOKEN_INDEX_FOR_LAST_4_DATA_CONSTANT,
                                                                           paymentTokenLast4Digits);
                    intent.putExtra(AppGlobalConstants.PAYMENT_TOKEN_INDEX_FOR_BRAND_DATA_CONSTANT,
                                                                                 paymentTokenBrand);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**
     * When user touches the registerNewBankAccount button, a token for the payment card will be
     * generated.
     * @param view Th button registerNewBankAccount
     */
    public void registerNewBankAccount(View view){
        generatePaymentCardToken();
        generatingTokenTextView.setVisibility(View.VISIBLE);
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
        final RestClient client = new RestClient(getApplicationContext());

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
                    tokenHasBeenCreatedCorrectly = true;
                    try {
                        clientRequestId = response.getString(CLIENT_REQUEST_ID_CONSTANT);
                        apiTraceId = response.getString(API_TRACE_ID_CONSTANT);
                        orderId = response.getString(ORDER_ID_CONSTANT);
                        paymentTokenLast4Digits =
                                response.getJSONObject(PAYMENT_TOKEN_CONSTANT_FOR_ARRAY).getString(
                                PAYMENT_TOKEN_LAST_4_DIGITS_CONSTANTS);
                        paymentTokenBrand =
                                response.getJSONObject(PAYMENT_TOKEN_CONSTANT_FOR_ARRAY).getString(
                                PAYMENT_TOKEN_BRAND_CONSTANT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
