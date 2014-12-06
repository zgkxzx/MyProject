package com.zgkxzx.activity;

import java.util.List;

import com.zgkxzx.activity.R;



import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGirdView extends BaseAdapter {
	
	private List<PackageInfo> packageInfos = null;
	private LayoutInflater inflater = null; //inflater的作用是将xml文件转换成视图
	private  Context context = null;
	private ImageView iv;
	
	public MyGirdView(List<PackageInfo>  packageInfos , Context context){
		this.packageInfos = packageInfos; 
		this.context = context ; 
		inflater = LayoutInflater.from(context);
		
	}
	
	
     //packageInfos.size()返回集合里元素的数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return packageInfos.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return packageInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.gridviewitem, null);
		TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
		ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);
		//tv.setText(packageInfos.get(position).applicationInfo.loadLabel(context.getPackageManager()));
		tv.setText("探头"+new Integer(position).toString());
		//iv.setImageDrawable(packageInfos.get(position).applicationInfo.loadIcon(context.getPackageManager()));
		if(position<2)
		{
			iv.setImageResource(R.drawable.node_red);
		}else
			iv.setImageResource(R.drawable.node_icon);
		
		return view;
	}

}
