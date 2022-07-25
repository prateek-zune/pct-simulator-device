package com.pct.device.simulator;

import java.net.InetAddress;

public class DatagramPacketObject2 {

	public DatagramPacketObject2() {
		super();
	}

	public DatagramPacketObject2(byte[] buf, int offset, int length, int bufLength, InetAddress address, int port) {
		super();
		this.buf = buf;
		this.offset = offset;
		this.length = length;
		this.bufLength = bufLength;
		this.address = address;
		this.port = port;
	}

	byte[] buf;
	int offset;
	int length;
	int bufLength;
	InetAddress address;
	int port;

}
