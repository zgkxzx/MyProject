package com.example.myndkdemo;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	
	private String s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		System.out.println("-----add function-----");
		System.out.println(add(120,302));
		System.out.println("-----mice function ----");
		System.out.println(mice(120,302));
		aaaa(1);
		bbbb();
		
		s = "zhaoXiang";
		accessField();
		System.out.println("In Java:s="+s);
		
	}
	
	
	private void callback()
	{
		System.out.println("----- callback() ----");
	}

	public native int add(int para1,int para2);
	public native int mice(int para1,int para2);
	public native int aaaa(int value);
	public native int bbbb();
	public native void accessField();
	static{
		
		System.loadLibrary("auth");
	}
}
