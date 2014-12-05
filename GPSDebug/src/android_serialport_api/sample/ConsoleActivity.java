/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package android_serialport_api.sample;

import java.io.IOException;
import java.text.SimpleDateFormat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ConsoleActivity extends SerialPortActivity {

	private EditText mReception;
	private EditText Emission;
	SimpleDateFormat sDateFormat;
	private TextView btn_gps_sw;
	private TextView btn_sattalte_nums;
	private TextView btn_gps_speed;
	private TextView btn_gps_attitude;
	private TextView btn_gps_to;
	private TextView btn_gps_data;
	private TextView btn_gps_time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//вўВи
		setContentView(R.layout.console);
		
		

		sDateFormat = new SimpleDateFormat("hh:mm:ss");
		
		
		mReception = (EditText) findViewById(R.id.gps_serial_info);
		
		btn_gps_sw = (TextView) findViewById(R.id.gps_sw);
		btn_sattalte_nums = (TextView) findViewById(R.id.sattalte_nums);
		btn_gps_speed = (TextView) findViewById(R.id.gps_speed);
		btn_gps_attitude = (TextView) findViewById(R.id.gps_attitude);
		btn_gps_to = (TextView) findViewById(R.id.gps_to);
		
		btn_gps_data = (TextView) findViewById(R.id.gps_data);
		btn_gps_time = (TextView) findViewById(R.id.gps_time);
		
		btn_gps_sw.setText("on");
		btn_gps_sw.setTextColor(Color.GREEN);

		
		
	
		
	}
	
    String gpsValue=""; 
    int lineNum=0;

	@Override
	protected void onDataReceived(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mReception != null) {
					
					
					
					//gpsValue=new String(buffer, 0, size);
					
					//mReception.append("[Time:"+sDateFormat.format(new java.util.Date())+"]: "+new String(buffer, 0, size)+"\n");
					
					
					//mReception.append(gpsValue);
					//gpsValue.concat(new String(buffer, 0, size));
					gpsValue += new String(buffer, 0, size);
					
					if(gpsValue.indexOf("\r\n")!=-1&&(gpsValue.indexOf("$")!=-1))//
					{
						int endIndex = gpsValue.indexOf("\r\n");

						String secondValue = gpsValue.substring(0, endIndex);
						
						int startIndex = secondValue.indexOf("$");
						
						String elseValue = secondValue.substring(startIndex);
	
						gpsValue= gpsValue.substring(endIndex+1);
						
						mReception.append("[Time:"+sDateFormat.format(new java.util.Date())+"]: "+elseValue+"\n");
						
						
						String[] gpsStr =  elseValue.split(",");
						
						for(int i=0;i<gpsStr.length;i++)
							System.out.print(">>"+new Integer(i).toString()+":"+gpsStr[i]+"<<");
						System.out.println("end");
						if(gpsStr[0].contains("$GPRMC")&&(gpsStr.length>5))
						{
							if(!(gpsStr[1].length()<5)) 
								btn_gps_time.setText(gpsStr[1]);
							//if(!(gpsStr[9].length()<5))
								//btn_gps_data.setText(gpsStr[9]);
							System.out.println("gpsStr[1]:"+gpsStr[1]+"gpsStr[9]:");
							
						}else if(gpsStr[0].contains("$GPGGA"))
						{
							if(!(gpsStr[9].length()<1))
								btn_gps_attitude.setText(gpsStr[9]);
						}else if(gpsStr[0].contains("$GPGSV"))
						{
							if(gpsStr[3].length()<4)
								btn_sattalte_nums.setText(gpsStr[3]+"/12");
						}else if(gpsStr[0].contains("$GPVTG"))
						{
							//if(gpsStr[7].length()<4)
								btn_gps_speed.setText(gpsStr[7]);
						}else if(gpsStr[0].contains("$GPRMC"))
						{
							//if(gpsStr[9].length()>4)
								btn_gps_speed.setText(gpsStr[9]);
						}
						
						
						lineNum++;
						if(lineNum==8)
						{
							mReception.setText("");
							lineNum=0;
						}
					}else 
					{
						
					}
					
					
				}
			}
		});
		
		
	}
}

