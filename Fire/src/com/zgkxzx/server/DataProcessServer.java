package com.zgkxzx.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import com.zgkxzx.activity.DeviceView;
import com.zgkxzx.activity.NodeGirdView;
import com.zgkxzx.infosend.ControlSend;
import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorDevice;




import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android_serialport_api.MyApplication;
import android_serialport_api.SerialPort;

public class DataProcessServer extends Service {
	
	private static final String TAG = "DataProcessServer";
	private final int DATA_SEND_HANDLE = 0 ;
	private Handler handler = null;
	private MyApplication mApplication;
	private SerialPort mSerialPort;
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	private ArrayList<String> node;
	
	private DevSqlSevice devSql;
	
	private int scanLayer=1;
	
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
					
					
					receiveValue += new String(buffer, 0, size);
					
					//Log.d(TAG, "getAll:"+receiveValue);
					if((receiveValue.indexOf("#")!=-1)&&(receiveValue.indexOf("$")!=-1))//
					{
						//Log.d(TAG, "getL:"+receiveValue);
						int endIndex = receiveValue.indexOf("#");

						String secondValue = receiveValue.substring(0, endIndex);
						
						int startIndex = secondValue.indexOf("$");
						
						String elseValue = secondValue.substring(startIndex+1);
	
						receiveValue= receiveValue.substring(endIndex+1);
						
						//Log.d(TAG, "getReceiveValue:"+receiveValue);
						
						String[] infoStrings =  elseValue.split(",");
						
						//串口取出来的数据保存到数据库
						if(infoStrings.length>2)
						{
							String layer = infoStrings[0];
							for(int i=2;i<infoStrings.length;i++)
							{
								try{
									//Log.d(TAG, "*************");
									String getString = infoStrings[i];
									char[] temp1 = new char[16] ;
									getString.getChars(0, 16, temp1, 0);
									String sensorsStatus = String.valueOf(temp1);
									
									
									//Log.d(TAG, "sensorsStatus："+sensorsStatus);
									
									String PowerMode =  String.valueOf(getString.charAt(16));
									//Log.d(TAG, "PowerMode："+PowerMode);
									
									char[] temp2 = new char[3] ;
									getString.getChars(17, 20, temp2, 0);
									String power = String.valueOf(temp2);
									//Log.d(TAG, "power："+power);
									
									char[] temp3 = new char[8] ;
									getString.getChars(20, 27, temp3, 0);
									String devicesStatus = String.valueOf(temp3);
									//Log.d(TAG, "devicesStatus："+devicesStatus);
									
									String nodeId = Integer.toString(i-2);
									String name = layer+ nodeId;
									
									if(sensorsStatus.contains("1")||sensorsStatus.contains("2"))
									{
										SensorDevice devLog = new SensorDevice(layer,nodeId,
												PowerMode,power,sensorsStatus,devicesStatus);
								    	devSql.saveLog(devLog);
								    	Log.d(TAG, "Log Name："+name);
									}
							
									
									if((name.length()<7)&&(name.length()>2))
									{
										
									   
										//Log.d(TAG, layer+"-"+nodeId+"-"+PowerMode+"-"+power+"-"+sensorsStatus+"-"+devicesStatus);
										if(!(node.contains(name)))
										{
											SensorDevice dev = new SensorDevice(layer,nodeId,
													PowerMode,power,sensorsStatus,devicesStatus);
										    devSql.save(dev);
									    	node.add(name);
									    
											//Log.d(TAG, "add name："+name);
										}else
										{
											SensorDevice dev = new SensorDevice(layer,nodeId,
													PowerMode,power,sensorsStatus,devicesStatus);
										    devSql.update(dev);
										    //Log.d(TAG, "updata name："+name);
										}
										
										
									}
									
								}catch(Exception e)
								{
									Log.d(TAG+"数据接收异常:",e.toString());
									
								}
								
								
							}
							
							
						}
						
						
					    /*
					    if(Integer.parseInt(infoStrings[0])>8)//8层以上保存日志
					    {
					    	SensorDevice dev = new SensorDevice(infoStrings[0],infoStrings[1],
					    			infoStrings[2],infoStrings[3],infoStrings[4],infoStrings[5]);
					    	devSql.saveLog(dev);
					    }*/
					    	
						
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
			
			node = new ArrayList<String>();
			
			devSql = new DevSqlSevice(DataProcessServer.this);
			
		} catch (SecurityException e) {
			Log.d(TAG, e.toString());
		} catch (IOException e) {
			Log.d(TAG, e.toString());
		} catch (InvalidParameterException e) {
			Log.d(TAG, e.toString());
		}
		super.onCreate();
		
		
		handler = new Handler() {
			// 当消息发送过来的时候会执行下面这个方法
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				if(msg.what == DATA_SEND_HANDLE){
					
					
					byte [] sendData = ControlSend.sendCommand(Integer.toString(scanLayer), Integer.toString(25), ControlSend.NODE_MAIN_DATA);
					scanLayer++;
					if(scanLayer==10)
						scanLayer=1;
					try 
					{
						mOutputStream.write(sendData, 0, sendData.length);
						Log.d(TAG,"发送成功");
					} catch (IOException e) 
					{
						e.printStackTrace();
						Log.d(TAG,"发送失败");
					}
				}
			};
		};
		
		 new Thread()
			{
				@Override
				public void run()
				{
					while(true)
					{
						
						try
						{
							Thread.sleep(5000);
						}catch(InterruptedException e)
						{
							
						}
						if(mApplication.isSendCommandFlag())
						handler.sendEmptyMessage(DATA_SEND_HANDLE);
					}
				}
			}.start();
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
