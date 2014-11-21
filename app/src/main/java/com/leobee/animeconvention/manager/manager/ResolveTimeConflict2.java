package com.leobee.animeconvention.manager.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leobee.animeconvention.manager.R;
import com.leobee.animeconvention.manager.utilities.AppStatus;



public class ResolveTimeConflict2 extends Activity  {
	


	String errorEventId;
	String errorEventName;
	String errorEventDate;
	String errorEventStart;
	String errorEventEnd;
	String errorEventLocation;
	String errorEventDelete;
	
	String conflictEventId;
	String conflictEventName;
	String conflictEventDate;
	String conflictEventStart;
	String conflictEventEnd;
	String conflictEventLocation;
	TextView NameDisplay;
	TextView DateDisplay;
	TextView StartTimeDisplay;
	TextView EndTimeDisplay;
	TextView LocationDisplay;
	Button btnUpdateSchedule;
	Button btnSeeSchedule;
	 TextView tv2 ;

	 private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.resolvetimeconflict);
		
		Bundle bundle = getIntent().getExtras();
		String time_conflicts = bundle.getString("time_conflicts");
		String deleted_event = bundle.getString("deleted_event");
		System.out.println(deleted_event + "  " + time_conflicts);
		
		NameDisplay = (TextView)findViewById(R.id.NameDisplay);
		DateDisplay= (TextView)findViewById(R.id.DateDisplay);
		StartTimeDisplay = (TextView)findViewById(R.id.StartTimeDisplay);
		EndTimeDisplay = (TextView)findViewById(R.id.EndTimeDisplay);
		LocationDisplay= (TextView)findViewById(R.id.LocationDisplay);
		 tv2 = (TextView)findViewById(R.id.tv2);
		btnUpdateSchedule= (Button)findViewById(R.id.btnUpdateSchedule);
		
		
		webView = (WebView) findViewById(R.id.webView1);
		

		if (AppStatus.getInstance(ResolveTimeConflict2.this).isOnline(ResolveTimeConflict2.this)) {			

		webView = (WebView) findViewById(R.id.webView1);		
		
		webView.getSettings().setJavaScriptEnabled(true);
				
		webView.getSettings().setBuiltInZoomControls(true);
		// simply, just load an image						
		
		webView.getSettings().setAppCacheEnabled(false);
		
		webView.setInitialScale(70);
		
		final Activity activity = this;
		webView.setWebChromeClient(new WebChromeClient() {
		   public void onProgressChanged(WebView view, int progress) {
		     // Activities and WebViews measure progress with different scales.
		     // The progress meter will automatically disappear when we reach 100%
		     activity.setProgress(progress * 1000);
		   }
		 });
		webView.setWebViewClient(new WebViewClient() {
		   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		     Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
		   }
		 });
		
		webView.loadUrl("http://txtease.com/android/push/so/ganttChart/chart2.php");
 
		

		} else {
			
			
			Toast.makeText(
					this,
					"Internet connection is unavaiable. Application is unable to get data.",
					8000).show();
			Log.v("Home", "Internet connection is unavaiable");
			

		  	}

		
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(deleted_event);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				errorEventId = jsonObject.getString("event_id");
				errorEventName = jsonObject.getString("event_name");
				errorEventDate = jsonObject.getString("event_date");
				errorEventStart = jsonObject.getString("event_start");
				errorEventEnd = jsonObject.getString("event_end");
				errorEventLocation = jsonObject.getString("event_location");
				errorEventDelete= jsonObject.getString("event_delete_flag");

				NameDisplay.setText(errorEventName);
				DateDisplay.setText(errorEventDate +" Location: "+errorEventLocation);
				StartTimeDisplay.setText(errorEventStart+"  To: "+errorEventEnd);				
				
			        

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}// end of Deleted event Display text
		
		// scroll of text for conflicting eventsl
		
		
		try {
			JSONArray jsonArray2 = new JSONArray(time_conflicts);
			

				for (int i = 0; i < jsonArray2.length(); i++) {
					JSONObject jsonObject = jsonArray2.getJSONObject(i);

					conflictEventId = jsonObject.getString("event_id");
					conflictEventName = jsonObject.getString("event_name");
					conflictEventDate = jsonObject.getString("event_date");
					conflictEventStart = jsonObject.getString("event_start");
					conflictEventEnd = jsonObject.getString("event_end");
					conflictEventLocation = jsonObject.getString("event_location");
					
					System.out.println(conflictEventName+" \n Date: "+ conflictEventDate+" \n Times: "+ conflictEventStart+" to "+ conflictEventEnd+" \n Location: "+ conflictEventLocation);

				        tv2.append(conflictEventName+" \n Date: "+ conflictEventDate+" \n Start Time: "+ conflictEventStart+" to "+ conflictEventEnd+" \n Location: "+ conflictEventLocation+" \n");
				        tv2.append(" \n");
				        }
				        

			} catch (JSONException e) {
				e.printStackTrace();
			}

		

		btnUpdateSchedule.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent = new Intent(
						ResolveTimeConflict2.this.getApplicationContext(),
						UpdateServer.class);
				
				int errorid=Integer.parseInt(errorEventId);
				intent.putExtra("id", errorid);
				intent.putExtra("name", errorEventName);
				intent.putExtra("date", errorEventDate);
				intent.putExtra("startTime", errorEventStart);
				intent.putExtra("endTime", errorEventEnd);
				intent.putExtra("location", errorEventLocation);
				intent.putExtra("deleteFlag", 0);
				
				
				startActivity(intent);
			
			}
		});

		
	}
	
}