package com.leobee.animeconvention.manager.manager;

import com.leobee.animeconvention.manager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class Chart extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart);
		
		WebView webView=(WebView)findViewById(R.id.webViewChart);
		Button btnBack =(Button)findViewById(R.id.btnBack);
		
		webView.getSettings().setJavaScriptEnabled(true);
		
		
		webView.getSettings().setBuiltInZoomControls(true);
		// simply, just load an image						
		
		webView.getSettings().setAppCacheEnabled(false);
		
		webView.setInitialScale(70);
		
		webView.loadUrl("http://txtease.com/android/push/so/ganttChart/chart2.php");
		// go back  to list
		Button button2 = (Button) findViewById(R.id.btnBack);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Chart.this
						.getApplicationContext(), DataView.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	
	
	
	

}
