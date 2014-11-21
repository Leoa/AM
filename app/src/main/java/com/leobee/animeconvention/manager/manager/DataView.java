package com.leobee.animeconvention.manager.manager;

import com.leobee.animeconvention.manager.R;
import com.leobee.animeconvention.manager.library.JSONParser;
import com.leobee.animeconvention.manager.library.UserFunctions;
import com.leobee.animeconvention.manager.library.Utils;
import com.leobee.animeconvention.manager.loging.LoginActivity;
import com.leobee.animeconvention.manager.utilities.ConvertStdTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class DataView extends Activity implements OnItemClickListener {

	private static final String rssFeed = "http://txtease.com/android/push/login/schedule.php";
	private static final String AddEvent = "http://txtease.com/android/push/login/deleteUpdate.php";

	private static final String ARRAY_NAME = "events";
	private static final String EVENT_NAME = "event_name";
	private static final String EVENT_ID = "event_id";
	private static final String EVENT_DATE = "event_date";
	private static final String EVENT_START = "event_start";
	private static final String EVENT_END = "event_end";
	private static final String EVENT_LOCATION = "event_location";
	private static final String EVENT_DELETE_FLAG = "event_delete_flag";

	List<Item> arrayOfList;
	List<String> sortOfList;
	SimpleAdapter simpleadapter;

	ListView listView;
	NewsRowAdapter objAdapter;
	List<Integer> deleteList;
	String undelete;
	String eventNameDeleted;
	String eventIdDeleted;
	String eventDateDeleted;
	String eventStartDeleted;
	String eventEndDeleted;
	String eventLocationDeleted;
	JSONObject json;
	String addBack = "0";
	Button btnMessage;
	Button btnLogout;
	Button btnChart;
	UserFunctions userFunctions;
	EditText searchBox;
	int textlength = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedulelistview);

		userFunctions = new UserFunctions();
		if (userFunctions.isUserLoggedIn(getApplicationContext())) {

			listView = (ListView) findViewById(R.id.listview);
			listView.isTextFilterEnabled();
			listView.setOnItemClickListener(this);
			deleteList = new ArrayList<Integer>();
			btnLogout = (Button) findViewById(R.id.btnLogout);
			btnMessage = (Button) findViewById(R.id.btnMessage);
			btnChart = (Button) findViewById(R.id.btnChart);
			searchBox = (EditText) findViewById(R.id.EditText01);
			if (searchBox.toString().equals("")) {
				objAdapter.notifyDataSetChanged();

			}

			listView.setTextFilterEnabled(true);
			btnMessage.setOnClickListener(new View.OnClickListener() {

				public void onClick(View arg0) {

					Intent login = new Intent(getApplicationContext(),
							SendMessageToAll.class);
					login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(login);
					finish();

				}
			});
			
			btnChart.setOnClickListener(new View.OnClickListener() {

				public void onClick(View arg0) {
				
					
					Intent login = new Intent(DataView.this,
							Chart.class);
					login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(login);
					finish();
				}
			});

			btnLogout.setOnClickListener(new View.OnClickListener() {

				public void onClick(View arg0) {
				
					userFunctions.logoutUser(getApplicationContext());
					Intent login = new Intent(getApplicationContext(),
							LoginActivity.class);
					login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(login);
					finish();
				}
			});

			arrayOfList = new ArrayList<Item>();

			if (Utils.isNetworkAvailable(DataView.this)) {
				new MyTask().execute(rssFeed);
			} else {
				showToast("No Network Connection. Application is unable to load data.");
			}

		} else {

			// user is not logged in show login screen
			Intent login = new Intent(getApplicationContext(),
					LoginActivity.class);
			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			finish();

		}

		searchBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// Do nothing
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Do nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				System.out.println(" ONTEXTCHANGED... " + s);
				System.out.println(" Lenght... " + s.length());
				if (s.length() > 0)
				{
					objAdapter.getFilter().filter(s);

				}
				if (
					searchBox.getText().toString().length()==0) {
					listView.clearTextFilter();
//					objAdapter.notifyDataSetChanged();
//					objAdapter = new NewsRowAdapter(DataView.this,
//							R.layout.schedulelistrow, arrayOfList, deleteList);
//					listView.setAdapter(objAdapter);
					
					reload();
					

				}

			}

		});

	}

	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(DataView.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return Utils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("No Network Connection. Application is unable to load data.");
				DataView.this.finish();
			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(ARRAY_NAME);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject objJson = jsonArray.getJSONObject(i);

						Item objItem = new Item();

						// here  objItem is reciveing and processing data from
						// JSON string text
						objItem.setId(objJson.getInt(EVENT_ID));// names from
																// feed is in
																// static vars
																// at top
						objItem.setName(objJson.getString(EVENT_NAME));
						objItem.setDate(objJson.getString(EVENT_DATE));
						objItem.setStartTime(objJson.getString(EVENT_START));
						objItem.setEndTime(objJson.getString(EVENT_END));
						objItem.setLocation(objJson.getString(EVENT_LOCATION));
						objItem.setDeleteFlag(objJson.getInt(EVENT_DELETE_FLAG));

						arrayOfList.add(objItem);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				for (int i = 0; i < arrayOfList.size(); i++) {

					Item deletflagCheck = arrayOfList.get(i);

					if (deletflagCheck.getDeleteFlag() == 1) {

						deleteList.add(deletflagCheck.getId());
					} else {

					}

				}

			}
			setAdapterToListview();

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View viewDel, int position,
			long id) {

		Item deletflagCheck = arrayOfList.get(position);

		if (deletflagCheck.getDeleteFlag() == 1) {

			showDeleteDialog(position);

		} else {

			Intent intent = new Intent(DataView.this.getApplicationContext(),
					UpdateServer.class);

			for (int i = 0; i < 1; i++) {
				Item item = arrayOfList.get(position);
				intent.putExtra("id", item.getId());
				intent.putExtra("name", item.getName());
				intent.putExtra("date", item.getDate());
				intent.putExtra("startTime", item.getStartTime());
				intent.putExtra("endTime", item.getEndTime());
				intent.putExtra("location", item.getLocation());
				intent.putExtra("deleteFlag", item.getDeleteFlag());

			}
			startActivity(intent);
		}

	}

	private void showDeleteDialog(final int position) {
		Item item = arrayOfList.get(position);
		eventNameDeleted = item.getName();
		// eventIdDeleted=item.getId();
		eventDateDeleted = item.getDate();
		eventStartDeleted = item.getStartTime();
		eventEndDeleted = item.getEndTime();
		eventLocationDeleted = item.getLocation();

		AlertDialog alertDialog = new AlertDialog.Builder(DataView.this)
				.create();
		alertDialog.setTitle(eventNameDeleted);
		alertDialog.setMessage("Add Event Back To Schedule?");
		alertDialog.setButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.setButton2("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				undelete = Integer.toString(position);
				// objAdapter.notifyDataSetChanged();
				addBack = "1";
				System.out.println("addBack is " + addBack);
				if (Utils.isNetworkAvailable(DataView.this)) {
					new AddEventTask().execute(AddEvent);

				} else {
					showToast("No Network Connection!!!");
				}

			}
		});
		alertDialog.show();

	}

	public void setAdapterToListview() {

		objAdapter = new NewsRowAdapter(DataView.this,
				R.layout.schedulelistrow, arrayOfList, deleteList);

		objAdapter.notifyDataSetChanged();
		listView.setAdapter(objAdapter);
		objAdapter.notifyDataSetChanged();
	}

	public void showToast(String msg) {
		Toast.makeText(DataView.this, msg, Toast.LENGTH_LONG).show();
	}

	class AddEventTask extends AsyncTask<String, Void, JSONObject> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(DataView.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected JSONObject doInBackground(String... params) {

			String zero = "0";

			ConvertStdTime shCST = new ConvertStdTime();
			String startHour = shCST.toMilitaryString(eventStartDeleted);
			String endHour = shCST.toMilitaryString(eventEndDeleted);

			JSONParser jsonParser = new JSONParser();
			List<NameValuePair> addBacktoSchedule = new ArrayList<NameValuePair>();
			addBacktoSchedule.add(new BasicNameValuePair("id", undelete));
			addBacktoSchedule.add(new BasicNameValuePair("deleteEvent", zero));
			addBacktoSchedule.add(new BasicNameValuePair("name",
					eventNameDeleted));
			addBacktoSchedule.add(new BasicNameValuePair("date",
					eventDateDeleted));
			addBacktoSchedule
					.add(new BasicNameValuePair("startTime", startHour));
			addBacktoSchedule.add(new BasicNameValuePair("endTime", endHour));
			addBacktoSchedule.add(new BasicNameValuePair("location",
					eventLocationDeleted));
			addBacktoSchedule.add(new BasicNameValuePair("addBack", addBack));
			String paramx = "id=" + undelete + "&deleteEvent=" + zero
					+ " &addback= " + addBack;
			System.out.println(paramx);
			JSONObject json = jsonParser.getJSONFromUrl(AddEvent,
					addBacktoSchedule);

			Log.v("JSON  in DataView", json.toString());
			// String x = null;
			// return json;
			return json;

		}

		@SuppressWarnings("unused")
		@Override
		protected void onPostExecute(JSONObject result) {


			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("No Network Connection. Application is unable to load data.");
				DataView.this.finish();
			} else {

				try {

					JSONObject json = result;

					String message = json.getString("msg");
					Toast.makeText(DataView.this, message, Toast.LENGTH_LONG)
							.show();

					int dialog = 0;
					
					dialog = json.getInt("dialog");

					if (dialog == 1) {
						System.out.println("dialog is 1");

						String time_conflicts = json
								.getString("time_conflicts");
						String deleted_event = json
								.getString("the_deleted_event");

					 deletedEvent(time_conflicts, deleted_event);

					}

					if (dialog !=1) {
		
						Toast.makeText(DataView.this,
								eventNameDeleted + " added back to schedule.",
								Toast.LENGTH_LONG).show();
			
						NewsRowAdapter nra=null;
						nra.notifyDataSetChanged();
						
						reload();

					}
					// Toast.makeText(DataView.this,eventNameDeleted+" "+
					// message, Toast.LENGTH_LONG).show();

					// DataView.this.finish();
				} catch (JSONException e) {
					e.printStackTrace();
					reload();
					
					
					
				}

			}

			//

		}
	}

	public void deletedEvent(final String time_conflicts,
			final String deleted_event) {

		AlertDialog alertDialog = new AlertDialog.Builder(DataView.this)
				.create();
		alertDialog.setTitle("Fix time conflict with " + eventNameDeleted
				+ " ?");
		alertDialog.setButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alertDialog.setButton2("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// Toast.makeText(DataView.this,
				// eventNameDeleted+" has time conflict.",
				// Toast.LENGTH_LONG).show();

				Intent intent = new Intent(DataView.this
						.getApplicationContext(), ResolveTimeConflict2.class);
				intent.putExtra("time_conflicts", time_conflicts);
				intent.putExtra("deleted_event", deleted_event);

				startActivity(intent);
				// ********objAdapter.clear(); Crash when clear is
				// here**************************

			}
		});
		alertDialog.show();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// new AddEventTask().execute(AddEvent);
		// ilterText.removeTextChangedListener(filterTextWatcher);

	}
	
	
    public void reload() {
System.out.println("reloaded");
   
Intent intent = new Intent(DataView.this, DataView.class ); 

//Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(getApplicationContext().getPackageName()); 
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    finish();
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
    startActivity(intent);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case R.id.options_about:
        startActivity(new Intent(this, About.class));
        return true;
        case R.id.options_tutorial:
        startActivity(new Intent(this, Tutorial.class));
        return true;
        default:
        return super.onOptionsItemSelected(item);
    }
    }
}