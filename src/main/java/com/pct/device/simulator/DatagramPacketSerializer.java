package com.pct.device.simulator;

import java.net.DatagramPacket;
import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class DatagramPacketSerializer implements Serializer<DatagramMigrationPacketObject> {

	@Override
	public byte[] serialize(String s, DatagramMigrationPacketObject quote) {
		byte[] retVal = null;

		// ObjectMapper objectMapper = new
		// ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); // turn off everything
		objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // only use fields
		try {

			retVal = objectMapper.writeValueAsString(quote).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
