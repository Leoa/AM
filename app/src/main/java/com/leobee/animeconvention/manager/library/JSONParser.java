package com.leobee.animeconvention.manager.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
 


 
public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    String str;
    // constructor
    public JSONParser() {
 
    }
 
    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
 
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
           
          
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
        
             str = inputStreamToString(httpResponse.getEntity().getContent()).toString();
         
            
            
            
            if(str.toString().equalsIgnoreCase("true"))
            {

            }else
            {
                       
            }
            /*
            HttpEntity httpEntity = httpResponse.getEntity();
            Log.v("Http Response",httpResponse.getStatusLine().toString());
            is = httpEntity.getContent();
 */
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
       
 
        // try parse the string to a JSON object
        try {
        	
            jObj = new JSONObject(str);
         
            
            
        } catch (JSONException e) {
           System.out.println(e);
        }
 
        // return JSON String
        return jObj;
 
    }
    
    
    
    
    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        // Read response until the end
        try {
         while ((line = rd.readLine()) != null) {
           total.append(line);
           json = total.toString();
         }
        } catch (IOException e) {
         e.printStackTrace();
        }
        // Return full string
        return total;
       }
}