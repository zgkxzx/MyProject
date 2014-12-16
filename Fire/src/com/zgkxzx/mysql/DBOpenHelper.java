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
				"layer varchar(4),"+
				"name varchar(20)," +
				"powerMode varchar(1),"+
				"power varchar(3),"+
				"sensorsStatus varchar(8),"+
				"sensorsType varchar(8))"
		);
		//建立一张 报警日志 表
		db.execSQL("CREATE TABLE log (id integer primary key autoincrement," +
				"time varchar(20)," +
				"name varchar(20)," +
				"detail varchar(20))"
		);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
