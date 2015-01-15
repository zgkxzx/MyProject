package com.zgkxzx.sth;

public class NodeConfig {
	private String nodeName;
	private String addrName;
	
	
	public NodeConfig(String nodeName,String addrName)
	{
		this.nodeName = nodeName;
		this.addrName = addrName;
	}




	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public String getAddrName() {
		return addrName;
	}


	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}




}
