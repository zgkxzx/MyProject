package com.zgkxzx.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorDevice;



import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android_serialport_api.MyApplication;
import android_serialport_api.SerialPort;

public class DataProcessServer extends Service {
	
	private static final String TAG = "DataProcessServer";
	private MyApplication mApplication;
	private SerialPort mSerialPort;
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	
	private DevSqlSevice devSql;
	
	private class ReadThread extends Thread {

		@Override
		public void run() {
			super.run();
			while(!isInterrupted()) {
				int size;
				try {
					byte[] buffer = new byte[512];
					if (mInputStream == null) return;
					size = mInputStream.read(buffer);
					if (size > 0) {
						onDataReceived(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}

		public final void runEncodeThread(Runnable action) 
	    {
			action.run();
		}
		String receiveValue=""; 
	    int lineNum=0;
		protected void onDataReceived(final byte[] buffer, final int size) {
			runEncodeThread(new Runnable() {
				public void run() {
					//Log.d(TAG, "onDataReceived is running!");
					receiveValue += new String(buffer, 0, size);
					if(receiveValue.indexOf("\r\n")!=-1&&(receiveValue.indexOf("$")!=-1))//
					{
						int endIndex = receiveValue.indexOf("\r\n");

						String secondValue = receiveValue.substring(0, endIndex);
						
						int startIndex = secondValue.indexOf("$");
						
						String elseValue = secondValue.substring(startIndex+1);
	
						receiveValue= receiveValue.substring(endIndex+1);
						
						//mReception.append("[Time:"+sDateFormat.format(new java.util.Date())+"]: "+elseValue+"\n");
						
						
						String[] infoStrings =  elseValue.split(",");
						
						
						devSql = new DevSqlSevice(DataProcessServer.this);
					    SensorDevice dev = new SensorDevice(infoStrings[0],infoStrings[1],infoStrings[2],infoStrings[3],infoStrings[4]);
					    devSql.save(dev);
						
					}
					
					
				}
				});
		}
			
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	//创建服务时候调用
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d(TAG, "Service on create!");
		mApplication = (MyApplication) getApplication();
		try {
			mSerialPort = mApplication.getSerialPort();
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();

			/* Create a receiving thread */
			mReadThread = new ReadThread();
			
		} catch (SecurityException e) {
			Log.d(TAG, e.toString());
		} catch (IOException e) {
			Log.d(TAG, e.toString());
		} catch (InvalidParameterException e) {
			Log.d(TAG, e.toString());
		}
		super.onCreate();
	}

	
	//开始服务时候调用
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Service on StartCommand!");
		mReadThread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	//销毁时候调用
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG, "Service on Destroy!");
		if (mReadThread != null)
			mReadThread.interrupt();
		mApplication.closeSerialPort();
		mSerialPort = null;
	
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	

}
