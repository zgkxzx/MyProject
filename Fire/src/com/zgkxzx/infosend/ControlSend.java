package com.zgkxzx.infosend;

import com.zgkxzx.mymath.CRC16;

public class ControlSend {
	
	public static final int ROLLDOOR = 7; //æÌ¡Ÿ√≈
	public static final int FIREPLUG = 6; //œ˚∑¿À®
	public static final int FUN = 5;
	public static final int INTERRUPTPOWER = 4;
	public static final int LIFT = 3;
	public static final int BROAST = 2;
	public static final int MOTORDOOR = 1;
	public static final int RES = 0;
	
	public static final byte DEVICE_ON = (byte)0xff;
	public static final byte DEVICE_OFF = (byte)0x00;
	
	
	public static final byte NODE_MAIN_DATA = (byte)0x00;
	public static final byte NODE_LITTLE_DATA = (byte)0x01;
	
	
	public static byte [] sendCommand(String layer,String name,byte cmd)
	{
		
		
		byte [] buffer = new byte[5];
		buffer[0]=(byte)0xff;
		buffer[1]=(byte)Integer.parseInt(layer);
		buffer[2]=(byte)Integer.parseInt(name);
		buffer[3]=(byte)0x05;
		buffer[4]=(byte)cmd;
		return CRC16.data2send(buffer);
		
	}
	
	
	public static byte [] sendAllWarn(String layer,String name,String DetailAddr)
	{
		
		
		byte [] buffer = new byte[36];
		buffer[0]=(byte)0xff;
		buffer[1]=(byte)Integer.parseInt(layer);
		buffer[2]=(byte)Integer.parseInt(name);
		buffer[3]=(byte)0x06;
		
		byte [] b = DetailAddr.getBytes();
		
		 
		System.arraycopy(b, 0, buffer, 4, b.length);
				
		return CRC16.data2send(buffer);
		
	}
	
	public static byte [] sendControl(String layer,String name,int type,byte sw)
	{
		
		
		byte [] buffer = new byte[5];
		buffer[0]=(byte)0xff;
		buffer[1]=(byte)Integer.parseInt(layer);
		buffer[2]=(byte)Integer.parseInt(name);
		buffer[3]=(byte)0x04;
		buffer[4]=(byte)getSWCode(type,sw);
		return CRC16.data2send(buffer);
		
	}
	private static byte getSWCode(int type,byte sw)
	{
		byte code = 0;
		if(sw==DEVICE_ON)
		{
			code = (byte)(0x80|(type));
		}else if(sw==DEVICE_OFF)
		{
			code = (byte)(type);
		}
		return code;
		
	}
	public static byte getSW(boolean b)
	{
		if(b)
			return DEVICE_ON;
		else
		   return DEVICE_OFF;
		
	}
	

}
