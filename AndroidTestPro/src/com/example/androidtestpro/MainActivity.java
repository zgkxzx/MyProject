package com.example.androidtestpro;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		Toast.makeText(this, "ListViewDemo", Toast.LENGTH_LONG).show();
		startActivity(new Intent(MainActivity.this,ListViewDemo.class));
	}

	
	
}
