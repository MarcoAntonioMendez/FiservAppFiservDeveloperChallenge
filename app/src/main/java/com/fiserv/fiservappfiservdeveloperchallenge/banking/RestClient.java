package com.fiserv.fiservappfiservdeveloperchallenge.banking;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * The RestClient class sends HTTP petitions to Fiserv server to execute finance and banking
 * operations.
 *
 * Important: In order for this class to work, the API_KEY and API_SECRET constants should be filled
 * by the developer using his/her respective credentials.
 *
 * @author  MarcoAntonioMéndez,Fiserv Developing Team.
 * @version 1.0
 * @since   2020-10-26
 */

public class RestClient {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();//used for hmac sha256
    private static final String BASE_URL = "https://cert.api.firstdata.com/gateway/v2/";
    private static final String API_KEY = "vL28BOFd8BrFL2XwI54yDYblGNJG9NZk";
    private static final String API_SECRET = "6Ih3Jy1YuAyGzaXl";

    private AsyncHttpClient client;
    private Context appContext;

    public RestClient(Context ctx){
        this.client = new AsyncHttpClient();
        this.appContext = ctx;
    }

    public void get(String url, @Nullable JSONObject data, AsyncHttpResponseHandler responseHandler) throws  Exception{
        String jsonString = data==null?"":data.toString();
        this.setHeaders(jsonString);
        client.get(getAbsoluteUrl(url),null,responseHandler);
    }

    public void post(String url, @Nullable JSONObject data, AsyncHttpResponseHandler responseHandler) throws Exception {
        String jsonString = data==null?"":data.toString();
        this.setHeaders(jsonString);
        StringEntity entity = new StringEntity(jsonString);
        client.post(this.appContext,getAbsoluteUrl(url),jsonString== ""?null:entity,"application/json",responseHandler);
    }

    public void post(String url, @Nullable JSONArray data, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), null, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private void setHeaders(String jsonString) throws Exception{
        client.removeAllHeaders();
        client.addHeader("Content-Type","application/json");

        //HashMap<String,String> map = (HashMap<String, String>)createMessageSignature(jsonString).entrySet();

        for(Map.Entry entry : createMessageSignature(jsonString).entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            client.addHeader(key,value);
        }
    }

    private static HashMap<String,String> createMessageSignature(String payload) throws Exception{
        HashMap<String,String> map =  new HashMap<>();
        map.put("Client-Request-Id",UUID.randomUUID().toString());
        map.put("Api-Key",API_KEY);
        map.put("Timestamp",System.currentTimeMillis()+"");
        String msg = map.get("Api-Key")+map.get("Client-Request-Id")+
                map.get("Timestamp")+payload;
        map.put("Message-Signature",hash_hmac(msg,API_SECRET));
        return  map;
    }

    private static String hash_hmac(String str, String secret) throws Exception{
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] hashRaw = sha256_HMAC.doFinal(str.getBytes());
        String hex = bytesToHex(hashRaw).toLowerCase();
        String aux =Base64.encodeToString(hex.getBytes(),Base64.NO_WRAP);
        return aux;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
