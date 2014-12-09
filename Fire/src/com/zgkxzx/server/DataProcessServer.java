package com.zgkxzx.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;



import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android_serialport_api.MyApplication;
import android_serialport_api.SerialPort;

public class DataProcessServer extends Service {
	
	private static final String TAG = "DataProcessServer";
	protected MyApplication mApplication;
	protected SerialPort mSerialPort;
	protected OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	
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
		
		protected void onDataReceived(final byte[] buffer, final int size) {
			runEncodeThread(new Runnable() {
				public void run() {
					Log.d(TAG, "onDataReceived is running!");
					
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
			mReadThread.start();
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
