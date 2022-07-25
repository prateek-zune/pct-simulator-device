package com.pct.device.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeviceSimulatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceSimulatorServiceApplication.class, args);

		SendReport sendReport = new SendReport();
		while (true) {
			sendReport.sendRawReport();
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	} 

}
