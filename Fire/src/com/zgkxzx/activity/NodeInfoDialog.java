package com.zgkxzx.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NodeInfoDialog extends Dialog {
	
	private final String TAG = "NodeInfoDialog";
	
	private Context context;
	
	private TextView layerContent;
	private TextView nodeNameContent;
	
	private TextView powerModeContent;
	
	private ImageView powerIcon;
	private TextView powerContent;
	
	private ImageView  nodesStatus[] = new ImageView[8] ;
	private ImageView testIcon;
	private ImageView  nodesType[];
	
	
	private boolean powerMain=true;
	
	
	
	private static int nodesStatusId[]={
		R.id.node1_status_icon,
		R.id.node2_status_icon,
		R.id.node3_status_icon,
		R.id.node4_status_icon,
		R.id.node5_status_icon,
		R.id.node6_status_icon,
		R.id.node7_status_icon,
		R.id.node8_status_icon,
		R.id.node9_status_icon,
		R.id.node10_status_icon,
		R.id.node11_status_icon,
		R.id.node12_status_icon,
		R.id.node13_status_icon,
		R.id.node14_status_icon,
		R.id.node15_status_icon,
		R.id.node16_status_icon,
			
	};
	private static int nodesTypeId[]={
			R.id.node1_type_icon,
			R.id.node2_type_icon,
			R.id.node3_type_icon,
			R.id.node4_type_icon,
			R.id.node5_type_icon,
			R.id.node6_type_icon,
			R.id.node7_type_icon,
			R.id.node8_type_icon,
			R.id.node9_type_icon,
			R.id.node10_type_icon,
			R.id.node11_type_icon,
			R.id.node12_type_icon,
			R.id.node13_type_icon,
			R.id.node14_type_icon,
			R.id.node15_type_icon,
			R.id.node16_type_icon,
				
		};
	private static int powerId[]={
		R.drawable.power_icon_0,
		R.drawable.power_icon_1,
		R.drawable.power_icon_2,
		R.drawable.power_icon_3,
		R.drawable.power_icon_4,
		R.drawable.power_icon_5,
		R.drawable.power_icon_6,
		R.drawable.power_icon_7,
		R.drawable.power_icon_8,
		R.drawable.power_icon_9,
		R.drawable.power_icon_full,
			
	};

	public NodeInfoDialog(Context context) {
		super(context);
		this.context = context;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=inflater.inflate(R.layout.custom_dialog, null); 
        
        
        nodesStatus = new ImageView[16];
	    nodesType = new ImageView[16];
	    
         powerIcon = (ImageView) view.findViewById(R.id.node_power_icon);
        
		 layerContent = (TextView) view.findViewById(R.id.layer_info_content);
		 nodeNameContent = (TextView) view.findViewById(R.id.node_name_content);
		 powerModeContent = (TextView) view.findViewById(R.id.power_mode_content);
		 powerContent = (TextView) view.findViewById(R.id.node_power_content);
		 
		 	 
		
	    for(int i = 0; i < 16; i++)
		    nodesStatus[i] = (ImageView) view.findViewById(nodesStatusId[i]);
	 
	    for(int i = 0; i < 16; i++)
		    nodesType[i] = (ImageView) view.findViewById(nodesTypeId[i]);
        
	
         setContentView(view);
	}
	public NodeInfoDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
	 
	
	// 设置楼层信息的内容
	public void setLayerContent(String s)
	{
		layerContent.setText(s);
	}
	// 设置节点名字的内容
	public void setNodeNameContent(String s)
	{
		nodeNameContent.setText(s);
	}
	// 设置供电模式的内容
	public void setPowerModeContent(String s,String power)
	{
		//1-->主店  2--->备电
		if(s.equals("1"))
		{
			powerMain=true;
			powerModeContent.setText("主电模式");
		}
		else if(s.equals("2"))
		{
			powerMain=false;
			powerModeContent.setText("备电模式");
			
		}
			
		setPowerContent(power);
		
	}
	// 设置备电状态的内容
	private void setPowerContent(String s)
	{
		if(powerMain)
		{
			powerIcon.setImageResource(R.drawable.power_icon_change);
			powerContent.setText("充电中");
		}else
		{
			int power = Integer.parseInt(s);
			
			if(power<=100)
			powerIcon.setImageResource(powerId[(int)(power/10)]);
			
			powerContent.setText(s+"%");
			
		}
		
	}
	
	// 设置传感器的状态
	public void setSensorsStatus(String s)
	{
		
		char c[] = s.toCharArray();
		for(int i = 0; i<16 ;i++)
		switch(c[i])
		{
		case '0': 
			nodesStatus[i].setImageResource(R.drawable.node_unlink);	
			break;
		case '1':
			nodesStatus[i].setImageResource(R.drawable.node_warning);	
			break;
		case '2':
			nodesStatus[i].setImageResource(R.drawable.node_error);
			break;
		case '3':
			nodesStatus[i].setImageResource(R.drawable.node_normal);
			break;
		
		default:break;
		}
			
		
	}
	// 设置传感器的类型
	public void setSensorsType(String s)
	{
		char c[] = s.toCharArray();
		for(int i = 0; i<16 ;i++)
		switch(c[i])
		{
		case '0':
			nodesType[i].setImageResource(R.drawable.node_unlink);
			break;
		case '1':
			nodesType[i].setImageResource(R.drawable.node_normal);
			break;
		case '2':
			nodesType[i].setImageResource(R.drawable.node_error);
			break;
		case '3':
			nodesType[i].setImageResource(R.drawable.node_warning);
			break;
		
		default:break;
		}
		
	}

	 
}
