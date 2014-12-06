package com.zgkxzx.activity;

import java.util.ArrayList;
import java.util.List;

import com.zgkxzx.activity.R;
import com.zgkxzx.mysql.DBOpenHelper;
import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorDevice;





import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MyActivity extends Activity {

	private List<MyMenuIcon> myMenuItem = null;
	private GridView gridView = null;
	
	
	//���˵�����
	private static String []menuTitle={
			"��Ϣ�鿴",
			"��������",
			"ϵͳ����",
			"���ߴ�ӡ",
			"��չ����",
			"��Ʒ��Ϣ"			
	};
	//���˵�ͼ����Դ
	private static int [] iconTilte = {
			R.drawable.menu_home,
			R.drawable.menu_control,
			R.drawable.menu_settings,
			R.drawable.menu_fax,
			R.drawable.menu_add,
			R.drawable.menu_info
			
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //����
		setContentView(R.layout.activity_my);
				 
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ǿ��Ϊ���� 
	    overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_in_top);//���µ���
	    myMenuItem = new ArrayList <MyMenuIcon>();
	    
	    //���˵���Ŀ���뵽 ����
	    for(int i=0;i<menuTitle.length;i++)
	       myMenuItem.add(new MyMenuIcon(menuTitle[i],iconTilte[i]));
	    
	    
	   // DBOpenHelper dbOpenHelper =new DBOpenHelper(MyActivity.this);
	    DevSqlSevice devSql = new DevSqlSevice(MyActivity.this);
	    //public SensorDevice(String name,String mainPowerStatus,String power,String sensorType)
	    SensorDevice dev = new SensorDevice("1-101","main","90%","smoke");
	    devSql.save(dev);
	    
	    dev = new SensorDevice("1-103","other","90%","sss");
	    devSql.save(dev);
	    
	    SensorDevice findDevice = devSql.find(1);
	    
	    Toast.makeText(MyActivity.this, String.valueOf(devSql.getCount()), 1000).show();
	     
	    gridView = (GridView) findViewById(R.id.menuGridView);
	    
	    gridView.setAdapter(new MenuGirdView(myMenuItem,MyActivity.this));
        gridView.setOnItemClickListener(new OnItemClickListener() {
			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch(position)
				{
					case 0:
						
						//Toast.makeText(MyActivity.this, new Integer(position).toString(), 1000).show();
						Toast.makeText(MyActivity.this,"������Ϣ��ѯ", 1000).show();
						
						startActivity(new Intent(MyActivity.this,LayerListView.class));
						//overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out); 
						//overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_in_top);//���µ���
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						break;
					case 1:
						
						
						Toast.makeText(MyActivity.this,"������������", 1000).show();
						startActivity(new Intent(MyActivity.this,DeviceControl.class));
						//overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out); 
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);    
						break;
					case 2:
						
						
						Toast.makeText(MyActivity.this,"����ϵͳ���ò˵�", 1000).show();
						startActivity(new Intent(MyActivity.this,Settings.class));
						//overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						break;
					case 3:
						Toast.makeText(MyActivity.this,"���ߴ�ӡ", 1000).show();
						
						
						break;
					case 4:
						Toast.makeText(MyActivity.this,"��ӹ���", 1000).show();
						AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
						builder.setTitle("��ӹ���");
						builder.setMessage("������δ��ͨ������");
						builder.show();
						
						break;
					case 5:
						Toast.makeText(MyActivity.this,"��ע��Ʒ��Ϣ", 1000).show();
						
						startActivity(new Intent(MyActivity.this,ProductInfo.class));
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						/*
						AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
						builder.setTitle("����");
						builder.setMessage(R.string.about_msg);
						builder.show();*/
						break;
					default:
						
						Toast.makeText(MyActivity.this,"û�п�ִ�еĶ���", 1000).show();
						break;
				
				}
				
				
			}
	   
	   
        });
	}
	

	
}
