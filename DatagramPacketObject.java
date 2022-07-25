package com.pct.device.receiver.serialise;

import java.net.InetAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatagramPacketObject {

	public DatagramPacketObject() {
		super();
	}

	public DatagramPacketObject(byte[] buf, int offset, int length, int bufLength, InetAddress address, int port) {
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
