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

public class MenuGirdView extends BaseAdapter{
	
	private List<MyMenuIcon> myMenuItem = null;
	private LayoutInflater inflater = null; //inflater的作用是将xml文件转换成视图
	private  Context context = null;
	
	public MenuGirdView(List<MyMenuIcon>  myMenuItem , Context context){
		this.myMenuItem = myMenuItem; 
		this.context = context ; 
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return myMenuItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myMenuItem.get(position);
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
		
		tv.setText(myMenuItem.get(position).getTitle());
		iv.setImageResource(myMenuItem.get(position).getIcon());
		
		return view;
	}
	
	

}
