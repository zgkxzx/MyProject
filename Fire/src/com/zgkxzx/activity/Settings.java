package com.zgkxzx.activity;

import com.zgkxzx.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Settings extends Activity {
	
	
	private ImageView ivSettingsItem1;
	private boolean settings1status = false;
	
	private ImageView ivSettingsItem2;
	private boolean settings2status = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏
		this.setContentView(R.layout.activity_settings);
				 
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
	    
	    
	    	
	    
	    
		
	    ivSettingsItem1 = (ImageView)this.findViewById(R.id.settings01);
	    ivSettingsItem1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(settings1status)
				{
					Toast.makeText(Settings.this, "开", 1000).show();
					ivSettingsItem1.setImageResource(R.drawable.switch_on_normal);
					settings1status = false;
					
				}else
				{
					Toast.makeText(Settings.this, "关", 1000).show();
					ivSettingsItem1.setImageResource(R.drawable.switch_off_normal);
					settings1status = true;
				}
				
				
			}
		
	    	
	    	
	    	
	    }); 
	    
	    ivSettingsItem2 = (ImageView)this.findViewById(R.id.settings02);
	    ivSettingsItem2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(settings2status)
				{
					Toast.makeText(Settings.this, "开", 1000).show();
					ivSettingsItem2.setImageResource(R.drawable.switch_on_normal);
					settings2status = false;
					
				}else
				{
					Toast.makeText(Settings.this, "关", 1000).show();
					ivSettingsItem2.setImageResource(R.drawable.switch_off_normal);
					settings2status = true;
				}
				
				
			}
		
	    	
	    	
	    	
	    }); 
	    
	    
	}
	
	
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_settings", Activity.MODE_PRIVATE);
		settings1status = sharedSettings.getBoolean("setting01", false);	
    	if(!settings1status)				
			ivSettingsItem1.setImageResource(R.drawable.switch_on_normal);
		else
			ivSettingsItem1.setImageResource(R.drawable.switch_off_normal);
    	
    	settings2status = sharedSettings.getBoolean("setting02", false);	
    	if(!settings2status)				
			ivSettingsItem2.setImageResource(R.drawable.switch_on_normal);
		else
			ivSettingsItem2.setImageResource(R.drawable.switch_off_normal);
	}





	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_settings", Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedSettings.edit();
    	
    	editor.putBoolean("setting01", settings1status);
    	editor.putBoolean("setting02", settings2status);
    	editor.commit();
	}





	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(Settings.this, MyActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			return false;
		}
		return false;
	}
}
