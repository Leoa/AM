package com.leobee.animeconvention.manager.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.leobee.animeconvention.manager.R;

public class About extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		TextView tv =(TextView)findViewById(R.id.aboutApp);
		tv.setText("Anime Convention Manager \n\nAnime Convention Manager assist convention administrators in managing last minute and live changes to the convention schedule. Also this application communicates these changes in real-time to members of the convention.\n \n\u00A9  2012-2013 Copyright \n\nSugar Defynery 2012-2013");
		
	
	// go back  to list
	Button button2 = (Button) findViewById(R.id.btnBack);
	button2.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(About.this
					.getApplicationContext(), DataView.class);
			startActivity(intent);
		}
	});
}
}

