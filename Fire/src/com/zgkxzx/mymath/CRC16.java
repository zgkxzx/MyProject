package com.zgkxzx.mymath;



import android.util.Log;

public class CRC16 {
	
	public static String strings2CRC16(String value)
    {  
		
            long data = GetCRC(value.replace(" ", ""));
            int h1, l0;
            l0 = (int) data / 256;
            h1 = (int) data % 256;
            String s = "";
            if (Integer.toHexString(h1).length() < 2)
            {
                    s = "0" + Integer.toHexString(h1);
            } else
            {
                    s = Integer.toHexString(h1);
            }
            if (Integer.toHexString(l0).length() < 2)
            {
                    s = s + "0" + Integer.toHexString(l0);
            } else
            {
                    s = s + Integer.toHexString(l0);
            }
            return s;
    }

    private static int[] str2HexByte(String hexString)
    {
            hexString = hexString.replace(" ", "");
            // ������Ȳ���ż������ô������ӿո�

            if ((hexString.length() % 2) != 0)
            {
                    hexString += " ";
            }

            // �������飬����Ϊ��ת���ַ������ȵ�һ�롣
            int[] returnBytes = new int[hexString.length() / 2];

            for (int i = 0; i < returnBytes.length; i++)
                    //����Ϊʲô����ָ�����?
                    returnBytes[i] =  (0xff & Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16));
            return returnBytes;
    }

    private static long GetCRC(String DATA)
    {
            long functionReturnValue = 0;
            long i = 0;

            long J = 0;
            int[] v = null;
            //֮ǰ֮���Դ���,����Ϊ�е����ֱ���Ϊ�Ǹ�����.
            v = str2HexByte(DATA);
            
            long CRC = 0;
            CRC = 0xffffL;
            for (i = 0; i <= (v).length - 1; i++)
            { // 2.�ѵ�һ��8λ���������ݣ���ͨѶ��Ϣ֡�ĵ�һ���ֽڣ���16λ��CRC�Ĵ����ĵ�8λ����򣬰ѽ������CRC�Ĵ�����
                    CRC = (CRC / 256) * 256L + (CRC % 256L) ^ v[(int) i];
                    for (J = 0; J <= 7; J++)
                    {       // 3.��CRC�Ĵ�������������һλ������λ����0����λ����������λ��
                            // 4.������λΪ0���ظ���3�����ٴ�����һλ����
                            // ������λΪ1��CRC�Ĵ��������ʽA001��1010 0000 0000 0001���������
                            // 5.�ظ�����3��4��ֱ������8�Σ���������8λ����ȫ�������˴���
                            long d0 = 0;
                            d0 = CRC & 1L;
                            CRC = CRC / 2;
                            if (d0 == 1)
                                    CRC = CRC ^ 0xa001L;
                    } 
                    // 6.�ظ�����2������5������ͨѶ��Ϣ֡��һ�ֽڵĴ���
            } // 7.���õ���CRC�Ĵ������ݼ�Ϊ��CRC�롣
            CRC = CRC % 65536;
            functionReturnValue = CRC;
            return functionReturnValue;
    }
    
    private static final char[] HexChar = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    
    public static String byte2char(byte [] data)
    {
    	String s= "";
    	int num=0;
    	for(byte b:data)
    	{
    		if((byte)(b&0x80)==(byte)0x80)
    			num = (int) (256 + b);
    		else
    			num = (int)b;
    		s += HexChar[(int)(num/16)];
    		s += HexChar[(int)(num%16)];
    	}
    	
		return s;
    }
   
   /* public static byte [] data2send(byte [] data)
    {
    	String sDate="";
    	sDate = byte2char(data);
    	String check =strings2CRC16(sDate);
    	
    	sDate += check;
        
    	byte [] temp = new byte[7];
    	
    	
    	int [] bData = str2HexByte(sDate);
    	
		for(int i = 0;i<sDate.length()/2;i++)
    		temp[i] =(byte) bData[i];
    	
    
		return temp;
    	
    }*/
    
    public static byte [] data2send(byte [] data)
    {
    	String sDate="";
    	sDate = byte2char(data);
    	String check =strings2CRC16(sDate);
    	
    	sDate += check;
  
    	int [] bData = str2HexByte(sDate);
    	
    	int byteLength = sDate.length()/2;
    	
    	byte [] temp = new byte[byteLength];
    	
		for(int i = 0;i<byteLength;i++)
    		temp[i] =(byte) bData[i];
    	
    
		return temp;
    	
    }

}
