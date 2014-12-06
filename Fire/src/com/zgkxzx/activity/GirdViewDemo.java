package com.zgkxzx.activity;

import java.util.List;

import com.zgkxzx.activity.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ContextMenu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class GirdViewDemo extends Activity {
	

	private List<PackageInfo> packageInfos = null; 
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
		
		packageInfos = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		
		
		
		
	
		gridView = (GridView) findViewById(R.id.gridView);
		
		
		this.registerForContextMenu(gridView);	//��� ����ʱ��
		gridView.setAdapter(new MyGirdView(packageInfos,GirdViewDemo.this));
		
		
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
		@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				showNodeDetail(new Integer(position).toString());
				//Toast.makeText(GirdViewDemo.this, new Integer(position).toString(), 1000).show();
				
			}
		
		
		
		
		
	});
		
	}
	//��ϸ��Ϣ��ʾ�˵�
		private void showNodeDetail(String no) {	
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("�ڵ���ϸ��Ϣ");
			StringBuffer message = new StringBuffer();
			message.append("\t���: " + "��"+no+"��");
			message.append("\n\t����: " + "hello");//����
			message.append("\n\t����: " + "100");//����
			message.append("\n\t����: " + "����");//����
			message.append("\n\t̽ͷ: " + "�й�");//����
			message.append("\n\t����: " + "100");//����
			builder.setMessage(message.toString());
			builder.setIcon(R.drawable.node_icon);
			builder.setPositiveButton("ȷ��", null);//��������Dialog��ʧ
			builder.create().show();
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
					
					this.startActivity(new Intent(GirdViewDemo.this,DeviceControl.class));
					overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out); 
					
					break;
			
				default:break;
			
			
			
			}
			return super.onContextItemSelected(item);
		}
		
		
		
		
		
}
