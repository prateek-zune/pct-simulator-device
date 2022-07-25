package com.pct.device.simulator;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

	public Producer() {
		System.out.println("intilize producer---");
	}

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private KafkaTemplate<String, DatagramMigrationPacketObject> kafkaTemplate;

	public void sendMessage(DatagramMigrationPacketObject rawData, String topic, String deviceId) {

		List<Header> headers = new ArrayList<>();
		String uuid = UUID.randomUUID().toString();
		Long receivedTimeStamp = System.currentTimeMillis();
		headers.add(new RecordHeader("receivedTimeStamp", receivedTimeStamp.toString().getBytes()));
		headers.add(new RecordHeader("messageUUID", uuid.getBytes()));
		ProducerRecord<String, DatagramMigrationPacketObject> bar = new ProducerRecord<>(topic, 0, receivedTimeStamp, deviceId,
				rawData, headers);
		kafkaTemplate.send(bar);
	}
}
