package com.zgkxzx.activity;



import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;


import com.zgkxzx.activity.R;
import com.zgkxzx.infosend.ControlSend;
import com.zgkxzx.mymath.CRC16;
import com.zgkxzx.server.DataProcessServer;

import com.zgkxzx.sth.DevSqlSevice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import android_serialport_api.MyApplication;
import android_serialport_api.SerialPort;

public class DeviceControl extends Activity implements OnClickListener {
	
	private final String TAG = "DeviceControl";
	
	private MyApplication mApplication;
	private SerialPort mSerialPort;
	private OutputStream mOutputStream;
	
	private ImageView ivControlItem[] = new ImageView[7];
	private boolean itemStatus[]  = new boolean[7];
	
	private static final int controlItemIdRes[]={
		R.id.ctl_rolldoor,
		R.id.ctl_fireplug,
		R.id.ctl_fun,
		R.id.ctl_power,
		R.id.ctl_lift,
		R.id.ctl_broast,
		R.id.ctl_motordoor,
	};
	
	private String nodeLayer;
	private String nodeNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏
		this.setContentView(R.layout.activity_control);
				 
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
	    
	    
	    Bundle extras = getIntent().getExtras();
	    nodeLayer = extras.getString("Layer");
	    nodeNumber = extras.getString("NodeNumber");
	    Log.d(TAG, nodeNumber);
	    
	    mApplication = (MyApplication) getApplication();
		try {
			mSerialPort = mApplication.getSerialPort();
			mOutputStream = mSerialPort.getOutputStream();
		
		} catch (SecurityException e) {
			Log.d(TAG, e.toString());
		} catch (IOException e) {
			Log.d(TAG, e.toString());
		} catch (InvalidParameterException e) {
			Log.d(TAG, e.toString());
		}
	    
		for(int i =0;i< 7;i++)
		{
			ivControlItem[i] = (ImageView)this.findViewById(controlItemIdRes[i]);
			ivControlItem[i].setOnClickListener(this);
			itemStatus[i] = false;
		}
		
	    
	    
		
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		/*
		SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_control", Activity.MODE_PRIVATE);
		item1stutas = sharedSettings.getBoolean("control01", false);	
    	if(!item1stutas)				
    		ivControlItem1.setImageResource(R.drawable.switch_on_normal);
		else
			ivControlItem1.setImageResource(R.drawable.switch_off_normal);*/
    
	}





	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		/*SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_control", Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedSettings.edit();
    	
    	editor.putBoolean("control01", item1stutas);
    	editor.commit();*/
		
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
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Log.d(TAG, Integer.toString(v.getId()));
		int deviceNum=0;
		switch (v.getId()) 
		{
			case R.id.ctl_rolldoor:
				deviceNum=1;
				break;
			case R.id.ctl_fireplug:
				deviceNum=2;
				break;
			case R.id.ctl_fun:
				deviceNum=3;
				break;
			case R.id.ctl_power:
				deviceNum=4;
				break;
			case R.id.ctl_lift:
				deviceNum=5;
				break;
			case R.id.ctl_broast:
				deviceNum=6;
				break;
			case R.id.ctl_motordoor:
				deviceNum=7;
				break;
			default:break;
		}
		if(deviceNum!=0)
		{
			if(itemStatus[deviceNum-1])
			{
				ivControlItem[deviceNum-1].setImageResource(R.drawable.switch_on_normal);
				
				itemStatus[deviceNum-1] = false;
				Log.d(TAG,"设备开");
			}else
			{
				ivControlItem[deviceNum-1].setImageResource(R.drawable.switch_off_normal);
				
				itemStatus[deviceNum-1] = true;
				Log.d(TAG,"设备关");
			}
			
			byte [] sendData = ControlSend.sendControl(nodeLayer,nodeNumber,ControlSend.FIREPLUG, ControlSend.getSW(itemStatus[deviceNum-1]));
			try {
				mOutputStream.write(sendData, 0, sendData.length);
				Log.d(TAG,"发送成功");
			} catch (IOException e) 
			{
				e.printStackTrace();
				Log.d(TAG,"发送失败");
			}
		}
		
		
	}
}
