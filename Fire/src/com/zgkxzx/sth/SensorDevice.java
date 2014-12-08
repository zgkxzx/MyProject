package com.zgkxzx.sth;

public class SensorDevice {
	private String id; //ID
	private String layer; //楼层
	private String name; //节点名字
	private String mainPowerStatus;//供电状态   ture -->主电 ;false --> 备电
	private String power; //电量
	private String sensorType;   //传感器类型  ture -->光 ;false --> 烟
	
	public SensorDevice(){}
	
	public SensorDevice(String id,String name){
		
		this.id = id;
		this.name = name;
	}
	public SensorDevice(String id,String layer,String name,String mainPowerStatus,String power,String sensorType){
		
		this.id = id;
		this.layer =layer;
		this.name = name;
		this.mainPowerStatus = mainPowerStatus;
		this.power = power;
		this.sensorType = sensorType;
}

	public SensorDevice(String layer,String name,String mainPowerStatus,String power,String sensorType){
		
		this.layer =layer;
		this.name = name;
		this.mainPowerStatus = mainPowerStatus;
		this.power = power;
		this.sensorType = sensorType;
   }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainPowerStatus() {
		return mainPowerStatus;
	}

	public void setMainPowerStatus(String mainPowerStatus) {
		this.mainPowerStatus = mainPowerStatus;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		layer = layer;
	}
	

}
