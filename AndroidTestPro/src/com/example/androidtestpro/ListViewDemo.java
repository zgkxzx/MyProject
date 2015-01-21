package com.example.androidtestpro;

import java.util.ArrayList;

import com.zgkxzx.sellrecoder.LayerTel;


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




public class ListViewDemo  extends ListActivity {
	
	
	private ArrayList<LayerTel> alLayer=null;
	private View myView = null;//自定义Dialog的View
	private EditText etLayerName = null; 
	private EditText etCallNumber = null; 
	private Dialog dialog=null;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_tel_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
		
		this.setTitle("楼层电话信息");
		
		alLayer = new ArrayList<LayerTel>();
		
		//SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_settings", Activity.MODE_PRIVATE);
		//String layer = sharedSettings.getString("layer","1");
		
		for(int i=1;i<10;i++)
		{
			LayerTel lt = new LayerTel(String.valueOf(i),"xx","1312121122");
			alLayer.add(lt);
		}
		LayerTel lt = new LayerTel(String.valueOf(10),"xx","no data");
		alLayer.add(lt);
		
		HistoryListView hlv = new HistoryListView(alLayer,this);
		
		this.getListView().setAdapter(hlv);
		
		this.getListView().setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
				
				infoModifyDialog(position+1);
				if(dialog != null)
					dialog.show();
				Toast.makeText(ListViewDemo.this, "Selected:"+String.valueOf(position), Toast.LENGTH_SHORT).show();
				
			}
		
		
	   });
		
		 
	}
	public void infoModifyDialog(int slayer)
	{
		final int layer;
		layer = slayer;
		LayoutInflater factory = LayoutInflater.from(ListViewDemo.this);
		
		myView = factory.inflate(R.layout.dialog_info_input, null);
		
		etLayerName= (EditText) myView.findViewById(R.id.et_layer_name);
		etCallNumber = (EditText) myView.findViewById(R.id.et_call_number);
		
		
		sp = this.getSharedPreferences("layer_call_config",Activity.MODE_PRIVATE);
		
		String telNum = sp.getString(String.valueOf(layer)+"call", "");
		
		etLayerName.setText("xx楼");
		etCallNumber.setText(telNum);
						
		dialog = new AlertDialog.Builder(ListViewDemo.this)
			.setIcon(R.drawable.st_icon_phone_info)
			.setView(myView)
			.setTitle(String.valueOf(layer)+"楼信息修改")
			.setPositiveButton("保存",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					SharedPreferences.Editor editor = sp.edit();
					editor.putString(String.valueOf(layer)+"call",etCallNumber.getText().toString());
					editor.commit();
					
					
					
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

	
	
}
