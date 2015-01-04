package com.zgkxzx.mysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "zx.db", null, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//建立一张 传感节点的数据表
		db.execSQL("CREATE TABLE subMachine (subid integer primary key autoincrement," +
				"layer varchar(3),"+
				"name varchar(3)," +
				"powerMode varchar(1),"+
				"power varchar(3),"+
				"sensorsStatus varchar(16),"+
				"sensorsType varchar(16),"+
				"devicesStatus varchar(8))"
		);
		
		//建立一张 报警日志 表
		db.execSQL("CREATE TABLE log (logid integer primary key autoincrement," +
				"time varchar(25)," +
				"name varchar(20)," +
				"detail varchar(20))"
		);
		//建立一张 具体探头名称 具体地点 表
				db.execSQL("CREATE TABLE addrInfo (infoid integer primary key autoincrement," +
						"nodeName varchar(10)," +
						"addressName varchar(32))"
				);
	}

   
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
