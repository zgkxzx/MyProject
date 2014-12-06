package com.zgkxzx.activity;



import com.zgkxzx.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class DeviceControl extends Activity {
	
	
	private ImageView ivControlItem1;
	private boolean item1stutas = true;
	
	private ImageView ivControlItem2;
	private boolean item2stutas = true;
	
	private ImageView ivControlItem3;
	private boolean item3stutas = true;
	
	private ImageView ivControlItem4;
	private boolean item4stutas = true;
	
	
	private ImageView ivControlItem5;
	private boolean item5stutas = true;
	
	
	private ImageView ivControlItem6;
	private boolean item6stutas = true;
	
	
	private ImageView ivControlItem7;
	private boolean item7stutas = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏
		this.setContentView(R.layout.activity_control);
				 
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
	    
	    ivControlItem1 = (ImageView)this.findViewById(R.id.settings01);
	    ivControlItem1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(item1stutas)
				{
					
					ivControlItem1.setImageResource(R.drawable.switch_on_normal);
					item1stutas = false;
					Toast.makeText(DeviceControl.this, "设备1开", 500).show();
				}else
				{
					
					ivControlItem1.setImageResource(R.drawable.switch_off_normal);
					item1stutas = true;
					Toast.makeText(DeviceControl.this, "设备1关", 500).show();
				}
				
			}
	    	
	    	
	    	
	    });
	    
	    
	    ivControlItem2 = (ImageView)this.findViewById(R.id.settings02);
	    ivControlItem2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(item2stutas)
				{
					ivControlItem2.setImageResource(R.drawable.switch_on_normal);
					item2stutas = false;
					Toast.makeText(DeviceControl.this, "设备2开", 500).show();
				}else
				{
					
					ivControlItem2.setImageResource(R.drawable.switch_off_normal);
					item2stutas = true;
					Toast.makeText(DeviceControl.this, "设备2关", 500).show();
				}
				
			}
	    	
	    	
	    	
	    });
	    
	    
	    ivControlItem3 = (ImageView)this.findViewById(R.id.settings03);
	    ivControlItem3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(item3stutas)
				{
					ivControlItem3.setImageResource(R.drawable.switch_on_normal);
					item3stutas = false;
				}else
				{
					
					ivControlItem3.setImageResource(R.drawable.switch_off_normal);
					item3stutas = true;
				}
				
			}
	    	
	    	
	    	
	    });
	    
	    
	    ivControlItem4 = (ImageView)this.findViewById(R.id.settings04);
	    ivControlItem4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(item4stutas)
				{
					ivControlItem4.setImageResource(R.drawable.switch_on_normal);
					item4stutas = false;
				}else
				{
					
					ivControlItem4.setImageResource(R.drawable.switch_off_normal);
					item4stutas = true;
				}
				
			}
	    	
	    	
	    	
	    });
	    
	    
	    ivControlItem5 = (ImageView)this.findViewById(R.id.settings05);
	    ivControlItem5.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(item5stutas)
				{
					ivControlItem5.setImageResource(R.drawable.switch_on_normal);
					item5stutas = false;
				}else
				{
					
					ivControlItem5.setImageResource(R.drawable.switch_off_normal);
					item5stutas = true;
				}
				
			}
	    	
	    	
	    	
	    });
	    
	    
	    ivControlItem6 = (ImageView)this.findViewById(R.id.settings06);
	    ivControlItem6.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(item6stutas)
				{
					ivControlItem6.setImageResource(R.drawable.switch_on_normal);
					item6stutas = false;
				}else
				{
					
					ivControlItem6.setImageResource(R.drawable.switch_off_normal);
					item6stutas = true;
				}
				
			}
	    	
	    	
	    	
	    });
	    
	    
	    ivControlItem7 = (ImageView)this.findViewById(R.id.settings07);
	    ivControlItem7.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(item7stutas)
				{
					ivControlItem7.setImageResource(R.drawable.switch_on_normal);
					item7stutas = false;
				}else
				{
					
					ivControlItem7.setImageResource(R.drawable.switch_off_normal);
					item7stutas = true;
				}
				
			}
	    	
	    	
	    	
	    });
	    
		
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_control", Activity.MODE_PRIVATE);
		item1stutas = sharedSettings.getBoolean("control01", false);	
    	if(!item1stutas)				
    		ivControlItem1.setImageResource(R.drawable.switch_on_normal);
		else
			ivControlItem1.setImageResource(R.drawable.switch_off_normal);
    	
    	item2stutas = sharedSettings.getBoolean("control02", false);	
    	if(!item2stutas)				
    		ivControlItem2.setImageResource(R.drawable.switch_on_normal);
		else
			ivControlItem2.setImageResource(R.drawable.switch_off_normal);
    	item3stutas = sharedSettings.getBoolean("control03", false);	
    	if(!item3stutas)				
    		ivControlItem3.setImageResource(R.drawable.switch_on_normal);
		else
			ivControlItem3.setImageResource(R.drawable.switch_off_normal);
	}





	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_control", Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedSettings.edit();
    	
    	editor.putBoolean("control01", item1stutas);
    	editor.putBoolean("control02", item2stutas);
    	editor.putBoolean("control03", item3stutas);
    	editor.putBoolean("control04", item4stutas);
    	editor.putBoolean("control05", item5stutas);
    	editor.putBoolean("control06", item6stutas);
    
    	editor.commit();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(DeviceControl.this, MyActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			return false;
		}
		return false;
	}
}
