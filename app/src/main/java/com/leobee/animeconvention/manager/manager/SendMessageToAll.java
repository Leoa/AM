package com.leobee.animeconvention.manager.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.leobee.animeconvention.manager.R;
import com.leobee.animeconvention.manager.library.JSONParser;
import com.leobee.animeconvention.manager.library.Utils;
import com.leobee.animeconvention.manager.loging.LoginActivity;
import com.leobee.animeconvention.manager.manager.DataView.MyTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageToAll extends Activity implements OnClickListener {

	private static final String SENDMSG = "http://www.txtease.com/android/push/login/gcm/msg.php";

	private static final String ARRAY_NAME = "message";

	JSONObject json;
	EditText txtAdmin;
	Button btnSend;
	Button btnBack;
	String sendTxt;
	int success;
	InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendmessagetoall);

		txtAdmin = (EditText) findViewById(R.id.txtAdmin);
		btnSend = (Button) findViewById(R.id.btnSend);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		
//		getWindow().setSoftInputMode(
//			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//	
//		
//		 imm = (InputMethodManager) getSystemService(SendMessageToAll.this.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(txtAdmin.getWindowToken(), 0);
//
//		 imm = (InputMethodManager) getSystemService(SendMessageToAll.this.INPUT_METHOD_SERVICE);
//		imm.showSoftInput(txtAdmin, InputMethodManager.SHOW_IMPLICIT);

		

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

		
		sendTxt = txtAdmin.getText().toString();

		if (sendTxt.equals("")) {
		} else {

			if (v.getId() == R.id.btnSend) {

				sendTxt = txtAdmin.getText().toString();
				System.out.println(sendTxt);

				if (Utils.isNetworkAvailable(SendMessageToAll.this)) {
					new MyTask().execute(SENDMSG);
				} else {
					Toast.makeText(
							SendMessageToAll.this,
							"No Network Connection. Application is unable to load data.",
							Toast.LENGTH_LONG).show();
				}
			}
			
			
			
			
		}
		
		
		if (v.getId() == R.id.btnBack) {
			
			System.out.println("clicked");
			Intent intent = new Intent(SendMessageToAll.this
					.getApplicationContext(), DataView.class);
			startActivity(intent);
			
			
		}
	}

	class MyTask extends AsyncTask<String, Void, JSONObject> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SendMessageToAll.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected JSONObject doInBackground(String... params) {
	
			
			
			
			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> paramsx = new ArrayList<NameValuePair>();

			paramsx.add(new BasicNameValuePair("message", sendTxt));

			json = jsonParser.getJSONFromUrl(SENDMSG, paramsx);

			Log.v("JSON", json.toString());
			// String x = null;
			return json;

		}

		@Override
		protected void onPostExecute(JSONObject result) {
		
			String message;
			
			
			if (null != pDialog && pDialog.isShowing()) {

				pDialog.dismiss();
			}
			
			System.out.println("MSG is ....." + result);
			
			JSONObject mainJson = result;
			
			 try {
				
				
				 message = mainJson.getString("msg");
				 Toast.makeText(SendMessageToAll.this, "Message: \" " + message + " \" was send to attendees", Toast.LENGTH_LONG).show();
					
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 // add a list object here
			
		 try {
			success=mainJson.getInt("success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("success is "+success);
			 
			
		
			 
			pDialog.dismiss();
			
			if(success>0){
				
				
			}
			Intent intent = new Intent(
					SendMessageToAll.this.getApplicationContext(),
					DataView.class);

			startActivity(intent);
			 }// end postend 
		
	

}// async task
}
