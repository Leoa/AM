package com.leobee.animeconvention.manager.library;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.util.Log;
import android.content.Context;
 
public class UserFunctions {
 
    private JSONParser jsonParser;
 
    // Testing in Leobee.com
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    
    private  final static String  loginURL = "http://www.txtease.com/android/push/login/index.php";
    private  final static String registerURL = "http://www.txtease.com/android/push/login/json3.php";
 
 
    private static String login_tag = "login";
    private static String register_tag = "register";
 
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
 
    /**
     * function make Login Request
     * @param email
     * @param password
     *  */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> paramsx = new ArrayList<NameValuePair>();
        paramsx.add(new BasicNameValuePair("tag", login_tag));
        paramsx.add(new BasicNameValuePair("email", email));
        paramsx.add(new BasicNameValuePair("password", password));
        String params = "tag="+login_tag+"&email="+email+"&password="+password; 
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, paramsx);
        Log.v(" --------------------------Login url ", registerURL);
        Log.i("---------------------------url placed in json object",  params);
        Log.e("JSON", json.toString());
       return json;
    }

    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password){
    	Log.v("register user", name+ " "+email +" " + password);
        // Building Parameters
        ArrayList<NameValuePair> paramsx = new ArrayList<NameValuePair>(4);
        paramsx.add(new BasicNameValuePair("tag", register_tag));
        paramsx.add(new BasicNameValuePair("name", name));
        paramsx.add(new BasicNameValuePair("email", email));
        paramsx.add(new BasicNameValuePair("password", password));
 
        String params = "tag="+register_tag+"&name="+name+"&email="+email+"&password="+password;  
        // getting JSON Object
        Log.v(" --------------------------Register url ", registerURL);
        Log.i("---------------------------url placed in json object",  params);
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, paramsx);
        // return json
        //Log.v("JSON parser string", json.toString());
        return json;
    }
 
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
 
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
 
}
