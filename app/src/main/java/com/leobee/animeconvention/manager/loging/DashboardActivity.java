package com.leobee.animeconvention.manager.loging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.leobee.animeconvention.manager.R;
import com.leobee.animeconvention.manager.library.UserFunctions;
import com.leobee.animeconvention.manager.manager.DataView;
import com.leobee.animeconvention.manager.manager.SendMessageToAll;
 
public class DashboardActivity extends Activity {
    UserFunctions userFunctions;
    Button btnLogout;
    Button btnSchedule;
    Button btnMessage;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /**
         * Dashboard Screen for the application
         * */
        // Check login status in database
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
       // user already logged in show databoard
            setContentView(R.layout.dashboard);
            btnLogout = (Button) findViewById(R.id.btnLogout);
            btnMessage =(Button)findViewById(R.id.btnMessage);
            btnSchedule =(Button)findViewById(R.id.btnSchedule);
            
            btnMessage.setOnClickListener(new View.OnClickListener() {
          	  
                public void onClick(View arg0) { 
                	
              	  
              	 
              	  Intent login = new Intent(getApplicationContext(),  SendMessageToAll.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    // Closing dashboard screen
                    finish();
                	
                }
            });
            
            btnSchedule.setOnClickListener(new View.OnClickListener() {
            	 
                public void onClick(View arg0) {
                	 Intent manager = new Intent(getApplicationContext(), DataView.class);
                	 manager.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(manager);
                     // Closing dashboard screen
                     finish();
                	
                }
            });
 
            btnLogout.setOnClickListener(new View.OnClickListener() {
 
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    // Closing dashboard screen
                    finish();
                }
            });
 
        }else{
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
        }
    }
}