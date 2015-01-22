package com.zgkxzx.activity;



import java.util.ArrayList;
import java.util.List;

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




public class TelViewActivity  extends ListActivity {
	
	
	private List<LayerTel> alLayer= new ArrayList<LayerTel>();
	private View myView = null;//自定义Dialog的View
	private EditText etLayerName = null; 
	private EditText etCallNumber = null; 
	private Dialog dialog=null;
	private DevSqlSevice qss;
	private MyApplication mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_tel_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
		
		
		
		qss = new DevSqlSevice(this);
		
		this.setTitle("楼层电话");
		
		
		
		this.getListView().setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				infoModifyDialog(position+1);
				if(dialog != null)
					dialog.show();
				//Toast.makeText(TelViewActivity.this, "Selected:"+String.valueOf(position), Toast.LENGTH_SHORT).show();
				
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
								
					qss.updateLayerTel(new LayerTel(layerId,layerName,layerTel));
					updateView();
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
		mApplication = (MyApplication) getApplication();
		int getLayer = mApplication.getScanLayer();
		int sqlLayer = (int)qss.getLayerTelCount();
		
//		if((qss.getLayerTelList(0, 2)==null))//如果数据库为空
//		{
//				qss.saveLayerTel(new LayerTel(String.valueOf(1),"xxx","no data"));
//		}
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

