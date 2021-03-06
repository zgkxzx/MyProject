package com.zgkxzx.sth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zgkxzx.mysql.DBOpenHelper;

public class DevSqlSevice {
	
	private final String TAG = "DevSqlSevice";
	private DBOpenHelper dbOpenHelper;
	
	private SimpleDateFormat sDateFormat;
	
	public DevSqlSevice(Context context){
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd#hh:mm:ss");
		dbOpenHelper = new DBOpenHelper(context);
	}
	//日志相关操作
	public void saveLog(SensorDevice dev){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put("id", dev.getId());
		values.put("time",sDateFormat.format(new java.util.Date()));
		values.put("name", dev.getLayer()+"-"+dev.getName());
		values.put("detail", dev.getSensorsStatus());
		
		db.insert("log", null, values); 
		db.close();
	}
	public List<SensorLog> getLogData(int offset, int maxResult){
		List<SensorLog> sensors = new ArrayList<SensorLog>();
		
		long l = getLogCount();
		
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
	
		Cursor cursor = db.rawQuery("select * from log limit ?,?", 
				new String[]{String.valueOf(offset), String.valueOf(l)});
		while(cursor.moveToNext()){
			String subTime = cursor.getString(cursor.getColumnIndex("time"));
			String subName = cursor.getString(cursor.getColumnIndex("name"));
			String subDetail = cursor.getString(cursor.getColumnIndex("detail"));
		
			sensors.add(new SensorLog(subTime,subName,subDetail));
			
		}
		db.close();
		return sensors;
	}
	public long getLogCount(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("log", new String[]{"count(*)"} , null, null, null, null, null);
		cursor.moveToFirst();
		
		long count = cursor.getLong(0);
		//db.close();
		return count;
	}
	
	//保存具体详细节点信息对应查询表到数据库
	public void saveDatailNodeConfig(NodeConfig nc){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put("id", dev.getId());
		values.put("nodeName", nc.getNodeName());
		values.put("addressName",nc.getAddrName());

		db.insert("addrInfo", null, values); 
		db.close();
	}
	
	public  NodeConfig getNodeConfigInfo(String name){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		String nodeName = null;
		String addressName = "无配置信息";
		Cursor cursor = db.query("addrInfo", new String[]{"infoid","nodeName","addressName"}, 
				"nodeName=?", new String[]{name.toString()}, null, null, null, null);
		if(cursor.moveToFirst()){
			
			nodeName = cursor.getString(cursor.getColumnIndex("nodeName"));
			addressName = cursor.getString(cursor.getColumnIndex("addressName"));
		
			cursor.close();
			
			
		}
		db.close();
		return new NodeConfig(nodeName,addressName);
	}
	//清除地址配置信息的数据库操作
	public void clearConfig()
	{
		
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from addrInfo;");
		
        String sql ="update sqlite_sequence set seq = 0 where name ='"+"addrInfo"+"'";
		
		db.execSQL(sql);
		db.close();
	}
	//清除子机的数据库操作
	public void clearTab()
	{
		
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from subMachine;");
		
        String sql ="update sqlite_sequence set seq = 0 where name ='"+"subMachine"+"'";
		
		db.execSQL(sql);
		db.close();
	}
	//清除日志的数据库操作
	public void clearLogHistory()
	{
		
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from log;");
		
        String sql ="update sqlite_sequence set seq = 0 where name ='"+"log"+"'";
		
		db.execSQL(sql);
		db.close();
	}
	/////////////////////////////////////////////////////////////////////////////////
	//从数据库中得到所有元素，并以list的形式返回
	public List<LayerTel> getLayerTelList(int offset, int maxResult){
		List<LayerTel> layerlist = new ArrayList<LayerTel>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		long l = getLayerTelCount();
		if(l==0)
		{
			db.close();
			return null;
		}
			
		Cursor cursor = db.rawQuery("select * from layerTel limit ?,?", 
				new String[]{String.valueOf(offset), String.valueOf(l)});
		while(cursor.moveToNext()){
			String layer = cursor.getString(cursor.getColumnIndex("layerid"));
			String name = cursor.getString(cursor.getColumnIndex("layerName"));
			String telNum = cursor.getString(cursor.getColumnIndex("telNum"));
		
			layerlist.add(new LayerTel(layer,name,telNum));
		}
		db.close();
		return layerlist;
	}
	//保存楼层电话到数据库
	public void saveLayerTel(LayerTel lt){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("layerid", lt.getLayerNo());
		values.put("layerName", lt.getLayerName());
		values.put("telNum", lt.getTel());
		db.insert("layerTel", null, values); 
		db.close();
      }
	public void updateLayerTel(LayerTel lt){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("layerid", lt.getLayerNo());
		values.put("layerName", lt.getLayerName());
		values.put("telNum", lt.getTel());

		db.update("layerTel", values, "layerid=?", new String[]{lt.getLayerNo()});
		db.close();
	}
	//删除楼层电话从数据库
	public void deleteLayerTel(Integer id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.delete("layerTel", "layerid=?", new String[]{id.toString()});
		db.close();
	}
	//得到某一楼的信息
	public LayerTel findLayerTel(Integer layer){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("layerTel", new String[]{"telid","layerid","layerName","telNum"}, 
				"layerid=?", new String[]{layer.toString()}, null, null, null, null);
		if(cursor.moveToFirst()){
			String layerId = cursor.getString(cursor.getColumnIndex("layerid"));
			String name = cursor.getString(cursor.getColumnIndex("layerName"));
			String telNum = cursor.getString(cursor.getColumnIndex("telNum"));
			cursor.close();
			db.close();
			return new LayerTel(layerId,name,telNum);
		}
		db.close();
		return null;
	}
	//楼层信息的数量
	public long getLayerTelCount(){
		long count = 0;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("layerTel", new String[]{"count(*)"} , null, null, null, null, null);
		cursor.moveToFirst();
		count = cursor.getLong(0);
		//db.close();
		return count;
	}
	
