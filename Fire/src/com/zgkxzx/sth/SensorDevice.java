package com.zgkxzx.sth;

public class SensorDevice {
	private String id; //ID
	private String layer; //楼层
	private String name; //节点名字
	private String PowerMode;//供电状态   
	private String power; //电量
	private String sensorsStatus;   //传感器状态  
	private String sensorsType;   //传感器类型  
	private String devicesStatus;//设备状态
	
	public SensorDevice(){}
	
	public SensorDevice(String id,String name){
		
		this.id = id;
		this.name = name;
	}
    public String getDevicesStatus() {
		return devicesStatus;
	}

	public void setDevicesStatus(String devicesStatus) {
		this.devicesStatus = devicesStatus;
	}

	public void setSensorsType(String sensorsType) {
		this.sensorsType = sensorsType;
	}

   public SensorDevice(String id,String layer,String name,String PowerMode,String power,String sensorsStatus,String sensorsType,String devicesStatus){
		
		this.id = id;
		this.layer =layer;
		this.name = name;
		this.PowerMode = PowerMode;
		this.power = power;
		this.sensorsStatus = sensorsStatus;
		this.sensorsType = sensorsType;
		this.devicesStatus = devicesStatus;
	}
	public SensorDevice(String id,String layer,String name,String PowerMode,String power,String sensorsStatus,String sensorsType){
		
		this.id = id;
		this.layer =layer;
		this.name = name;
		this.PowerMode = PowerMode;
		this.power = power;
		this.sensorsStatus = sensorsStatus;
		this.sensorsType = sensorsType;
	}

	public SensorDevice(String layer,String name,String PowerMode,String power,String sensorsStatus,String sensorsType){
		
		this.layer =layer;
		this.name = name;
		this.PowerMode = PowerMode;
		this.power = power;
		this.sensorsStatus = sensorsStatus;
		this.sensorsType = sensorsType;
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

	public String getPowerMode() {
		return PowerMode;
	}

	public String getSensorsStatus() {
		return sensorsStatus;
	}

	public void setSensorsStatus(String sensorsStatus) {
		this.sensorsStatus = sensorsStatus;
	}

	public void setPowerMode(String PowerMode) {
		this.PowerMode = PowerMode;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getSensorsType() {
		return sensorsType;
	}

	public void setSensorType(String sensorsType) {
		this.sensorsType = sensorsType;
	}
	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		layer = layer;
	}
	

}
