package com.example.androidtestpro;


import java.util.ArrayList;
import java.util.List;

import com.zgkxzx.sellrecoder.LayerTel;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryListView extends BaseAdapter{
	//List<SensorLog> list = new ArrayList<SensorLog>();
	List<LayerTel> list = new ArrayList<LayerTel>();
	// 类LayoutInflater用于将一个XML布局文件实例化为一个View对象
	LayoutInflater layoutInflater;
	Context context;
	//private DevSqlSevice dsl;
	
	// 构造函数，参数为列表对象 和 Context
	public HistoryListView(List<LayerTel> list, Context context) {
		this.list = list;
		this.context = context;
		//dsl= new DevSqlSevice(context);	
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
			convertView = layoutInflater.inflate(R.layout.list_item,null);
			
			holder = new ViewHolder();
			holder.listImage = (ImageView)convertView.findViewById(R.id.list_image);
			holder.firstTitle = (TextView)convertView.findViewById(R.id.list_main_title);
			holder.secondtTitle = (TextView)convertView.findViewById(R.id.list_second_title);
			holder.thirdTitle = (TextView)convertView.findViewById(R.id.list_third_title);
		
			
			// 为一个View添加标签，标签项在类ViewHolder中定义，可以看作是一个绑定操作
			convertView.setTag(holder);
		} else 
			holder = (ViewHolder) convertView.getTag();
		
		
		if(list.get(position).getTel().equals("no data"))
			 holder.listImage.setImageResource(R.drawable.config_call_gray);
		else
		   holder.listImage.setImageResource(R.drawable.config_call_green);
				
		holder.firstTitle.setText("第"+list.get(position).getLayerNo()+"楼");

		holder.secondtTitle.setText(list.get(position).getLayerName());
		
		holder.thirdTitle.setText("Tel: "+list.get(position).getTel());
      
		
		return convertView;
	}
}
//用于存放每一行所有元素的类
class ViewHolder {
	TextView firstTitle;
	TextView secondtTitle;
	TextView thirdTitle;
	ImageView listImage;
}


