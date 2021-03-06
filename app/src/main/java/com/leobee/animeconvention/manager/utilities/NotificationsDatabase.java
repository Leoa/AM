package com.leobee.animeconvention.manager.utilities;


import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;


public class NotificationsDatabase {
	
	private static final String DATABASE_NAME = "NotificationDataBase";
	private static final String DATABASE_TABLE = "NotificationTable";
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_ROWID = "_id";
	private static final String KEY_NOTE = "notfication";
	
	private static final int DATABASE_VERSION = 1;

	public DBHelper ourHelper;
	public final Context ourContext;
	private SQLiteDatabase ourDatabase;
	private Cursor cursor;
	

	ArrayList<String> alertList = null;
	List<Notes> msgOfList;
	ListView listView;
	NoteAdapter objAdapter;	
	
	
	public int getHighestID() {
		final String MY_QUERY = "SELECT MAX(_id) FROM " + DATABASE_TABLE;
		Cursor cur = ourDatabase.rawQuery(MY_QUERY, null);
		cur.moveToFirst();
		int ID = cur.getInt(0);
		cur.close();
		return ID;
		}   
	
	
	public String getMessage(long lRow){
		
		
		
		String msg="";
		
		try {

			String[] columns = new String[] { KEY_ROWID,
					KEY_NOTE, KEY_TIMESTAMP};
			// information from a database is read thru a cursor

			cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
					null, null);
			
			int iNote = cursor.getColumnIndex(KEY_NOTE);
						
			if (cursor != null) {

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					
					msg=cursor.getString(iNote);				

				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			msgOfList = null;

		} finally {

			if (cursor != null && !cursor.isClosed()) {

				cursor.close();

			}

		}

		return msg;
	}
	
	
	
	
	public List<Notes> getData() {

		
		msgOfList = new ArrayList<Notes>();

		try {

			String[] columns = new String[] { KEY_ROWID,
					KEY_NOTE, KEY_TIMESTAMP};
			// information from a database is read thru a cursor

			cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
					null, null);

			int iRow = cursor.getColumnIndex(KEY_ROWID);
			int iNote = cursor.getColumnIndex(KEY_NOTE);
			int iTimeStamp = cursor.getColumnIndex(KEY_TIMESTAMP);
			

			if (cursor != null) {

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {

					Notes notesObj = new Notes();

					notesObj.setId(cursor.getInt(iRow));
					notesObj.setNote(cursor.getString(iNote));
					notesObj.setTimeStamp(cursor.getString(iTimeStamp));
					
					
					msgOfList.add(notesObj);

				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			msgOfList = null;

		} finally {

			if (cursor != null && !cursor.isClosed()) {

				cursor.close();

			}

		}

		return msgOfList;
	}
	
	public  NotificationsDatabase (Context c) {
		ourContext = c;
	}
	
	public synchronized NotificationsDatabase open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		System.out.println("database opened " + ourDatabase);
		return this;
	}
	
	public synchronized void close() {
		try {
			if (ourDatabase != null && ourDatabase.isOpen()) {
				ourHelper.close();
				System.out.println("database closed ");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	public long createEntry( String notification, String timeStamp) {

		ContentValues cv = new ContentValues();
		cv.put(KEY_NOTE, notification);
		cv.put(KEY_TIMESTAMP, timeStamp);
		
		return ourDatabase.insert(DATABASE_TABLE, null, cv);

	}
	
	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" 
					+ KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_TIMESTAMP + " TEXT, " 
					+ KEY_NOTE + " TEXT );"

			);
		
		
	}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
			System.out.println("DROP TABLE IF EXISTS ");
		}
		
	}// end DB Helper
	
	

}
