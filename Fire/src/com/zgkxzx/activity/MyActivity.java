package com.zgkxzx.activity;

import java.util.ArrayList;
import java.util.List;

import com.zgkxzx.activity.R;
import com.zgkxzx.mysql.DBOpenHelper;
import com.zgkxzx.printer.BluetoothActivity;
import com.zgkxzx.server.DataProcessServer;
import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorDevice;





import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import android_serialport_api.MyApplication;


public class MyActivity extends Activity {

	private final String TAG = "MyActivity";
	private List<MyMenuIcon> myMenuItem = null;
	private GridView gridView = null;
	private DevSqlSevice devSql;
	
	private MyApplication mApplication =null;
	
	
	//主菜单标题
	private static String []menuTitle={
			"查看与控制",
			//"联动控制",
			"系统配置",
			"故障与报警",
			"消防电话",
			"产品信息"			
	};
	//主菜单图标资源
	private static int [] iconTilte = {
			R.drawable.menu_home,
			//R.drawable.menu_control,
			R.drawable.menu_settings,
			R.drawable.menu_fax,
			R.drawable.menu_phone,
			R.drawable.menu_info
			
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏
		setContentView(R.layout.activity_my);
				 
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
	   // overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_in_top);//由下到上
	    
	    mApplication = (MyApplication) getApplication();
	    
	    myMenuItem = new ArrayList <MyMenuIcon>();
	    
	    //将菜单条目加入到 集合
	    for(int i=0;i<menuTitle.length;i++)
	       myMenuItem.add(new MyMenuIcon(menuTitle[i],iconTilte[i]));
	    

	    SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_settings", Activity.MODE_PRIVATE);
		String getLayer = sharedSettings.getString("layer","1"); 
		
		mApplication.setScanLayer(Integer.parseInt(getLayer));
	    
	    
	    gridView = (GridView) findViewById(R.id.menuGridView);
	    //将集合添加到适配器
	    gridView.setAdapter(new MenuGirdView(myMenuItem,MyActivity.this));
	    
	    DevSqlSevice devSql = new DevSqlSevice(MyActivity.this);
	    devSql.clearTab();
	    if(mApplication.isDataServiceFlag())
	    {
		    //开启服务
			 MyActivity.this.startService(new Intent(MyActivity.this,DataProcessServer.class));
			 Log.d(TAG, "串口数据接收服务开启");
			 //
			 mApplication.setDataServiceFlag(false);
	    }
	    
	    
        gridView.setOnItemClickListener(new OnItemClickListener() {
			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch(position)
				{
					case 0:
			
						//Toast.makeText(MyActivity.this,"进入信息查询", 1000).show();
						
						startActivity(new Intent(MyActivity.this,LayerListView.class));
						//overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out); 
						//overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_in_top);//由下到上
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						break;
						
						
					case 1:
						
						
						//Toast.makeText(MyActivity.this,"进入系统设置菜单", 1000).show();
						startActivity(new Intent(MyActivity.this,Settings.class));
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						break;
						
					case 2:
						
						
						Intent intent = new Intent();										
						
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						intent.setClass(MyActivity.this, HistoryCheckActivity.class);
						startActivity(intent);
						
						break;
					case 3:
//						Toast.makeText(MyActivity.this,"添加功能", 1000).show();
//												
//						AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
//						builder.setTitle("添加功能");
//						builder.setMessage("功能尚未开通，保留");
//						builder.show();
						
						Intent intentPhone = new Intent();
						intentPhone.setClass(MyActivity.this, TelViewActivity.class);
						intentPhone.putExtra("phoneMessage", "dial");
						startActivity(intentPhone);
						
						
						break;
					case 4:
						//Toast.makeText(MyActivity.this,"关注产品信息", 1000).show();
						 
						startActivity(new Intent(MyActivity.this,ProductInfo.class));
						overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
						
						break;
					default:
						
						Toast.makeText(MyActivity.this,"没有可执行的动作", 1000).show();
						break;
				
				}
				
				
			}
	   
	   
        });
        
       
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		//停止服务
		//MyActivity.this.stopService(new Intent(MyActivity.this,DataProcessServer.class));
		//Log.d(TAG, "串口数据接收服务关闭");
		
		super.onDestroy();
	}
	 
	//屏蔽返回键的代码: 
    public boolean onKeyDown(int keyCode,KeyEvent event)    
    {    
        switch(keyCode)    
        {    
            case KeyEvent.KEYCODE_BACK:return true;    
        }    
        return super.onKeyDown(keyCode, event);    
    }
	

	
}
