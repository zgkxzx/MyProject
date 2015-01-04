package com.zgkxzx.activity;

import com.zgkxzx.activity.R;
import com.zgkxzx.sth.DevSqlSevice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Settings extends Activity {
	
	
	private EditText ivSettingsLayer;
	private ImageButton btn_clear;
	private String layer = "";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏
		this.setTitle("系统配置");
		this.setContentView(R.layout.activity_settings);
				 
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏 
	    
	    
		
	    ivSettingsLayer = (EditText)this.findViewById(R.id.et_layer);
	    
	    btn_clear = (ImageButton)findViewById(R.id.settings_clear);         
	    btn_clear.setOnTouchListener(new View.OnTouchListener(){            
		    public boolean onTouch(View v, MotionEvent event) {
		    	
		    	  
		            if(event.getAction() == MotionEvent.ACTION_DOWN){       
		  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.clear_press));
		               
		               DevSqlSevice dsl = new DevSqlSevice(Settings.this);
		               dsl.clearLogHistory();
		               Toast.makeText(Settings.this, "历史记录清除", Toast.LENGTH_SHORT).show(); 
		             
		              
		            }else if(event.getAction() == MotionEvent.ACTION_UP){       
		               
		                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.clear));
		                
		                
		            }          
		            return false;       
		    }
			
		}); 
	    
	    
	   
	   
	   	   
	}
	
		
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_settings", Activity.MODE_PRIVATE);
		layer = sharedSettings.getString("layer","");
		ivSettingsLayer.setText(layer);
		
    	
	}





	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
		SharedPreferences sharedSettings = super.getSharedPreferences("zgkxzx_settings", Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedSettings.edit();
    	
    	layer = ivSettingsLayer.getText().toString();
    	
    	editor.putString("layer",layer.replace(" ", ""));
    	
    	editor.commit();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(Settings.this, MyActivity.class);
			setResult(RESULT_OK, intent);
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			return false;
		}
		return false;
	}
}
