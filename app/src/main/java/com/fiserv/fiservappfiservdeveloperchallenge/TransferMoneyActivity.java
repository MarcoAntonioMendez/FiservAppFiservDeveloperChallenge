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

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpResponseException;

public class TransferMoneyActivity extends AppCompatActivity {
    public static final String NAVIGATION_BAR_COLOR = "#FE3412";
    public static final String STATUS_BAR_COLOR = "#363636";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(Color.parseColor(NAVIGATION_BAR_COLOR));
        getWindow().setStatusBarColor(Color.parseColor(STATUS_BAR_COLOR));

        //generatePaymentURL("100.00");
    }

    /**
     * This methods is used to make a direct sale
     * @param total
     */
    private void generatePaymentURL(String total){
        RestClient client = new RestClient(getApplicationContext());

        try {
            //Build JSON
            JSONObject saleTransaction = new JSONObject();
            saleTransaction.put("requestType","PaymentCardSaleTransaction");
            saleTransaction.put("transactionAmount",
                    (new JSONObject())
                            .put("total", total)
                            .put("currency", "MXN")
            );
            saleTransaction.put("paymentMethod",
                    (new JSONObject()).put("paymentCard",
                            (new JSONObject())
                                    .put("number", "4004430000000007")
                                    .put("securityCode", "111")
                                    .put("expiryDate",(new JSONObject())
                                            .put("month", "12")
                                            .put("year", "24"))
                    )
            );

            //send POST HTTP REQUEST
            client.post("payments", saleTransaction, new JsonHttpResponseHandler(){
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
