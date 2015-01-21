package com.zgkxzx.sth;

public class LayerTel {
	private String layerNo;
	private String layerName;
	private String tel;
	//
	public LayerTel(String layerNo,String layerName,String tel)
	{
		this.layerNo = layerNo;
		this.layerName = layerName;
		this.tel = tel;
	}
	//
	public LayerTel(String layerName,String tel)
	{
		this.layerName = layerName;
		this.tel = tel;
	}
	
	public String getLayerNo() {
		return layerNo;
	}
	public void setLayerNo(String layerNo) {
		this.layerNo = layerNo;
	}
	public String getLayerName() {
		return layerName;
	}
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	

}
