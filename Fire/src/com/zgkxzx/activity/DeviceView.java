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

public class DeviceView extends Activity {
	

	private List<SensorDevice> sensorDeviceList = null; 
	
	private GridView gridView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.girdnode);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
		
		
		Bundle extras = getIntent().getExtras();
		this.setTitle("第"+extras.getString("Layer")+"层");
		
		//packageInfos = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		//public List<SensorDevice> getScrollData(int offset, int maxResult){
		
		DevSqlSevice devSqlSevice = new DevSqlSevice(DeviceView.this);
		sensorDeviceList = devSqlSevice.getScrollData(0, 50);
		
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
				//Toast.makeText(GirdViewDemo.this, new Integer(position).toString(), 1000).show();
				
			}
		
		
		
		
		
	});
		
	}
	//详细信息显示菜单
    private void showNodeDetail(int position) {	
    	   //创建 AlertDialog.Builder 实例
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("节点详细信息");
			
			StringBuffer message = new StringBuffer();
			message.append("\n\t编号: " + sensorDeviceList.get(position).getId());
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
					
					
					break;
				
				case Menu.FIRST+2:
					
					this.startActivity(new Intent(DeviceView.this,DeviceControl.class));
					overridePendingTransition(R.anim.fade, R.anim.hold); 
					
					break;
			
				default:break;
			
			
			
			}
			return super.onContextItemSelected(item);
		}
		
		
		
		
		
}
