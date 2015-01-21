

package android_serialport_api;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import android.app.Application;
import android.util.Log;


public class MyApplication extends Application {

	private static final String TAG = "MyApplication";
	private SerialPort mSerialPort = null;
	
    private  boolean sendCommandFlag = true;//���������־
    private  boolean dataServiceFlag = true;//���ݷ��ͱ�־
    private  boolean logReceiveFlag = true; //���չ��ϱ�����־��־
    private  int scanLayer=1;
	
	

	public boolean getLogRevFlag() {
		return logReceiveFlag;
	}

	public void setLogRevFlag(boolean logReceiveFlag) {
		this.logReceiveFlag = logReceiveFlag;
	}

	public boolean isSendCommandFlag() {
		return sendCommandFlag;
	}

	public void setSendCommandFlag(boolean sendCommandFlag) {
		this.sendCommandFlag = sendCommandFlag;
	}

	public boolean isDataServiceFlag() {
		return dataServiceFlag;
	}

	public void setDataServiceFlag(boolean dataServiceFlag) {
		this.dataServiceFlag = dataServiceFlag;
	}

	public int getScanLayer() {
		return scanLayer;
	}

	public void setScanLayer(int scanLayer) {
		this.scanLayer = scanLayer;
	}

	public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
		if (mSerialPort == null) {
			mSerialPort = new SerialPort(new File("/dev/ttySAC1"), 9600, 0);
			Log.d(TAG," ����/dev/ttySAC1 �Ѿ���!");
		}
		return mSerialPort;
	}

	public void closeSerialPort() {
		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
	}
}
