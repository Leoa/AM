package com.leobee.animeconvention.manager.loging;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leobee.animeconvention.manager.R;
import com.leobee.animeconvention.manager.library.DatabaseHandler;
import com.leobee.animeconvention.manager.library.UserFunctions;
import com.leobee.animeconvention.manager.manager.DataView;
import com.leobee.animeconvention.manager.utilities.AppStatus;


public class LoginActivity extends Activity {
    Context context;
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    JSONObject json;
    UserFunctions userFunction;
    Context ctx= this;
 
    
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
 
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);

        

        Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        inputEmail.startAnimation(animationFadeIn);
        inputPassword.startAnimation(animationFadeIn);
        btnLogin.startAnimation(animationFadeIn);
        btnLinkToRegister.startAnimation(animationFadeIn);
        
        Animation mAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
      //  layout.startAnimation(mAnim);

        
        
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (AppStatus.getInstance(context).isOnline(context)) {
                    Log.v("application", " is online");
                 userFunction = new UserFunctions();
                 json = userFunction.loginUser(email, password);
                    // check for login response
                    try {
                        if (json.getString(KEY_SUCCESS) != null) {
                            loginErrorMsg.setText("");
                            String res = json.getString(KEY_SUCCESS);
                            if(Integer.parseInt(res) == 1){
                                // user successfully logged in
                                // Store user details in SQLite Database
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                JSONObject json_user = json.getJSONObject("user");

                                // Clear all previous data in database
                                userFunction.logoutUser(getApplicationContext());
                                db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

                                // Launch Dashboard Screen
                                Intent dashboard = new Intent(getApplicationContext(), DataView.class);

                                // Close all views before launching Dashboard
                                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(dashboard);

                                // Close Login Screen
                                finish();
                            }else{
                                // Error in login
                                loginErrorMsg.setText("Incorrect username/password");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Log.v("application", " is Not online");
                    Toast.makeText(ctx," Unable to login because internet is unavailable.", Toast.LENGTH_LONG).show();
                }

            }
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    
    
    @Override
    protected void onPause() {
     // TODO Auto-generated method stub
     super.onPause();
    // image.clearAnimation();
     inputEmail.clearAnimation();
     inputPassword.clearAnimation();
     btnLogin.clearAnimation();
     btnLinkToRegister.clearAnimation();
    }



    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}