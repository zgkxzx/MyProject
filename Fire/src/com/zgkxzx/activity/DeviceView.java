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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//������
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			
		setContentView(R.layout.girdnode);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ǿ��Ϊ���� 
		
		
		Bundle extras = getIntent().getExtras();
		bLayer = extras.getString("Layer");
		this.setTitle("��"+bLayer+"��");
		
		devSqlSevice = new DevSqlSevice(DeviceView.this);
		
		sensorDeviceList = devSqlSevice.getCommonAttrNode(bLayer);
		
		gridView = (GridView)this.findViewById(R.id.gridView);
		
		this.registerForContextMenu(gridView);	//��� ���� �����¼�
		
		//�����ݿ��������뵽������
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
		super.onDestroy();
	}
	//��ϸ��Ϣ��ʾ�˵�
    private void showNodeDetail(int position) {
    	
    	
    	
    	NodeInfoDialog nid = new NodeInfoDialog(DeviceView.this);
    	
    	nid.setLayerContent(sensorDeviceList.get(position).getLayer()+"¥");
    	nid.setNodeNameContent(sensorDeviceList.get(position).getName());
    	nid.setPowerModeContent(sensorDeviceList.get(position).getPowerMode(),sensorDeviceList.get(position).getPower());
    	nid.setSensorsStatus(sensorDeviceList.get(position).getSensorsStatus());
    	nid.setSensorsType(sensorDeviceList.get(position).getSensorsType());
    	
    	nid.setTitle("�ӻ�"+sensorDeviceList.get(position).getName()+"   ��Ϣ��");
    	
    	nid.show();
    	 
		}
    
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);
			
			menu.setHeaderTitle("����ѡ��");
			menu.add(Menu.NONE, Menu.FIRST+1, 1, "����");
		}
		@Override
		public boolean onContextItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			long id = menuInfo.id;
			DeviceView.this.nodePosition = (int)id;
			
			switch(item.getItemId())
			{
				
				
				case Menu.FIRST+1:
					
					String layer = this.bLayer;
					
					Log.d(TAG, "nodeNumber");
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
			return super.onContextItemSelected(item);
		}
		private final int SEARCH_NODE = 0 ;
		private Handler handler = new Handler() {
			// ����Ϣ���͹�����ʱ���ִ�������������
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
