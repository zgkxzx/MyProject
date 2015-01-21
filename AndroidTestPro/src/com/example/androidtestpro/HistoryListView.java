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
	// ��LayoutInflater���ڽ�һ��XML�����ļ�ʵ����Ϊһ��View����
	LayoutInflater layoutInflater;
	Context context;
	//private DevSqlSevice dsl;
	
	// ���캯��������Ϊ�б���� �� Context
	public HistoryListView(List<LayerTel> list, Context context) {
		this.list = list;
		this.context = context;
		//dsl= new DevSqlSevice(context);	
	}
	//����б�Ԫ������
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	//����б�Ԫ��
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	//����б�Ԫ�ص�id
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	// ��ȡ��ʾ���ݵ��б��View����
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			// �Ӹ�����context�л�ȡLayoutInflater����
			layoutInflater = LayoutInflater.from(context);
			// ����inflate() - ���ض���XML�����ļ�inflate���ͳ�һ���µ���ͼ
			convertView = layoutInflater.inflate(R.layout.list_item,null);
			
			holder = new ViewHolder();
			holder.listImage = (ImageView)convertView.findViewById(R.id.list_image);
			holder.firstTitle = (TextView)convertView.findViewById(R.id.list_main_title);
			holder.secondtTitle = (TextView)convertView.findViewById(R.id.list_second_title);
			holder.thirdTitle = (TextView)convertView.findViewById(R.id.list_third_title);
		
			
			// Ϊһ��View��ӱ�ǩ����ǩ������ViewHolder�ж��壬���Կ�����һ���󶨲���
			convertView.setTag(holder);
		} else 
			holder = (ViewHolder) convertView.getTag();
		
		
		if(list.get(position).getTel().equals("no data"))
			 holder.listImage.setImageResource(R.drawable.config_call_gray);
		else
		   holder.listImage.setImageResource(R.drawable.config_call_green);
				
		holder.firstTitle.setText("��"+list.get(position).getLayerNo()+"¥");

		holder.secondtTitle.setText(list.get(position).getLayerName());
		
		holder.thirdTitle.setText("Tel: "+list.get(position).getTel());
      
		
		return convertView;
	}
}
//���ڴ��ÿһ������Ԫ�ص���
class ViewHolder {
	TextView firstTitle;
	TextView secondtTitle;
	TextView thirdTitle;
	ImageView listImage;
}


