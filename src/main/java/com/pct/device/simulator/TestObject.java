package com.pct.device.simulator;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class TestObject {

/*	@Value("${simulator.server.ip}")
	private static String serverIP;
	@Value("${simulator.server.port}")
	private static int serverPort;*/

	private static TestObject socket = null;
	private static InetAddress clientIPAddress;
	private static Map<String,List<Object>> testCaseList = new HashMap<String,List<Object>>();
	public static Map<String,String> deviceActiveMap = new HashMap<String,String>();

	public static TestObject getTestObject(String serverIP , int serverPort) throws Exception {
		
		System.out.println("Starting server on IP "+serverIP +" Port : "+serverPort);
		if (socket == null) {

			socket = new TestObject();

		}
		return socket;
	}
}
