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
	// ��LayoutInflater���ڽ�һ��XML�����ļ�ʵ����Ϊһ��View����
	LayoutInflater layoutInflater;
	Context context;
	private DevSqlSevice dsl;
	
	// ���캯��������Ϊ�б���� �� Context
	public HistoryListView(List<SensorLog> list, Context context) {
		this.list = list;
		this.context = context;
		dsl= new DevSqlSevice(context);	
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
			convertView = layoutInflater.inflate(R.layout.proc_list_item, null);
			
			holder = new ViewHolder();
			holder.image = (ImageView)convertView.findViewById(R.id.image_app);
			holder.nameText = (TextView)convertView.findViewById(R.id.name_app);
			holder.processName = (TextView)convertView.findViewById(R.id.package_app);
			holder.memInfo = (TextView)convertView.findViewById(R.id.cpumem_app);
			
			// Ϊһ��View��ӱ�ǩ����ǩ������ViewHolder�ж��壬���Կ�����һ���󶨲���
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final SensorLog sensorLog = list.get(position);
	
		
		
		holder.nameText.setText(sensorLog.getName()+" : "+dsl.getNodeConfigInfo(sensorLog.getName()+"-0").getAddrName());

		holder.processName.setText(sensorLog.getLogTime().replace("#", "/"));
        String detail = sensorLog.getDetail();
        
		holder.memInfo.setText(detail.replace("0", "����-").replace("1", "����-").replace("2", "����-").replace("3", "����-"));
		if(detail.contains("1"))
			holder.image.setImageResource(R.drawable.warning);
		else if(detail.contains("2"))
			holder.image.setImageResource(R.drawable.error);
		
		return convertView;
	}
}
//���ڴ��ÿһ������Ԫ�ص���
class ViewHolder {
	TextView nameText;
	TextView processName;
	TextView memInfo;
	ImageView image;
}

