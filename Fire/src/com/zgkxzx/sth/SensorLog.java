package com.zgkxzx.sth;

public class SensorLog {
	private String logTime;
	private String name;
	private String detail;
	
	public SensorLog(){}
	
	public SensorLog(String logTime,String name,String detail)
	{
		this.logTime = logTime;
		this.name = name;
		this.detail = detail;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
