package com.zgkxzx.fileoperate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileOperate {
	
	private final String TAG = "FileOperate";
	private Context context;

	public FileOperate(Context context) {
		this.context = context;
	}
	
    public String getConfigFile(String filename) {
		
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			Log.d(TAG, "û���ҵ���Ӧ���ļ�");
		
		}
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while( (len = inStream.read(buffer)) != -1){
				outStream.write(buffer, 0, len);
			}
		} catch (IOException e) {
			Log.d(TAG, "IOException ��д�쳣");
		}
		byte[] data = outStream.toByteArray();
		try {
			inStream.close();
		} catch (IOException e) {
			
			Log.d(TAG, "inStream �ر��쳣");
		}
		try {
			outStream.close();
		} catch (IOException e) {
			
			Log.d(TAG, "outStream �ر��쳣");
		}
		String reString =null;
		try {
			reString =  new String(data,"GBK");
		} catch (UnsupportedEncodingException e) {
			
			Log.d(TAG, "��֧�ֵı��뷽ʽ�쳣");
		}
		return reString;
	}
	
	
	
	

}
