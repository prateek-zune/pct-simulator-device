package com.pct.device.simulator.util;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestUtils {

	RestTemplate restTemplate = new RestTemplate();

	public void sendRequest(String rawReport, String deviceIp, int devicePort) {
		System.out.println("Inside RestUtils");
		String url = "http://localhost:8082/report/sendReport?rawreport=" + rawReport + "&deviceIp=" + deviceIp
				+ "&devicePort=" + devicePort;
		restTemplate.getForEntity(url, Object.class);
		System.out.println("After RestUtils");
	}

	public void getACKResponse(DatagramPacket datagramPacket) {
		System.out.println("Inside getAtCommandResponse");
		String url = "http://localhost:8082/response/getResponse?address=" + datagramPacket.getAddress() + "&port="
				+ datagramPacket.getPort() + "&data=" + datagramPacket.getData();
		restTemplate.getForEntity(url, DatagramPacket.class);
		System.out.println("After getAtCommandResponse");
	}

	public void sendATCommandRequest(String ATCommandrequest,String deviceIp, int devicePort) {
		System.out.println("Inside sendATCommandRequest");
		String url = "http://localhost:8082/report/sendReport?rawreport=" + ATCommandrequest +"&deviceIp=" + deviceIp
				+ "&devicePort=" + devicePort;;
		restTemplate.getForEntity(url, Object.class);
		System.out.println("After sendATCommandRequest");
		
	}
}
