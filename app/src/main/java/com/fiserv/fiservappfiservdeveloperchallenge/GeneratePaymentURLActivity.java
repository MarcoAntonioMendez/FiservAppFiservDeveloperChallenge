package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import com.fiserv.fiservappfiservdeveloperchallenge.banking.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpResponseException;

public class GeneratePaymentURLActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";
    public static final String PAYMENT_URL_CONSTANT = "paymentUrl";

    private EditText orderNumberEditText,subtotalEditText,otherChargesEditText,totalPriceEditText;
    private TextView chooseHowToShareTextView;
    private ImageView whatsappIcon,emailIcon;
    private String generatedUrl;
    private volatile boolean urlSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_payment_url);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));
        urlSet = false;

        // Getting references from XML file
        orderNumberEditText = findViewById(R.id.order_number_edit_text_in_generate_payment_url_activity);
        subtotalEditText = findViewById(R.id.subtotal_edit_text_in_generate_payment_url_activity);
        otherChargesEditText = findViewById(R.id.other_charges_edit_text_in_generate_payment_url_activity);
        totalPriceEditText = findViewById(R.id.total_price_edit_text_in_generate_payment_url_activity);
        chooseHowToShareTextView = findViewById(R.id.choose_how_to_share_text_view_in_generate_payment_url_activity);
        whatsappIcon = findViewById(R.id.whatsapp_icon_in_generate_payment_url_activity);
        emailIcon = findViewById(R.id.email_icon_in_generate_payment_url_activity);

        chooseHowToShareTextView.setVisibility(View.INVISIBLE);
        whatsappIcon.setVisibility(View.INVISIBLE);
        emailIcon.setVisibility(View.INVISIBLE);

        startCheckingLoop();
    }

    /**
     * When user touches emailIcon, the url payment will be shared through email.
     * @param view - emailIcon.
     */
    public void shareURLThroughGmail(View view){
        String[] TO = {"tondenga12@gmail.com"};
        String[] CC = {"marco.mendez.ing@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Payment URL");
        emailIntent.putExtra(Intent.EXTRA_TEXT, generatedUrl);

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    /**
     * When user touches whatsappIcon, the url payment will be shared through whatsapp.
     * @param view - whatsappIcon
     */
    public void shareURLThroughWhatsapp(View view){
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("text/plain");
        waIntent.setPackage("com.whatsapp");
        waIntent.putExtra(Intent.EXTRA_TEXT, generatedUrl);
        startActivity(Intent.createChooser(waIntent, "Compartir URL"));
    }

    /**
     * When user presses the button= Generate Payment URL.
     * @param view - generatePaymentURL
     */
    public void onGeneratePaymentURLButtonPressed(View view){
        generatePaymentURL(totalPriceEditText.getText().toString(),
                orderNumberEditText.getText().toString());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chooseHowToShareTextView.setVisibility(View.VISIBLE);
                chooseHowToShareTextView.setText("Generando URL...");
            }
        });
    }

    /**
     * Sets the loop that will keep on calculating the total price for the payment URL.
     */
    private void startCheckingLoop(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    keepCalculating();
                    checkIfPaymentURLWasGenerated();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Checks if payment was already generated, if it was already generated; the chooseToShareTextView
     * whatsappIcon and emailIcon will become visible.
     */
    private void checkIfPaymentURLWasGenerated(){
        if(urlSet){
            runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    chooseHowToShareTextView.setText("Puedes compartir el URL a través de:");
                    whatsappIcon.setVisibility(View.VISIBLE);
                    emailIcon.setVisibility(View.VISIBLE);
                }
            });
            urlSet = false;
        }
    }

    /**
     * Adds the prices in subtotal and otherCharges. When user inputs any number, the changes will
     * be reflected in totalPriceEditText.
     */
    private void keepCalculating(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String subtotal = "0",otherCharges = "0";
                if(!subtotalEditText.getText().toString().isEmpty()){
                    subtotal = subtotalEditText.getText().toString();
                }

                if(!otherChargesEditText.getText().toString().isEmpty()){
                    otherCharges = otherChargesEditText.getText().toString();
                }

                double total = Double.parseDouble(subtotal) + Double.parseDouble(otherCharges);

                totalPriceEditText.setText(Double.toString(total));
            }
        });
    }

    /**
     * This method sends an http request to FiservServer so an URL payment ca be generated.
     * @param total - Total amount of money for the transaction.
     * @param orderNumber - order Id
     */
    private void generatePaymentURL(String total,String orderNumber){
        RestClient client = new RestClient(getApplicationContext());

        try {
            //Build JSON
            JSONObject saleTransaction = new JSONObject();
            saleTransaction.put("transactionAmount",
                    (new JSONObject())
                            .put("total", total)
                            .put("currency", "MXN")
            );
            saleTransaction.put("transactionType","SALE");
            saleTransaction.put("transactionNotificationURL","https://showmethepaymentresult.com");
            saleTransaction.put("expiration","4102358400");
            saleTransaction.put("authenticateTransaction","true");
            saleTransaction.put("dynamicMerchantName","MyWebsite");
            saleTransaction.put("invoiceNumber","96126098");
            saleTransaction.put("purchaseOrderNumber",orderNumber);
            saleTransaction.put("ip","264.31.73.24");
            //send POST HTTP REQUEST
            client.post("payment-url", saleTransaction, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        generatedUrl = response.getString(PAYMENT_URL_CONSTANT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("SuccessObj", response.toString());
                    urlSet = true;
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
