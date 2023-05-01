package parking.monitoring.service;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import parking.monitoring.CarScan;

@Service
public class CameraScanImitatorImpl implements CameraScanImitator {
	
	@Value("${app.amount.min.cars: 1}")
	int nCarsMin;
	@Value("${app.amount.max.cars: 8}")
	int nCarsMax;
	@Value("${app.parking.zones: 1, 2, 3}")
	String[] parkingZone;
	private static Logger LOG = LoggerFactory.getLogger(CameraScanImitatorImpl.class);
	private static int CARS_AMOUNT = -1;
	private static long carNumber = 8;
	@Value("${app.scanning.timeout: 40000}")
	long timeout;

	@Override
	public CarScan nextCameraScan() {
		if(CARS_AMOUNT <= 0) {
			CARS_AMOUNT = ThreadLocalRandom.current().nextInt(nCarsMin, nCarsMax);
			carNumber = carNumber - ThreadLocalRandom.current().nextInt(1, CARS_AMOUNT + 1);
			try {
				LOG.debug("*imitator* next car scanning after 10 minutes");
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LOG.debug("*imitator* cars amount: {}", CARS_AMOUNT);
		}
		int index = ThreadLocalRandom.current().nextInt(0, parkingZone.length);
		CarScan car = new CarScan(carNumber++, parkingZone[index]);
		LOG.debug("*imitator* car scan: {}", car.toString());
		CARS_AMOUNT--;
		
		return car;
	}

}
