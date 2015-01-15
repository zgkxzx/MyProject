package com.zgkxzx.printer;

import com.zgkxzx.activity.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ListView;



public class BluetoothActivity extends Activity {

	 private Context context = null;    
	    
	    public void onCreate(Bundle savedInstanceState) {    
	        super.onCreate(savedInstanceState);    
	        this.context = this;    
	        setTitle("À¶ÑÀ´òÓ¡");
	        setContentView(R.layout.bluetooth_layout);    
	        this.initListener();    
	    }    
	    
	    private void initListener() {    
	        ListView unbondDevices = (ListView) this    
	                .findViewById(R.id.unbondDevices);    
	        ListView bondDevices = (ListView) this.findViewById(R.id.bondDevices);
	        
	        Button switchBT = (Button) this.findViewById(R.id.openBluetooth_tb);
	        
	        Button searchDevices = (Button) this.findViewById(R.id.searchDevices);    
	    
	        BluetoothAction bluetoothAction = new BluetoothAction(this.context,    
	                unbondDevices, bondDevices, switchBT, searchDevices,    
	                BluetoothActivity.this);    
	    
	        Button returnButton = (Button) this    
	                .findViewById(R.id.return_Bluetooth_btn);
	        
	        bluetoothAction.setSearchDevices(searchDevices);
	        
	        bluetoothAction.initView();    
	    
	        switchBT.setOnClickListener(bluetoothAction);    
	        searchDevices.setOnClickListener(bluetoothAction);    
	        returnButton.setOnClickListener(bluetoothAction);    
	    }    
	    //ÆÁ±Î·µ»Ø¼üµÄ´úÂë: 
	    /*
	    public boolean onKeyDown(int keyCode,KeyEvent event)    
	    {    
	        switch(keyCode)    
	        {    
	            case KeyEvent.KEYCODE_BACK:return true;    
	        }    
	        return super.onKeyDown(keyCode, event);    
	    }*/

		    

}
