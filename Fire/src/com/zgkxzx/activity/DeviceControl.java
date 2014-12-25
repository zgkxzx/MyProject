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
import com.zgkxzx.universal.Global;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android_serialport_api.MyApplication;
import android_serialport_api.SerialPort;

public class DeviceControl extends Activity implements OnClickListener {
	
	private final String TAG = "DeviceControl";
	
	private MyApplication mApplication;
	private SerialPort mSerialPort;
	private OutputStream mOutputStream;
	
	private Button ivDevicesControl[] = new Button[7];
	private TextView ivDevicesStatus[] = new TextView[7];
	private boolean devicesStatus[]  = new boolean[7];
	
	private final int buttonsId[]={
			R.id.device_button_1,
			R.id.device_button_2,
			R.id.device_button_3,
			R.id.device_button_4,
			R.id.device_button_5,
			R.id.device_button_6,
			R.id.device_button_7,
			
		};
	private final int deviceStatusId[]={
			R.id.device_status_1,
			R.id.device_status_2,
			R.id.device_status_3,
			R.id.device_status_4,
			R.id.device_status_5,
			R.id.device_status_6,
			R.id.device_status_7,
			
		};
	private String nodeLayer;
	private String nodeNumber;
	private TableRow tableRow01;
	
	private Handler mHandler;
	private Runnable runnable;

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
	    
		
		for(int i=0;i<7;i++)
		{
			ivDevicesControl[i]= (Button)this.findViewById(buttonsId[i]); 
			ivDevicesStatus [i] = (TextView)this.findViewById(deviceStatusId[i]); 
			devicesStatus[i] = false;
			ivDevicesControl[i].setOnClickListener(this);
		}
	    
		HandlerThread thread = new HandlerThread("DeviceControlThread");
		thread.start();
		
		mHandler  = new Handler(thread.getLooper());
		
		mHandler.post(runnable);
		
		runnable = new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//while(true)
				{
					Log.d(TAG,"runnable is runging!");
				}
			}
			
		};
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
    
	}





	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mHandler.removeCallbacks(runnable);
		
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
			case R.id.device_button_1:
				deviceNum=1;
				break;
			case R.id.device_button_2:
				deviceNum=2;
				break;
			case R.id.device_button_3:
				deviceNum=2;
				break;
			case R.id.device_button_4:
				deviceNum=5;
				break;
			case R.id.device_button_5:
				deviceNum=3;
				break;
			case R.id.device_button_6:
				deviceNum=4;
				break;
			case R.id.device_button_7:
				deviceNum=6;
				break;
			default:break;
		}
		if(deviceNum!=0)
		{
			
			{
			  // devicesStatus[deviceNum-1] = false;
			   Log.d(TAG,"设备开启");
			   Global.sendCommandData = false;
			
			   byte [] sendData = ControlSend.sendControl(nodeLayer,nodeNumber,deviceNum, ControlSend.getSW(true));
				try 
				{
					mOutputStream.write(sendData, 0, sendData.length);
					Log.d(TAG,"发送成功");
				} catch (IOException e) 
				{
					e.printStackTrace();
					Log.d(TAG,"发送失败");
				}
				Global.sendCommandData = true;
			
			}
		}
		
		
	}
}
