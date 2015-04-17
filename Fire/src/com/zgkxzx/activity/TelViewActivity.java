package com.zgkxzx.activity;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.LayerTel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android_serialport_api.MyApplication;
import android_serialport_api.SerialPort;




public class TelViewActivity  extends ListActivity {
	
	
	private List<LayerTel> alLayer= new ArrayList<LayerTel>();
	private View myView = null;//自定义Dialog的View
	private EditText etLayerName = null; 
	private EditText etCallNumber = null; 
	private Dialog dialog=null;
	private DevSqlSevice qss;
	
	private MyApplication mApplication;
	private SerialPort mDataSerialPort;
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	
	private String bundleContent=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_tel_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
		
		
		
		qss = new DevSqlSevice(this);
		
		Bundle extras = getIntent().getExtras();
		bundleContent = extras.getString("phoneMessage");
		
		mApplication = (MyApplication) getApplication();
		
		try {
			mDataSerialPort = mApplication.getCellSerialPort();
		} catch (InvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mOutputStream = mDataSerialPort.getOutputStream();
		mInputStream = mDataSerialPort.getInputStream();
		
		 
		
		this.setTitle("楼层电话");
			
		this.getListView().setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(bundleContent.equals("set"))
				{
					infoModifyDialog(position+1);
					if(dialog != null)
						dialog.show();					
				}else if(bundleContent.equals("dial"))
				{
					//Toast.makeText(TelViewActivity.this, "Haved dialing :"+String.valueOf(position), Toast.LENGTH_SHORT).show();
					
					try {
						String TelNo = null;
	                    TelNo = qss.findLayerTel(position+1).getTel();
	                    if(TelNo.equals("no data"))
	                    	Toast.makeText(TelViewActivity.this, "error!", Toast.LENGTH_SHORT).show();
	                    else
	                    {
	                    	mOutputStream.write(new String("ATD"+TelNo+";\r\n").getBytes());
	                    	Toast.makeText(TelViewActivity.this, "Calling "+TelNo, Toast.LENGTH_LONG).show();
	                    }
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else
					Toast.makeText(TelViewActivity.this, "error!", Toast.LENGTH_SHORT).show();
				
				
				
			}
		
		
	   });
		
		 
	}
	public void infoModifyDialog(int slayer)
	{
		final int layer;
		layer = slayer;
		LayoutInflater factory = LayoutInflater.from(TelViewActivity.this);
		
		myView = factory.inflate(R.layout.dialog_info_input, null);
		
		etLayerName= (EditText) myView.findViewById(R.id.et_layer_name);
		etCallNumber = (EditText) myView.findViewById(R.id.et_call_number);
		
		LayerTel ltl = qss.findLayerTel(layer);
		etLayerName.setText(ltl.getLayerName());
		etCallNumber.setText(ltl.getTel());
						
		dialog = new AlertDialog.Builder(TelViewActivity.this)
			.setIcon(R.drawable.st_icon_phone_info)
			.setView(myView)
			.setTitle(String.valueOf(layer)+"楼信息修改")
			.setPositiveButton("保存",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					//保存信息
					
					String layerId = String.valueOf(layer);
					String layerName = etLayerName.getText().toString();
					String layerTel = etCallNumber.getText().toString();
					
					Pattern p = Pattern.compile("[0-9]{11}");
					Matcher m = p.matcher(layerTel);
					if(m.matches())		
					{
						qss.updateLayerTel(new LayerTel(layerId,layerName,layerTel));
						updateView();
						
					}else
					{
						Toast.makeText(TelViewActivity.this, "输入有误，请输入11位手机号码", Toast.LENGTH_SHORT).show();
					}
					
				}
			})
			.setNegativeButton("取消",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			}).create();
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//alLayer = new ArrayList<LayerTel>();
		updateView();		
		
		
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

	public void updateView()
	{
		
		int getLayer = mApplication.getScanLayer();
		int sqlLayer = (int)qss.getLayerTelCount();
		

		if(sqlLayer < getLayer)
		{
			for(int i=sqlLayer+1;i<=getLayer;i++)
				qss.saveLayerTel(new LayerTel(String.valueOf(i),"xxx","no data"));
		}
		else//如果数据库有数据
		{
			
			for(int i = sqlLayer;i>getLayer;i--)
				qss.deleteLayerTel(i);
		}
		
		alLayer =  qss.getLayerTelList(0, 2);
	
		TelListView hlv = new TelListView(alLayer,this);
		
		this.getListView().setAdapter(hlv);
		
		
		
	}
	
}

