package com.zgkxzx.activity;



import java.io.IOException;
import java.util.List;

import com.zgkxzx.activity.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;



public class LayerListView extends Activity {
	
	
	
	
	
	private static String[] ListItem = new String[]{
		"第1层",
		"第2层",
		"第3层",
		"第4层",
		"第5层",
		"第6层",
		"第7层",
		"第8层",
		"第9层",
		"第10层",
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lvcommonlistview);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
		
		this.setTitle("楼层查看");
		ArrayAdapter<String> arrayAdapt = new ArrayAdapter<String>(this,R.layout.list_com,ListItem);
		
		
		ListView myList = (ListView)findViewById(R.id.lvCommon);
		
	
		myList.setAdapter(arrayAdapt);
		//ListView 的事件处理
		myList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(LayerListView.this, DeviceView.class);
				intent.putExtra("Layer", String.valueOf(position+1));
				startActivity(intent);
				
				overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			}
		
		
	   });
		
		 
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(LayerListView.this, MyActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			return false;
		}
		return false;
	}
	
	
}
