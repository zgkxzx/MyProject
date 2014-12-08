package com.zgkxzx.sth;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zgkxzx.mysql.DBOpenHelper;

public class DevSqlSevice {
	private DBOpenHelper dbOpenHelper;
	
	public DevSqlSevice(Context context){
		dbOpenHelper = new DBOpenHelper(context);
	}
	

	public void save(SensorDevice dev){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put("id", dev.getId());
		values.put("layer", dev.getLayer());
		values.put("name", dev.getName());
		values.put("mainPowerStatus", dev.getMainPowerStatus());
		values.put("power", dev.getPower());
		values.put("sensorType", dev.getSensorType());
		
		db.insert("sensor", null, values); 
		db.close();
	}
	
	public void update(SensorDevice dev){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put("sensorid", dev.getId());
		values.put("layer", dev.getLayer());
		values.put("name", dev.getName());
		values.put("mainPowerStatus", dev.getMainPowerStatus());
		values.put("power", dev.getPower());
		values.put("sensorType", dev.getSensorType());
		db.update("sensor", values, "name=?", new String[]{dev.getName()});
		db.close();
	}
	
	public void delete(Integer id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.delete("sensor", "sensorid=?", new String[]{id.toString()});
		db.close();
	}
	
	public SensorDevice find(Integer id){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("sensor", new String[]{"sensorid","layer","name","mainPowerStatus","power","sensorType"}, 
				"sensorid=?", new String[]{id.toString()}, null, null, null, null);
		if(cursor.moveToFirst()){
			int sensorid = cursor.getInt(cursor.getColumnIndex("sensorid"));
			String layer = cursor.getString(cursor.getColumnIndex("layer"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String mainPowerStatus = cursor.getString(cursor.getColumnIndex("mainPowerStatus"));
			String power = cursor.getString(cursor.getColumnIndex("power"));
			String sensorType = cursor.getString(cursor.getColumnIndex("sensorType"));
			cursor.close();
			db.close();
			return new SensorDevice(String.valueOf(sensorid),layer, name, mainPowerStatus, power,sensorType);
		}
		return null;
	}
	/*
	 *offset： 从哪儿开始
	 *maxResult : 最大条目
	 * */
	public List<SensorDevice> getScrollData(int offset, int maxResult){
		List<SensorDevice> persons = new ArrayList<SensorDevice>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		/*public  Cursor   query  (String table, String[] columns, String selection, 
									String[] selectionArgs,String groupBy, 
									String having, String orderBy, String limit)    
		*/
		//Cursor cursor = db.query("sensor", null, null, null, null, null, null, offset+ ","+ maxResult);
		Cursor cursor = db.rawQuery("select * from sensor limit ?,?", 
				new String[]{String.valueOf(offset), String.valueOf(maxResult)});
		while(cursor.moveToNext()){
			int sensorid = cursor.getInt(cursor.getColumnIndex("sensorid"));
			String layer = cursor.getString(cursor.getColumnIndex("layer"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String mainPowerStatus = cursor.getString(cursor.getColumnIndex("mainPowerStatus"));
			String power = cursor.getString(cursor.getColumnIndex("power"));
			String sensorType = cursor.getString(cursor.getColumnIndex("sensorType"));
			persons.add(new SensorDevice(String.valueOf(sensorid),layer, name, mainPowerStatus, power,sensorType));
		}
		db.close();
		return persons;
	}
	//相同名的放在一个集合里面
	public List<SensorDevice> getCommonAttrNode(String sensorName)
	{
		List<SensorDevice> sensorDev = new ArrayList<SensorDevice>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("sensor", new String[]{"sensorid","layer","name","mainPowerStatus","power","sensorType"}, 
				"name=?", new String[]{sensorName}, null, null, null, null);
		sensorDev = null;
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			if((cursor.getString(cursor.getColumnIndex("name")).equals(sensorName)))
			{
				int sensorid = cursor.getInt(cursor.getColumnIndex("sensorid"));
				String layer = cursor.getString(cursor.getColumnIndex("layer"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String mainPowerStatus = cursor.getString(cursor.getColumnIndex("mainPowerStatus"));
				String power = cursor.getString(cursor.getColumnIndex("power"));
				String sensorType = cursor.getString(cursor.getColumnIndex("sensorType"));
				sensorDev.add(new SensorDevice(String.valueOf(sensorid), layer,name, mainPowerStatus, power,sensorType));
			}
		}
		cursor.close();
		db.close();
		return sensorDev;
	}
	public long getCount(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("sensor", new String[]{"count(*)"} , null, null, null, null, null);
		cursor.moveToFirst();
		long count = cursor.getLong(0);
		db.close();
		return count;
	}
	
	

}
