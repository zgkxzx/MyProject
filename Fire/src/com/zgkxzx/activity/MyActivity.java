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
	
	
	//主菜单标题
	private static String []menuTitle={
			"信息查看",
			"联动控制",
			"系统设置",
			"无线打印",
			"扩展功能",
			"产品信息"			
	};
	//主菜单图标资源
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
		
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏
		setContentView(R.layout.activity_my);
				 
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
	    overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_in_top);//由下到上
	    myMenuItem = new ArrayList <MyMenuIcon>();
	    
	    //将菜单条目加入到 集合
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
						Toast.makeText(MyActivity.this,"进入信息查询", 1000).show();
						
						startActivity(new Intent(MyActivity.this,LayerListView.class));
						//overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out); 
						//overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_in_top);//由下到上
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						break;
					case 1:
						
						
						Toast.makeText(MyActivity.this,"进入联动控制", 1000).show();
						startActivity(new Intent(MyActivity.this,DeviceControl.class));
						//overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out); 
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);    
						break;
					case 2:
						
						
						Toast.makeText(MyActivity.this,"进入系统设置菜单", 1000).show();
						startActivity(new Intent(MyActivity.this,Settings.class));
						//overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						break;
					case 3:
						Toast.makeText(MyActivity.this,"无线打印", 1000).show();
						
						
						break;
					case 4:
						Toast.makeText(MyActivity.this,"添加功能", 1000).show();
						AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
						builder.setTitle("添加功能");
						builder.setMessage("功能尚未开通，保留");
						builder.show();
						
						break;
					case 5:
						Toast.makeText(MyActivity.this,"关注产品信息", 1000).show();
						
						startActivity(new Intent(MyActivity.this,ProductInfo.class));
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						/*
						AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
						builder.setTitle("关于");
						builder.setMessage(R.string.about_msg);
						builder.show();*/
						break;
					default:
						
						Toast.makeText(MyActivity.this,"没有可执行的动作", 1000).show();
						break;
				
				}
				
				
			}
	   
	   
        });
	}
	

	
}
