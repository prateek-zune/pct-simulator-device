package com.pct.device.simulator.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.pct.device.simulator.SocketObject;

@Component
public class DeviceSimulator implements Runnable {

	static Logger logger = LoggerFactory.getLogger(DeviceSimulator.class);
	private boolean isRunning = true;
	public static final int LISTEN_PACKET_LEN = 2048;

	private InetAddress clientIPAddress;
	@Value("${simulator.server.ip}")
	private String serverIp;
	@Value("${simulator.server.port}")
	private int serverPort;
	public int i = 0;

	@Override
	public void run() {

		while (isRunning) {
			try {
				i++;
				byte[] buffer = new byte[LISTEN_PACKET_LEN];
				logger.info("Inside thread");
				// receive UDP packet
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				DatagramSocket socket = SocketObject.getSocket(serverIp, serverPort);
				socket.setReceiveBufferSize(100000000);
				socket.receive(packet);
				System.out.println(packet.getData()[0]);
				if (packet.getData()[0] == 126)
					logger.info("Acknowledgement packet received");
				else {
					packet.getAddress();
					logger.info("ATCommand received " + new String(packet.getData()));
					String atCommand = new String(packet.getData());
					sendATResponse(atCommand);
				}
			} catch (IOException e) {
				logger.info("Exception " + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void sendATResponse(String atCommand) {

		logger.info("Inside AT Command 1" + StringUtilities.Hex2Byte(atCommand));
		String strr = new String(StringUtilities.Hex2Byte(atCommand));
		logger.info("Inside AT Command 1" + strr);

		atCommand = strr;
		logger.info("Inside AT Command " + atCommand);
		// if (atCommand.contains("at+")) {
		logger.info("Inside AT Command " + atCommand);

		// substring s - take out device ID and AT Command,

		String[] subString = atCommand.split("%");
		String deviceId = subString[1];
		String deviceID = deviceId.substring(0, deviceId.indexOf("^"));
		String atCommandStr = subString[0];
		Charset charset = Charset.forName("ASCII");

		String ATdeviceID = deviceID;
		logger.info("Device Id (ATCommand) " + deviceID);
		byte[] deviceIDByteArrray = deviceID.getBytes(charset);

		// String atCommand =atCommandStr;
		// logger.info("ATCommand (ATCommand " + atCommandStr);
		byte[] atCommandByteArrray = atCommandStr.getBytes(charset);

		Long timeInMilliSeconds = System.currentTimeMillis();
		String timeStamp = "000" + timeInMilliSeconds.toString();

		byte[] timeStampByteArrray = timeStamp.getBytes(charset);

		String[] subString1 = atCommand.split("&");

		String ipPort = subString1[1];
		//String ippp =  ipPort.
	//	System.out.println("IP " + ipPort);

		// System.out.println(new String(StringUtilities.Hex2Byte(ipPort)));
		byte[] ipBytes = ipPort.getBytes(charset);

		/*for (; i < ipBytes.length; i++) {
			if (ipBytes[i] == '&') {
				break;
			}
		}*/
		int ipIndex = i + 1;
		int portIndex = 0;
		for (i = ipIndex; i < ipBytes.length; i++) {
			if (ipBytes[i] == '^') {
				portIndex = i + 1;
				break;
			}
		}
		
		int ipLen = portIndex - ipIndex - 1;
		
		byte[] ipBytes1 = new byte[ipLen];

		System.arraycopy(ipBytes, ipIndex, ipBytes1, 0, ipLen);
System.out.println(new String(ipBytes1));
		//System.arraycopy(saveData, ipIndex, ipBytes, 0, ipLen);
		try {
			InetAddress clientIP = InetAddress.getByAddress(ipBytes1);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(("IP ********" + new String(ipBytes1)));

		/*String response = "0a5b315d3a20226465766465662e636667222043433d3c3078454542433e204349563d22583030355f303322200d0a5b325d3a2022646576646566322e636667222043433d3c3078383232383e204349563d225a3030352d31345f303422200d0a5b335d3a2022646576646566332e636667222043433d3c3078363641393e204349563d22456d7074795f303122200d0a5b345d3a2022646576646566342e636667222043433d3c3078354433363e204349563d224a65727365795f54656c65636f6d5f303422200d0a5b355d3a202264657675";
		byte[] responseByteArrray = response.getBytes(charset);

		String startIndex = "%";
		byte[] startIndexByteArrray = startIndex.getBytes(charset);

		String delimiter = "^";
		byte[] delimiterByteArrray = delimiter.getBytes(charset);

		byte[] allByteArray = new byte[startIndexByteArrray.length + deviceIDByteArrray.length
				+ delimiterByteArrray.length + atCommandByteArrray.length + delimiterByteArrray.length
				+ timeStampByteArrray.length];

		ByteBuffer buff = ByteBuffer.wrap(allByteArray);
		buff.put(startIndexByteArrray);
		buff.put(deviceIDByteArrray);
		buff.put(delimiterByteArrray);
		buff.put(atCommandByteArrray);
		buff.put(delimiterByteArrray);
		buff.put(timeStampByteArrray);
		// buff.put(responseByteArrray);

		if (TestObject.deviceActiveMap.get(deviceID) != null
				&& TestObject.deviceActiveMap.get(deviceID).equals("deactive")) {
			System.out.println("device " + deviceID + " is deactive");

		} else {
			System.out.println("device " + deviceID + " is active");

			byte[] combined = buff.array();
			try {
				byte[] fram = combined;
				String ATresponse = StringUtilities.Byte2HexString(combined, 0, combined.length);
				logger.info("ATResponse   " + ATresponse);
				// restUtils.sendATCommandRequest(ATresponse, serverIp, serverPort);
//				restUtils.sendATCommandRequest("0a50524f3a2020204152524f572d480d0a494d45493a20203031343633313030393938353133320d0a494d53493a20203230343034333339363433333133360d0a49434349443a2038393138353030303135303931313238353134320d0a4d444c3a202020333030315230310d0a42494e3a202020322e302e300d0a4150503a202020322e302e305f390d0a4346473a202020303030332a207273472a0d0a4f4b0d0a");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		// }
	}

	public static void main(String[] aa) {

		InetAddress deviceInetAddress = null;

		try {
			deviceInetAddress = InetAddress.getByName("10.181.207.33");
			System.out.println(deviceInetAddress.getHostAddress());
			byte[] ipBytes = deviceInetAddress.getAddress();

			System.out.println(new String(ipBytes));

			deviceInetAddress = InetAddress.getByAddress(ipBytes);
			System.out.println(deviceInetAddress.getHostAddress());
		} catch (UnknownHostException ue) {
			ue.printStackTrace();
		}
		try {
			deviceInetAddress = InetAddress.getByName("10.92.94.10");
			System.out.println(deviceInetAddress.getHostAddress());

			byte[] ipBytes = deviceInetAddress.getAddress();

			System.out.println(new String(ipBytes));
			deviceInetAddress = InetAddress.getByAddress(ipBytes);
			System.out.println(deviceInetAddress.getHostAddress());
		} catch (UnknownHostException ue) {
			ue.printStackTrace();
		}
		System.out.println("dasds");
		String s = new String();
		// byte[] buf = StringUtilities.Hex2Byte(s);

		DeviceSimulator deviceSimulator = new DeviceSimulator();
	//deviceSimulator.sendATResponse("41542b78726e253836313833363035373931363739385e41542b78726e5e30303031363534373635373537303432260ab5cf215e0000426f25");

	}
}
