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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class DeviceView extends Activity implements Runnable{
	
    private final String TAG = "DeviceViewTag";
	private List<SensorDevice> sensorDeviceList = null; 
	
	private GridView gridView = null;
	private String bLayer=null;
	
	private int nodePosition;
	
	private DevSqlSevice devSqlSevice;
	private Thread thread;
	
	private boolean threadSw = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//进度条
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			
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
		//thread.stop();
		//thread.interrupted();
		threadSw = false;
		super.onDestroy();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		threadSw = true;
				
		super.onStart();
	}
	//详细信息显示菜单
    private void showNodeDetail(int position) {
    	
    	
    	
    	NodeInfoDialog nid = new NodeInfoDialog(DeviceView.this);
    	
    	nid.setLayerContent(sensorDeviceList.get(position).getLayer()+"楼");
    	nid.setNodeNameContent(sensorDeviceList.get(position).getName());
    	nid.setPowerModeContent(sensorDeviceList.get(position).getPowerMode(),sensorDeviceList.get(position).getPower());
    	nid.setSensorsStatus(sensorDeviceList.get(position).getSensorsStatus());
    	nid.setSensorsType(sensorDeviceList.get(position).getSensorsType());
    	
       	nid.setTitle(devSqlSevice.getNodeConfigInfo("0"+bLayer+"-"+Integer.toString(position+1)+"-0").getAddrName()+" : 子机"+sensorDeviceList.get(position).getName());
    	
    	nid.show();
    	 
		}
    
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);
			
			menu.setHeaderTitle("操作选项");
			menu.add(Menu.NONE, Menu.FIRST+1, 1, "控制");
		}
		@Override
		public boolean onContextItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			
			AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			
			if(menuInfo!= null)
			{
				long id = menuInfo.id;
				DeviceView.this.nodePosition = (int)id;
				switch(item.getItemId())
				{
					
					
					case Menu.FIRST+1:
						
						String layer = this.bLayer;
						
						Log.d(TAG, sensorDeviceList.get(nodePosition).getName());
						
						Intent intent = new Intent();
						intent.setClass(DeviceView.this, DeviceControl.class);
						intent.putExtra("Layer", layer);
						intent.putExtra("NodeNumber", sensorDeviceList.get(nodePosition).getName());
						startActivity(intent);
						
						//this.startActivity(new Intent(DeviceView.this,DeviceControl.class));
					   // overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						break;
				
					default:break;
				   }			
				}else
				{
					Toast.makeText(DeviceView.this, "没有接收到任何信息", Toast.LENGTH_LONG).show();
				}
					
						
			
			return super.onContextItemSelected(item);
		}
		private final int SEARCH_NODE = 0 ;
		private Handler handler = new Handler() {
			// 当消息发送过来的时候会执行下面这个方法
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				if(msg.what == SEARCH_NODE){
					//Log.d(TAG, "handleMessage!");
					gridView.setAdapter(new NodeGirdView(sensorDeviceList,DeviceView.this));
				
				}
			};
		};
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				sensorDeviceList = devSqlSevice.getCommonAttrNode(bLayer);
				//Log.d(TAG, String.valueOf(sensorDeviceList.size()));
				
				handler.sendEmptyMessage(SEARCH_NODE);
				try
				{
					Thread.sleep(1000);
				}catch(InterruptedException e)
				{
					
				}
				
			}
			
			
			
			
		}
		
		
		
		
		
}
