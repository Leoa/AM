package com.leobee.animeconvention.manager.manager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.leobee.animeconvention.manager.R;

public class Tutorial extends Activity{
	
	String SrcPath = "rtsp://v4.cache3.c.youtube.com/CjYLENy73wIaLQnpqU3Z3Sg-IxMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYKLC1by0pcriTww=/0/0/0/video.3gp";
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial);
		
		VideoView myVideoView = (VideoView)findViewById(R.id.myvideoview);
	       myVideoView.setVideoURI(Uri.parse(SrcPath));
	       myVideoView.setMediaController(new MediaController(this));
	       myVideoView.requestFocus();
	       myVideoView.start();
	
		
	
		
		Button button2 = (Button) findViewById(R.id.btnBack);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Tutorial.this
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