    ////子机相关的数据库操作////////////////////////////////////////////
	//保存节点信息到数据库
	public void save(SensorDevice dev){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			//values.put("id", dev.getId());
			values.put("layer", dev.getLayer());
			values.put("name", dev.getName());
			values.put("PowerMode", dev.getPowerMode());
			values.put("power", dev.getPower());
			values.put("sensorsStatus", dev.getSensorsStatus());
			//values.put("sensorsType", dev.getSensorsType());
			values.put("sensorsType", "3212132312312121");
			
			values.put("devicesStatus", dev.getDevicesStatus());
			
			db.insert("subMachine", null, values); 
			db.close();
	}
	
	public void update(SensorDevice dev){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		//values.put("sensorid", dev.getId());
		values.put("layer", dev.getLayer());
		values.put("name", dev.getName());
		values.put("PowerMode", dev.getPowerMode());
		values.put("power", dev.getPower());
		values.put("sensorsStatus", dev.getSensorsStatus());
		//values.put("sensorsType", dev.getSensorsType());
		values.put("sensorsType", "1231231231231231");
		values.put("devicesStatus", dev.getDevicesStatus());
		
		db.update("subMachine", values, "layer=?", new String[]{dev.getName()});
		db.close();
	}
	
	public void delete(Integer id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.delete("subMachine", "subid=?", new String[]{id.toString()});
		db.close();
	}
	
	public SensorDevice find(Integer id){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("subMachine", new String[]{"subid","layer","name","PowerMode","power","sensorsStatus","sensorsType"}, 
				"subid=?", new String[]{id.toString()}, null, null, null, null);
		if(cursor.moveToFirst()){
			int subid = cursor.getInt(cursor.getColumnIndex("subid"));
			String layer = cursor.getString(cursor.getColumnIndex("layer"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String PowerMode = cursor.getString(cursor.getColumnIndex("PowerMode"));
			String power = cursor.getString(cursor.getColumnIndex("power"));
			String sensorsStatus = cursor.getString(cursor.getColumnIndex("sensorsStatus"));
			String sensorsType = cursor.getString(cursor.getColumnIndex("sensorsType"));
			cursor.close();
			db.close();
			return new SensorDevice(String.valueOf(subid),layer, name, PowerMode, power,sensorsStatus,sensorsType);
		}
		db.close();
		return null;
	}
	/*
	 *offset： 从哪儿开始
	 *maxResult : 最大条目
	 * */
	public List<SensorDevice> getScrollData(int offset, int maxResult){
		List<SensorDevice> sensors = new ArrayList<SensorDevice>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
	
		Cursor cursor = db.rawQuery("select * from subMachine limit ?,?", 
				new String[]{String.valueOf(offset), String.valueOf(maxResult)});
		while(cursor.moveToNext()){
			int subid = cursor.getInt(cursor.getColumnIndex("subid"));
			String layer = cursor.getString(cursor.getColumnIndex("layer"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String PowerMode = cursor.getString(cursor.getColumnIndex("PowerMode"));
			String power = cursor.getString(cursor.getColumnIndex("power"));
			String sensorsStatus = cursor.getString(cursor.getColumnIndex("sensorsStatus"));
			String sensorsType = cursor.getString(cursor.getColumnIndex("sensorType"));
			sensors.add(new SensorDevice(String.valueOf(subid),layer, name, PowerMode, power,sensorsStatus,sensorsType));
		}
		db.close();
		return sensors;
	}
	//相同名的放在一个集合里面
	public List<SensorDevice> getCommonAttrNode(String layerName)
	{
		List<SensorDevice> sensorDev = new ArrayList<SensorDevice>();
		
		long count = getCount();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select * from subMachine limit ?,?", 
				new String[]{String.valueOf(0), String.valueOf(count)});
		while(cursor.moveToNext())
		{
			
			//Log.d(TAG, cursor.getString(cursor.getColumnIndex("layer")));
			String comLayer = cursor.getString(cursor.getColumnIndex("layer"));
			if(Integer.parseInt(comLayer)==Integer.parseInt(layerName))
			{
				int subid = cursor.getInt(cursor.getColumnIndex("subid"));
				String layer = cursor.getString(cursor.getColumnIndex("layer"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String powerMode = cursor.getString(cursor.getColumnIndex("powerMode"));
				String power = cursor.getString(cursor.getColumnIndex("power"));
				String sensorsStatus = cursor.getString(cursor.getColumnIndex("sensorsStatus"));
				String sensorsType = cursor.getString(cursor.getColumnIndex("sensorsType"));
				String devicesStatus = cursor.getString(cursor.getColumnIndex("devicesStatus"));
				sensorDev.add(new SensorDevice(String.valueOf(subid),layer, name, powerMode, power,sensorsStatus,sensorsType,devicesStatus));
			}
			
		}
		
		
		cursor.close();
		db.close();
		
		return sensorDev;
	}
	public long getCount(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("subMachine", new String[]{"count(*)"} , null, null, null, null, null);
		cursor.moveToFirst();
		long count = cursor.getLong(0);
		db.close();
		return count;
	}

	
}
