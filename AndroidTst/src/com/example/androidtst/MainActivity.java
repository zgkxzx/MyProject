package com.example.androidtst;

import com.zgkxzx.myfile.FileService;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	private final String TAG = "MainActivity";
	private FileService service;
	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 service = new FileService(this);
		 
	
		 
		
		String s = null;
		try {
			s = service.getContent("zgkxzx.txt");
			
			tv.setText(s);
			
			String [] temp = s.split(":");
			
			for(String ss:temp)
			{
				System.out.println(ss);
				Log.d(TAG, ss);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("´ò¿ªÊ§°Ü");
		}
		
		
	
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}

	
}
