package com.zgkxzx.activity;

import java.util.List;

import com.zgkxzx.activity.R;
import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorDevice;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class DeviceView extends Activity implements Runnable{
	
    private final String TAG = "DeviceViewTag";
	private List<SensorDevice> sensorDeviceList = null; 
	
	private GridView gridView = null;
	private String bLayer=null;
	private DevSqlSevice devSqlSevice;
	private Thread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//进度条
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// 全屏显示
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.girdnode);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
		
		
		Bundle extras = getIntent().getExtras();
		bLayer = extras.getString("Layer");
		this.setTitle("第"+bLayer+"层");
		
		devSqlSevice = new DevSqlSevice(DeviceView.this);
		
		sensorDeviceList = devSqlSevice.getCommonAttrNode(bLayer);
		
		gridView = (GridView)this.findViewById(R.id.gridView);
		
		this.registerForContextMenu(gridView);	//添加 长按 按键事件
		
		//将数据库数据载入到适配器
		gridView.setAdapter(new NodeGirdView(sensorDeviceList,DeviceView.this));
				
		gridView.setOnItemClickListener(new OnItemClickListener() {
		@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
			
				showNodeDetail(position);
				
			}
		
		
	    });
		
		thread = new Thread(this);
		thread.start();
		
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
	}
	//详细信息显示菜单
    private void showNodeDetail(int position) {	
    	   //创建 AlertDialog.Builder 实例
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("节点详细信息");
			
			StringBuffer message = new StringBuffer();
			message.append("\t楼层: " + sensorDeviceList.get(position).getLayer());
			message.append("\n\t名称: " + sensorDeviceList.get(position).getName());
			message.append("\n\t电量: " + sensorDeviceList.get(position).getPower());
			message.append("\n\t供电: " + sensorDeviceList.get(position).getMainPowerStatus());
			message.append("\n\t探头: " + sensorDeviceList.get(position).getSensorType());
			builder.setMessage(message.toString());
			builder.setIcon(R.drawable.node_green);
			
			builder.setPositiveButton("确定", null);//仅仅是让Dialog消失
			builder.show();
		}
    
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);
			
			menu.setHeaderTitle("zgkxzx");
			menu.add(Menu.NONE, Menu.FIRST+1, 1, "详细");
			menu.add(Menu.NONE, Menu.FIRST+2, 2, "控制");
		}
		@Override
		public boolean onContextItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch(item.getItemId())
			{
				case Menu.FIRST+1:
					
					Toast.makeText(this, "详细", Toast.LENGTH_SHORT).show();
					break;
				
				case Menu.FIRST+2:
					
					this.startActivity(new Intent(DeviceView.this,DeviceControl.class));
					//overridePendingTransition(R.anim.fade, R.anim.hold); 
				   // overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
					break;
			
				default:break;
			
			
			
			}
			return super.onContextItemSelected(item);
		}
		private final int SEARCH_NODE = 0 ;
		private Handler handler = new Handler() {
			// 当消息发送过来的时候会执行下面这个方法
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				if(msg.what == SEARCH_NODE){
					Log.d(TAG, "handleMessage!");
					gridView.setAdapter(new NodeGirdView(sensorDeviceList,DeviceView.this));
				
				}
			};
		};
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				Log.d(TAG, "runing!");
				sensorDeviceList = devSqlSevice.getCommonAttrNode(bLayer);
				
				handler.sendEmptyMessage(SEARCH_NODE);
				try
				{
					Thread.sleep(5000);
				}catch(InterruptedException e)
				{
				}
				
			}
			
			
			
			
		}
		
		
		
		
		
}
