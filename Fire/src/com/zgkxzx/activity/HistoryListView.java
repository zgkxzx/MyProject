package com.zgkxzx.activity;


import java.util.ArrayList;
import java.util.List;

import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorLog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryListView extends BaseAdapter{
	List<SensorLog> list = new ArrayList<SensorLog>();
	// 类LayoutInflater用于将一个XML布局文件实例化为一个View对象
	LayoutInflater layoutInflater;
	Context context;
	private DevSqlSevice dsl;
	
	// 构造函数，参数为列表对象 和 Context
	public HistoryListView(List<SensorLog> list, Context context) {
		this.list = list;
		this.context = context;
		dsl= new DevSqlSevice(context);	
	}
	//获得列表元素总数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	//获得列表元素
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	//获得列表元素的id
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	// 获取显示数据的列表的View对象
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			// 从给定的context中获取LayoutInflater对象
			layoutInflater = LayoutInflater.from(context);
			// 方法inflate() - 从特定的XML布局文件inflate膨胀出一个新的视图
			convertView = layoutInflater.inflate(R.layout.proc_list_item, null);
			
			holder = new ViewHolder();
			holder.image = (ImageView)convertView.findViewById(R.id.image_app);
			holder.nameText = (TextView)convertView.findViewById(R.id.name_app);
			holder.processName = (TextView)convertView.findViewById(R.id.package_app);
			holder.memInfo = (TextView)convertView.findViewById(R.id.cpumem_app);
			
			// 为一个View添加标签，标签项在类ViewHolder中定义，可以看作是一个绑定操作
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final SensorLog sensorLog = list.get(position);
	
		
		
		holder.nameText.setText(sensorLog.getName()+" : "+dsl.getNodeConfigInfo(sensorLog.getName()+"-0").getAddrName());

		holder.processName.setText(sensorLog.getLogTime().replace("#", "/"));
        String detail = sensorLog.getDetail();
        
		holder.memInfo.setText(detail.replace("0", "掉线-").replace("1", "报警-").replace("2", "故障-").replace("3", "正常-"));
		if(detail.contains("1"))
			holder.image.setImageResource(R.drawable.warning);
		else if(detail.contains("2"))
			holder.image.setImageResource(R.drawable.error);
		
		return convertView;
	}
}
//用于存放每一行所有元素的类
class ViewHolder {
	TextView nameText;
	TextView processName;
	TextView memInfo;
	ImageView image;
}

