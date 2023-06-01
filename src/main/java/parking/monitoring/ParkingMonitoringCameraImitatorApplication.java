package parking.monitoring;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PreDestroy;
import parking.monitoring.service.CameraScanImitator;

@SpringBootApplication
public class ParkingMonitoringCameraImitatorApplication {

	@Autowired
	CameraScanImitator imitator;
	
	public static void main(String[] args) {
		SpringApplication.run(ParkingMonitoringCameraImitatorApplication.class, args);
		
	}
	
	@Bean
	Supplier<CarScan> camScanSupplier(){
		
		return imitator::nextCameraScan;
	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("ParkingMonitoringCameraImitator - shutdown has been performed");
	}

}



