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
		// ȫ����ʾ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.girdnode);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ǿ��Ϊ���� 
		
		
		Bundle extras = getIntent().getExtras();
		this.setTitle("��"+extras.getString("Layer")+"��");
		
		//packageInfos = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		//public List<SensorDevice> getScrollData(int offset, int maxResult){
		
		DevSqlSevice devSqlSevice = new DevSqlSevice(DeviceView.this);
		sensorDeviceList = devSqlSevice.getScrollData(0, 50);
		
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
				//Toast.makeText(GirdViewDemo.this, new Integer(position).toString(), 1000).show();
				
			}
		
		
		
		
		
	});
		
	}
	//��ϸ��Ϣ��ʾ�˵�
    private void showNodeDetail(int position) {	
    	   //���� AlertDialog.Builder ʵ��
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("�ڵ���ϸ��Ϣ");
			
			StringBuffer message = new StringBuffer();
			message.append("\n\t���: " + sensorDeviceList.get(position).getId());
			message.append("\n\t����: " + sensorDeviceList.get(position).getName());
			message.append("\n\t����: " + sensorDeviceList.get(position).getPower());
			message.append("\n\t����: " + sensorDeviceList.get(position).getMainPowerStatus());
			message.append("\n\t̽ͷ: " + sensorDeviceList.get(position).getSensorType());
			builder.setMessage(message.toString());
			builder.setIcon(R.drawable.node_green);
			
			builder.setPositiveButton("ȷ��", null);//��������Dialog��ʧ
			builder.show();
		}
    
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);
			
			menu.setHeaderTitle("zgkxzx");
			menu.add(Menu.NONE, Menu.FIRST+1, 1, "��ϸ");
			menu.add(Menu.NONE, Menu.FIRST+2, 2, "����");
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
