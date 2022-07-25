package com.pct.device.simulator;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.pct.device.simulator.util.DeviceSimulator;
import com.pct.device.simulator.util.StringUtilities;
@Component
public class SendReport {
	
	Logger logger = LoggerFactory.getLogger(SendReport.class);

	@Value("${listener.ip}")
	private String listenerIp;
	@Value("${listener.port}")
	private int listenerPort;

	@Value("${simulator.server.ip}")
	private String serverIp;
	@Value("${simulator.server.port}")
	private int serverPort;
	
	@Autowired
	DeviceSimulator deviceSimulator;
	
	public void sendRawReport() {
	String msgUuid = UUID.randomUUID().toString();
		try {
			logger.info("msgUuid " + msgUuid + " Inside method sendRawReport");
			String rawReport = "7d01001511500840139900012b18afa622e10e011bd12b18afc6077fcf2605c5f3023f0000018308080053000006aa39e10015014015100102150163020803070102020701070403e10135010001534e3a363830616532666666656234373461630048573a3130332d30372076370046573a3130303520312e31392e38205200e102167fff547261696c65724e65745f5361624c475f313600e200020000e21009010008007b002a1052e442070103d80000001ff0010400e400e4e622030c80cbfffe320ffeb30ce400c86403e8e00e0600000000000000000015a4250881495bda1386ca95a618b155aa96eeddccbbab07cffa1308ffff280000002400003338000000030000000000000000000000000c050302000043df007034cf00000008";
			logger.info("msgUuid " + msgUuid + " rawReport " + rawReport);
			DatagramSocket socket = SocketObject.getSocket("52.207.117.142",15022);
			logger.info(" Datagram socket created serverIp " + serverIp + " serverPort " + serverPort);
			byte[] buf = StringUtilities.Hex2Byte(rawReport);
			InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, 15020);
			logger.info("msgUuid " + msgUuid + " Datagram packet created listenerIp " + listenerIp + " listenerPort " + listenerPort);
			socket.send(datagramPacket);
			logger.info("msgUuid " + msgUuid + " Datagram packet sent to UDP Listener");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("msgUuid " + msgUuid + " Failed while sending raw report " + e);
			
		}

	}


}
