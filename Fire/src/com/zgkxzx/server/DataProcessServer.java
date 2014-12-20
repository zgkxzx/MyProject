package com.zgkxzx.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;

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
	//private OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	private ArrayList<String> node;
	
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
					
					
					receiveValue += new String(buffer, 0, size);
					
					Log.d(TAG, "ss:"+receiveValue);
					if(receiveValue.indexOf("\r\n")!=-1&&(receiveValue.indexOf("$")!=-1))//
					{
						int endIndex = receiveValue.indexOf("\r\n");

						String secondValue = receiveValue.substring(0, endIndex);
						
						int startIndex = secondValue.indexOf("$");
						
						String elseValue = secondValue.substring(startIndex+1);
	
						receiveValue= receiveValue.substring(endIndex+1);
						
						
						
						String[] infoStrings =  elseValue.split(",");
						
						//串口取出来的数据保存到数据库
						if(infoStrings.length>2)
						{
							String layer = infoStrings[0];
							for(int i=2;i<infoStrings.length;i++)
							{
								try{
									Log.d(TAG, "*************");
									String getString = infoStrings[i];
									char[] temp1 = new char[8] ;
									getString.getChars(0, 8, temp1, 0);
									String sensorsStatus = String.valueOf(temp1);
									Log.d(TAG, "sensorsStatus："+sensorsStatus);
									
									String PowerMode =  String.valueOf(getString.charAt(8));
									Log.d(TAG, "PowerMode："+PowerMode);
									
									char[] temp2 = new char[3] ;
									getString.getChars(9, 12, temp2, 0);
									String power = String.valueOf(temp2);
									Log.d(TAG, "power："+power);
									
									char[] temp3 = new char[8] ;
									getString.getChars(12, 20, temp3, 0);
									String devicesStatus = String.valueOf(temp3);
									Log.d(TAG, "devicesStatus："+devicesStatus);
									
									
									String nodeId = Integer.toString(i-2);
									String name = layer+ nodeId;
									
									if((name.length()<7)&&(name.length()>2))
									{
										
										Log.d(TAG, "-----------------");
										Log.d(TAG, "name："+name);
										//Log.d(TAG, layer+"-"+nodeId+"-"+PowerMode+"-"+power+"-"+sensorsStatus+"-"+devicesStatus);
										if(!(node.contains(name)))
										{
											SensorDevice dev = new SensorDevice(layer,nodeId,
													PowerMode,power,sensorsStatus,devicesStatus);
										    devSql.save(dev);
									    	node.add(name);
									    	Log.d(TAG, "add：");
										}else
										{
											SensorDevice dev = new SensorDevice(layer,Integer.toString(i-2),
													PowerMode,power,sensorsStatus,devicesStatus);
										    devSql.update(dev);
										    Log.d(TAG, "update：");
										}
										
										
									}
									
								}catch(Exception e)
								{
									Log.d(TAG,"数据接收异常！");
								}
								
							}
							
							
						}
						
						//public SensorDevice(String id,String layer,String name,String PowerMode,String power,String sensorsStatus,String devicesStatus)
												
					   /* if(!(node.contains(infoStrings[1])))//如果数据库不存在 ，那么添加；如果数据库存在，那么更新
					    {
					    	
						    SensorDevice dev = new SensorDevice(infoStrings[0],infoStrings[1],
						    		infoStrings[2],infoStrings[3],infoStrings[4],infoStrings[5]);
						    devSql.save(dev);
					    	node.add(infoStrings[1]);
					    }else
					    {
					    	
						    SensorDevice dev = new SensorDevice(infoStrings[0],infoStrings[1],
						    		infoStrings[2],infoStrings[3],infoStrings[4],infoStrings[5]);
						    devSql.update(dev);
					    }
					    
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
			//mOutputStream = mSerialPort.getOutputStream();
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
