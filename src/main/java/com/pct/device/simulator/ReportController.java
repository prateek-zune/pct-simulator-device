package com.pct.device.simulator;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.pct.device.simulator.util.DeviceSimulator;
import com.pct.device.simulator.util.StringUtilities;

@RestController
@RequestMapping("/report")
public class ReportController {

	Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Value("${listener.ip}")
	private String listenerIp;
	@Value("${listener.port}")
	private int listenerPort;

	@Value("${simulator.server.ip}")
	private String serverIp;
	@Value("${simulator.server.port}")
	private int serverPort;
	
	
	@Autowired
	Producer kafkaCommandProducer;
	
	@Autowired
	DeviceSimulator deviceSimulator;
	

	@GetMapping("/sendReport")
	public void sendRawReport(@RequestParam(value = "rawreport", required = true) String rawReport,@RequestParam(value = "numberOfRequest", required = true) int numberOfRequest,@RequestParam(value = "waitPeriod", required = true) int waitPeriod) {
		try {
			deviceSimulator.i =0;
			logger.info("Packet received on Simultor");
			//DatagramSocket socket = new DatagramSocket();
			DatagramSocket socket = SocketObject.getSocket(serverIp,serverPort);
			byte[] buf = StringUtilities.Hex2Byte(rawReport);
			InetAddress inetAddress = InetAddress.getByName(listenerIp);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, listenerPort);
			for(int i=0;i<numberOfRequest;i++)
			{
				socket.send(datagramPacket);
				try
				{
					Thread.sleep(waitPeriod);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			logger.info("Datagram packet sent to UDP Listener");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed while sending raw report");
		}

	}
	
	
	@GetMapping("/produce")
	public void produceKafka(@RequestParam(value = "rawreport", required = true) String rawReport,@RequestParam(value = "numberOfRequest", required = true) int numberOfRequest,@RequestParam(value = "waitPeriod", required = true) int waitPeriod) {
		try {
			deviceSimulator.i =0;
			logger.info("Packet received on Simultor");
			//DatagramSocket socket = new DatagramSocket();
			DatagramSocket socket = SocketObject.getSocket(serverIp,serverPort);
			byte[] buf = StringUtilities.Hex2Byte(rawReport);
			InetAddress inetAddress = InetAddress.getByName(listenerIp);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, listenerPort);
			DatagramMigrationPacketObject datagramMigrationPacketObject = new DatagramMigrationPacketObject();
			datagramMigrationPacketObject.setDatagramPacket(datagramPacket);
			datagramMigrationPacketObject.setServerIP("127.0.0.1");
			datagramMigrationPacketObject.setDeviceIP("127.0.0.1");
			datagramMigrationPacketObject.setDevicePort(10);
			datagramMigrationPacketObject.setServerPort(10);
			datagramMigrationPacketObject.setRecievedTimeStamp("Sa");
			datagramMigrationPacketObject.setOldRecordId(10);
			
			
			kafkaCommandProducer.sendMessage(datagramMigrationPacketObject, "topic", "123");
			logger.info("Datagram packet sent to UDP Listener");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed while sending raw report");
		}

	}

	@GetMapping("/update-status")
	public String getDeviceCommandFromRedis(@RequestParam("deviceId") String deviceID,
			@RequestParam("status") String status, HttpServletRequest httpServletRequest) {
		logger.info("Inside addDeviceCommand Api Controller");
		{
			TestObject.deviceActiveMap.put(deviceID, status);
			return "success";
		}
	}
	
	public static void main(String s[])
	{
		try {
		
			//DatagramSocket socket = new DatagramSocket();
			DatagramSocket socket = null;
			byte[] buf = StringUtilities.Hex2Byte("7d01001511500646044700162925705e2ee147011bb92925705e075f064f03b4329c3f0000000009093a5382000002bae10015014015100102160163020a0a040203009602000300e1013001300a534e3a303030303030303030303030303030300048573a302d30302076300046573a3020302e302e30206e7200e10218ffff4b656e5f4e6574776f726b5f4e616e6f546d6f5f3100e1035c01a5b94b656e5f4e6574776f726b5f4e616e6f546d6f5f3100026ed64b656e5f4770735f3100035d854b656e5f4576656e74735f4375746c6173735f3600042c674b656e5f4265686176696f725f4375746c6173735f390005b2f800e1052e0104004b656e5f4e6574776f726b020c05312e323000050000026ed60c0c06020002000000060c00070c000a0c00e21009012dfa00130000103c1121007f0401360104007e000000dc000000000000000000000000003bffaf00166464e200020100");
			InetAddress inetAddress = InetAddress.getByName("100.24.214.108");
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, 15020);
			if (socket == null) {

				socket = new DatagramSocket();
				socket.setReceiveBufferSize(100000000);

			}
			for(int i=0;i<1;i++)
			{
				socket.send(datagramPacket);
				try
				{
					Thread.sleep(3);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		//	logger.info("Datagram packet sent to UDP Listener");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed while sending raw report");
		}
	}

}
