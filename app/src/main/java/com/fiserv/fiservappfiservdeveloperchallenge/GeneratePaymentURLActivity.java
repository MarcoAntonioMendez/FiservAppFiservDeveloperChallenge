package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_payment_url);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));
    }

    private void generatePaymentURL(String total){
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
            saleTransaction.put("purchaseOrderNumber","123055342");
            saleTransaction.put("ip","264.31.73.24");
            //send POST HTTP REQUEST
            client.post("payment-url", saleTransaction, new JsonHttpResponseHandler(){
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
