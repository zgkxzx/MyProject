package com.zgkxzx.activity;


import java.util.ArrayList;
import java.util.List;

import com.zgkxzx.printer.BluetoothActivity;
import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorLog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryCheckActivity extends ListActivity {
	
	private Button btn_save = null;
	private Button btn_print= null;
	
	private List<SensorLog> list=null;
	
	private DevSqlSevice dsl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		
		dsl= new DevSqlSevice(this);
		
		btn_print = (Button)findViewById(R.id.btn_print_record);
		btn_print.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Toast.makeText(HistoryCheckActivity.this,"无线打印", 1000).show();
				try
				{
					Intent intent = new Intent();										
					overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
					intent.setClass(HistoryCheckActivity.this, BluetoothActivity.class);
					startActivity(intent);
					
				}catch(Exception e)
				{
					Toast.makeText(HistoryCheckActivity.this,"请安装打印程序！", 1000).show();
				}
			}
		});
		
		list = new ArrayList<SensorLog>();
		
		DevSqlSevice ds = new DevSqlSevice(this);
		long logNum = ds.getLogCount();
		list = ds.getLogData(0, 2);
		
		this.setTitle("故障报警记录：共 "+Long.toString(logNum)+" 条");
		
		HistoryListView hlv = new HistoryListView(list,this);
		this.getListView().setAdapter(hlv);
		
		this.getListView().setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				showDetailAddress(position);
				
			}
		
		
	   });
		
		
	}
    public void showDetailAddress(int position)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(HistoryCheckActivity.this);
		
		
		builder.setTitle("探头名称\t\t\t探头状态\t\t\t具体地点");
		builder.setIcon(R.drawable.fire_icon);		
		StringBuffer message = new StringBuffer();
		
		String nodeName = null;
		String detailInfo =null;
		
		String nodeAllName = null;
		String nodeNameSql = null;
		
		String nodeStatus = null;
		
		nodeName = list.get(position).getName();
		detailInfo = list.get(position).getDetail();
		
		//message.append("\t探头名称\t\t\t\t探头状态\t\t\t\t具体地点");
		
		for(int i=1;i<=16;i++)
		{
			
			nodeStatus = detailInfo.substring(i-1, i).replace("0", "掉线").replace("1", "报警").replace("2", "故障").replace("3", "正常");
			if(i<10)
			{
				nodeAllName = nodeName+"-0"+Integer.toString(i);
			}else
				nodeAllName = nodeName+"-"+Integer.toString(i);
			nodeNameSql= nodeName+"-"+Integer.toString(i);
			message.append("\t\t\t"+nodeAllName+"\t\t\t\t\t"+nodeStatus+"\t\t\t\t"+dsl.getNodeConfigInfo(nodeNameSql).getAddrName()+"\n");
		}
		
		builder.setMessage(message.toString());
		
		builder.setPositiveButton("确定", null);//仅仅是让Dialog消失
		builder.show();
    }
	
}
