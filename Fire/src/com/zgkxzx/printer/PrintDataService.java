package com.zgkxzx.printer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.zgkxzx.server.DataProcessServer;
import com.zgkxzx.sth.DevSqlSevice;
import com.zgkxzx.sth.SensorLog;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class PrintDataService {
	private Context context = null;
	private String deviceAddress = null;
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();
	private BluetoothDevice device = null;
	private static BluetoothSocket bluetoothSocket = null;
	private static OutputStream outputStream = null;
	private static final UUID uuid = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private boolean isConnection = false;
	final String[] items = { "��λ��ӡ��", "��׼ASCII����", "ѹ��ASCII����", "���岻�Ŵ�",
			"��߼ӱ�", "ȡ���Ӵ�ģʽ", "ѡ��Ӵ�ģʽ", "ȡ�����ô�ӡ", "ѡ���ô�ӡ", "ȡ���ڰ׷���", "ѡ��ڰ׷���",
			"ȡ��˳ʱ����ת90��", "ѡ��˳ʱ����ת90��" };
	final byte[][] byteCommands = { 
			{ 0x1b, 0x40 },// ��λ��ӡ��
			{ 0x1b, 0x4d, 0x00 },// ��׼ASCII����
			{ 0x1b, 0x4d, 0x01 },// ѹ��ASCII����
			{ 0x1d, 0x21, 0x00 },// ���岻�Ŵ�
			{ 0x1d, 0x21, 0x11 },// ��߼ӱ�
			{ 0x1b, 0x45, 0x00 },// ȡ���Ӵ�ģʽ
			{ 0x1b, 0x45, 0x01 },// ѡ��Ӵ�ģʽ
			{ 0x1b, 0x7b, 0x00 },// ȡ�����ô�ӡ
			{ 0x1b, 0x7b, 0x01 },// ѡ���ô�ӡ
			{ 0x1d, 0x42, 0x00 },// ȡ���ڰ׷���
			{ 0x1d, 0x42, 0x01 },// ѡ��ڰ׷���
			{ 0x1b, 0x56, 0x00 },// ȡ��˳ʱ����ת90��
			{ 0x1b, 0x56, 0x01 },// ѡ��˳ʱ����ת90��
	};
		
	public PrintDataService(Context context, String deviceAddress) {
		super();
		this.context = context;
		this.deviceAddress = deviceAddress;
		this.device = this.bluetoothAdapter.getRemoteDevice(this.deviceAddress);
		
		
	}

	/**
	 * ��ȡ�豸����
	 * 
	 * @return String
	 */
	public String getDeviceName() {
		return this.device.getName();
	}

	/**
	 * ���������豸
	 */
	public boolean connect() {
		if (!this.isConnection) {
			try {
				bluetoothSocket = this.device
						.createRfcommSocketToServiceRecord(uuid);
				bluetoothSocket.connect();
				outputStream = bluetoothSocket.getOutputStream();
				this.isConnection = true;
				if (this.bluetoothAdapter.isDiscovering()) {
					System.out.println("�ر���������");
					this.bluetoothAdapter.isDiscovering();
				}
			} catch (Exception e) {
				Toast.makeText(this.context, "����ʧ�ܣ�", 1).show();
				return false;
			}
			Toast.makeText(this.context, this.device.getName() + "���ӳɹ���",
					Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return true;
		}
	}

	/**
	 * �Ͽ������豸����
	 */
	public static void disconnect() {
		System.out.println("�Ͽ������豸����");
		try {
			bluetoothSocket.close();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * ѡ��ָ��
	 */
	public void selectCommand() {
		new AlertDialog.Builder(context).setTitle("��ѡ��ָ��")
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (isConnection) {
							try {
								outputStream.write(byteCommands[which]);

							} catch (IOException e) {
								Toast.makeText(context, "����ָ��ʧ�ܣ�",
										Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(context, "�豸δ���ӣ����������ӣ�",
									Toast.LENGTH_SHORT).show();
						}
					}
				}).create().show();
	}

	/**
	 * ��������
	 */
	public void send(String sendData) {
		if (this.isConnection) {
			
			StringBuffer bf = new StringBuffer();
			List<String> recodeData = new ArrayList<String>();
			DevSqlSevice devSql = new DevSqlSevice(this.context);
			

			List<SensorLog> sensorInfo = devSql.getLogData(0, 9);
			
			bf.append("----������������ϵͳ�쳣��־----\n\n");
			bf.append("��ע��"+sendData+"\n");
			
			
			
			for(int i=0;i<devSql.getLogCount();i++)
			{
				String nodeTime = sensorInfo.get(i).getLogTime();
				String[] infoTime =  nodeTime.split("#");
				if(!(recodeData.contains(infoTime[0])))
				{
					recodeData.add(infoTime[0]);
					bf.append("          "+infoTime[0]+"\n");
					//bf.append("  ʱ��   ����     ̽ͷ��Ϣ  \n");
				}
				
				for(int n=1;n<=16;n++)
				{
					
					
					String nodeName = sensorInfo.get(i).getName()+"-"+Integer.toString(n);
					Character a  = sensorInfo.get(i).getDetail().charAt(n-1);
					if(!a.equals('3')){
						
						String sTemp = a.toString();
						bf.append("-----------------------------\n");
						bf.append("���ƣ� "+nodeName+"\n");
						bf.append("ʱ�䣺 "+infoTime[1]+"\n");
						bf.append("״̬�� "+sTemp.replace("0", "����").replace("1", "����").replace("2", "����")+"\n");
						bf.append("�ص㣺 "+devSql.getNodeConfigInfo(nodeName).getAddrName()+"\n");
						
						
						
					}
					
					
					
				}
				
				
				//bf.append(infoTime[1]+"  "+sensorInfo.get(i).getName()+"    "
				     //+sensorInfo.get(i).getDetail().replace("1","N").replace("2", "Q").replace("3", "X")+"\n");	
			}
			//bf.append("\n     --------˵��--------\n");
			//bf.append("\nN:����  Q������   X������\n");
			bf.append("\n******�人�����Ƽ����޹�˾******\n");
			bf.append("------------��ӡ���------------\n\n\n");
		
			System.out.println("��ʼ��ӡ����");
			try {
				byte[] data = bf.toString().getBytes("gbk");
				//byte[] data = sendData.getBytes("gbk");
				outputStream.write(data, 0, data.length);
				outputStream.flush();
				
			} catch (IOException e) {
				Toast.makeText(this.context, "����ʧ�ܣ�", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(this.context, "�豸δ���ӣ����������ӣ�", Toast.LENGTH_SHORT)
					.show();

		}
	}

}