package com.zgkxzx.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import com.zgkxzx.activity.DeviceControl;
import com.zgkxzx.activity.DeviceView;
import com.zgkxzx.activity.MyActivity;
import com.zgkxzx.activity.NodeGirdView;
import com.zgkxzx.activity.R;
import com.zgkxzx.infosend.ControlSend;
import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorDevice;




import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android_serialport_api.MyApplication;
import android_serialport_api.SerialPort;

public class DataProcessServer extends Service {
	
	private static final String TAG = "DataProcessServer";
	
	private final static int NOTIFICATION_ID_ICON = 0x10000;
	
	private final int DATA_SEND_HANDLE = 0 ;
	private Handler handler = null;
	private MyApplication mApplication;
	private SerialPort mSerialPort;
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	
	private String wNodeName=null;
	private String wLayerName=null;
	
	private ArrayList<String> node;
	
	private ArrayList<String> logTempList;
	
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
						Log.d(TAG, "getL:"+receiveValue);
						int endIndex = receiveValue.indexOf("#");

						String secondValue = receiveValue.substring(0, endIndex);
						
						int startIndex = secondValue.indexOf("$");
						
						String elseValue = secondValue.substring(startIndex+1);
	
						receiveValue= receiveValue.substring(endIndex+1);
						
						//Log.d(TAG, "getReceiveValue:"+receiveValue);
						
						String[] infoStrings;
						infoStrings =  elseValue.split(",");
						
						//串口取出来的数据保存到数据库
						if(infoStrings.length>2&&infoStrings.length<20)
						{
				
													
							switch(infoStrings[0].charAt(0))
							{
								case 'A':
									
									dataReceive(infoStrings);
									break; 
								case 'B':
									//接收 一次性 数据
									
									break;
								case 'C':
									//标志功能
									//mApplication.setLogRevFlag(true);
									logTempList.clear();
									break;
								default:
									System.out.println("default");
									break;
							}
							
						}	
								
											
							
							
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
			logTempList =  new ArrayList<String>();
			
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
					
					//扫描楼层
					byte [] sendData = ControlSend.sendCommand(Integer.toString(scanLayer), Integer.toString(25), ControlSend.NODE_MAIN_DATA);
					
					scanLayer++;
					if(scanLayer==mApplication.getScanLayer())
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
	
	public void dataReceive(String [] sbuffer)
	{
		
		String layer = sbuffer[1];
		for(int i=3;i<sbuffer.length;i++)
		{
				//Log.d(TAG, "*************");
			String sensorsStatus= null;
			String power= null;
			String powerMode = null;
			String devicesStatus= null;
			
			String name= null;
			
			try{
				//子机状态
				String getString = sbuffer[i];
				char[] temp1 = new char[16] ;
				getString.getChars(0, 16, temp1, 0);
			    sensorsStatus = String.valueOf(temp1);
				
				
				//Log.d(TAG, "sensorsStatus："+sensorsStatus);
				
				powerMode =  String.valueOf(getString.charAt(16));
				//Log.d(TAG, "PowerMode："+PowerMode);
				
				char[] temp2 = new char[3] ;
				getString.getChars(17, 20, temp2, 0);
				power = String.valueOf(temp2);
				//Log.d(TAG, "power："+power);
				//探头状态
				char[] temp3 = new char[8] ;
				getString.getChars(20, 27, temp3, 0);
				devicesStatus = String.valueOf(temp3);
				//Log.d(TAG, "devicesStatus："+devicesStatus);
			}catch(Exception e)
			{
				Log.d(TAG, "串口接收异常");
			}
				
				String nodeId = Integer.toString(i-2);
				
				name= layer+ nodeId;
				
				//报警节点处理 
				if(sensorsStatus!=null)
				{
					
					if(sensorsStatus.contains("1")||sensorsStatus.contains("2"))
					{
						if((!logTempList.contains(name)))
						{
							SensorDevice devLog = new SensorDevice(layer,nodeId,
									powerMode,power,sensorsStatus,devicesStatus);
					    	devSql.saveLog(devLog);
					    	
					    	logTempList.add(name);
					    	wNodeName = nodeId;
					    	wLayerName = layer;
					    	showWarningNotification();
					    	//mApplication.setLogRevFlag(false);
					    	Log.d(TAG, "------------Add Log Name---------："+name);
					    	
					    	
					    	 //发送信息到主机
					    	  mApplication.setSendCommandFlag(false);
							    	  			    	  
							   byte [] sendData = ControlSend.sendAllWarn(layer, nodeId,  devSql.getNodeConfigInfo(name).getAddrName());
								try 
								{
									mOutputStream.write(sendData, 0, sendData.length);
									Log.d(TAG,"发送成功");
								} catch (IOException e) 
								{
									e.printStackTrace();
									Log.d(TAG,"发送失败");
								}
								
								mApplication.setSendCommandFlag(true);
					    	
						}
					}
					
					
				}

				if((name.length()<7)&&(name.length()>2))
				{
					if(devSql.getLogCount()==0)
						node.clear();
					
					Log.d(TAG, layer+"-"+nodeId+"-"+powerMode+"-"+power+"-"+sensorsStatus+"-"+devicesStatus);
					if(!(node.contains(name)))
					{
						SensorDevice dev = new SensorDevice(layer,nodeId,
								powerMode,power,sensorsStatus,devicesStatus);
					    devSql.save(dev);
				    	node.add(name);
				    
						Log.d(TAG, "add name："+name);
					}else
					{
						SensorDevice dev = new SensorDevice(layer,nodeId,
								powerMode,power,sensorsStatus,devicesStatus);
					    devSql.update(dev);
					    Log.d(TAG, "updata name："+name);
					}
					
					
				}
		}
				
		}

	//Intent intent = new Intent();
	//intent.setClass(DeviceView.this, DeviceControl.class);
	//intent.putExtra("Layer", wLayerName);
	//intent.putExtra("NodeNumber", wNodeName);
	//
	 private void showWarningNotification() { 
		 NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 
			Notification notification
			= new Notification(R.drawable.fire_icon, null, System.currentTimeMillis());
			
			notification.flags |= Notification.FLAG_ONGOING_EVENT; 
			notification.flags |= Notification.FLAG_NO_CLEAR; 
			
			Intent intent = new Intent();
			intent.setClass(this, DeviceControl.class);
			intent.putExtra("Layer", wLayerName);
			intent.putExtra("NodeNumber", wNodeName);
			
			PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0); 
			notification.contentIntent = pi; 
			notification.setLatestEventInfo(this, "报警信息来自：",wLayerName+"楼"+wNodeName+"号", pi); 
			
			nm.notify(NOTIFICATION_ID_ICON, notification); 
		 
	    }

}
