package com.leobee.animeconvention.manager.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class CopyOfJSONParser {
	
	static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    List<NameValuePair> pairs;
    String postURL;
    JSONObject returnedJson;
    String response;
    
    // constructor
    public CopyOfJSONParser() {
 
    }
    
    

    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
    	postURL=url;
    	pairs= params; 
    	 ServerTask a =new ServerTask();a.execute(new String[] {url});
		return returnedJson;

     
 
    }
    
	 class ServerTask extends AsyncTask<String, Void, String>{

			@Override
           protected String doInBackground(String... urls) {
				
				String response = "";
				for (String url : urls) {
					
					 DefaultHttpClient httpClient = new DefaultHttpClient();
			         HttpPost httpPost = new HttpPost(postURL);
		        try {
		            // defaultHttpClient
		           
		            
		       
		            // UrlEncodedFormEntity An entity composed of a list of url-encoded pairs. This is
		           
		            //typically useful while sending an HTTP POST request. 
		           
		            //setEntity(HttpEntity entity)Associates a response entity with this response.
		           
		            /*An entity is a lightweight persistence domain object. Typically, an entity represents 
		             * a table in a relational database, and each entity instance corresponds to a row in 
		             * that table. The primary programming artifact of an entity is the entity class, although
		             * entities can use helper classes.The persistent state of an entity is represented
		             * through either persistent fields or persistent properties. These fields or properties
		             * use object/relational mapping annotations to map the entities and entity 
		             * relationships to the relational data in the underlying data store.
		             *   */
		            httpPost.setEntity(new UrlEncodedFormEntity(pairs));
		            

		            HttpResponse httpResponse = httpClient.execute(httpPost);
		            Log.v("httpResponce excute", httpResponse.toString());
		            Log.v("httpResponce status", httpResponse.getStatusLine().toString());
		            HttpEntity httpEntity = httpResponse.getEntity();
		            Log.v("httpEntity status", httpEntity.getContent().toString());
		            is = httpEntity.getContent();
		           
		            Log.v(" parse json", is.toString());
		 
		        } catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		        } catch (ClientProtocolException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		        try {
		        	 Log.v(" try", "try caluse");
		        	 
		            BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1" ), 20);
		            Log.v(" reader ", reader.toString());
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            Log.v("line befor while loop", line + " n");
		            while ((line = reader.readLine()) != null) {
		            	Log.v("while loop", line +" values");
		                sb.append(line);
		            }
		            is.close();
		            json = sb.toString();
		            response += json;
		            Log.e("JSON String", json);
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		        }
		 
		       
		        
		        
			}// end of for loop
				return response;
				
				
		}
			@Override
			protected void onPostExecute(String result) {

				 // try parse the string to a JSON object
		        try {
		            jObj = new JSONObject(result);
		        } catch (JSONException e) {
		            Log.e("JSON Parser", "Error parsing data " + e.toString());
		        }
		 
		        // return JSON String
		     returnedJson=  jObj;
			}
			
			
			
	 }
 	
    
}


/*
 * 
 * */
