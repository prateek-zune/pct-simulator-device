package com.pct.device.simulator;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class DatagramMigrationPacketObject implements Serializable{

	public DatagramMigrationPacketObject() {
		super();
	}

	public DatagramMigrationPacketObject(byte[] buf, int offset, int length, int bufLength, InetAddress address, int port) {
		super();


	}

	private String serverIP;
	private String deviceIP;
	private int devicePort;
	private int serverPort;
	private String recievedTimeStamp;
	private long oldRecordId;
	DatagramPacket datagramPacket;
	
	public DatagramPacket getDatagramPacket() {
		return datagramPacket;
	}

	public void setDatagramPacket(DatagramPacket datagramPacket) {
		this.datagramPacket = datagramPacket;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public int getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(int devicePort) {
		this.devicePort = devicePort;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getRecievedTimeStamp() {
		return recievedTimeStamp;
	}

	public void setRecievedTimeStamp(String recievedTimeStamp) {
		this.recievedTimeStamp = recievedTimeStamp;
	}

	public long getOldRecordId() {
		return oldRecordId;
	}

	public void setOldRecordId(long oldRecordId) {
		this.oldRecordId = oldRecordId;
	}

	

	

}
