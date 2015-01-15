package com.zgkxzx.myfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileService {
	private Context context;

	public FileService(Context context) {
		this.context = context;
	}

	public void saveToSDCard(String filename, String content) throws Exception{
		
		
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		
		//File file = new File("/storage/sdcard1", filename);
		
		
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void save(String filename, String content) throws Exception{
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void saveAppend(String filename, String content) throws Exception{// ctrl+shift+y
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_APPEND);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void saveReadable(String filename, String content) throws Exception{
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void saveWriteable(String filename, String content) throws Exception{
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_WORLD_WRITEABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public void saveRW(String filename, String content) throws Exception{
		FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_WORLD_WRITEABLE + Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public String getContent(String filename) throws Exception{
		
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		FileInputStream inStream = new FileInputStream(file);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		inStream.close();
		outStream.close();
		return new String(data,"GBK");
	}
}