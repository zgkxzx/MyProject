package com.zgkxzx.activity;

import java.util.List;

import com.zgkxzx.activity.R;
import com.zgkxzx.sth.SensorDevice;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NodeGirdView extends BaseAdapter {
	
	private final String TAG = "NodeGirdView";
	private List<SensorDevice> sensorDevice = null;
	private LayoutInflater inflater = null; //inflater的作用是将xml文件转换成视图
	private  Context context = null;
	private ImageView iv;
	
	public NodeGirdView(List<SensorDevice>  sensorDevice , Context context){
		this.sensorDevice = sensorDevice; 
		this.context = context ; 
		inflater = LayoutInflater.from(context);
		
	}
		
     //packageInfos.size()返回集合里元素的数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sensorDevice.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return sensorDevice.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.gridviewitem, null);
		TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
		ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);
		tv.setText("子机"+sensorDevice.get(position).getName());
		
		String nodeStatusInfo = sensorDevice.get(position).getSensorsStatus();
		
		int temp = Integer.parseInt(sensorDevice.get(position).getName());
		if((temp!=0)&&(temp%25==0))
		{
			iv.setImageResource(R.drawable.node_box);
			//Log.d(TAG, "R.drawable.node_box!");
		}else 
		{
			if(nodeStatusInfo!=null)
			{
				if(nodeStatusInfo.contains("1"))
					iv.setImageResource(R.drawable.node_red);
				else if(nodeStatusInfo.contains("2"))
					iv.setImageResource(R.drawable.node_yellow);
				else if(nodeStatusInfo.contains("0"))
					iv.setImageResource(R.drawable.node_black);
				else 
					iv.setImageResource(R.drawable.node_green);
				
				
			}
		}
		
		return view;
	}

}
