package org.javaacademy.NuclearPowerPlant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("france")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NuclearPowerPlantApplicationFranceTests {

	@Autowired
	private NuclearStation nuclearStation;
	@Autowired
	private ReactorDepartment reactorDepartment;
	@Autowired
	private SecurityDepartment securityDepartment;
	@Autowired
	private EconomicDepartment economicDepartment;

	@Test
	void addAccidentSuccess() {
		securityDepartment.addAccident();
		Assertions.assertEquals(1, securityDepartment.getCountAccidents());
	}

	@Test
	void deleteAccidentSuccess() {
		securityDepartment.addAccident();
		securityDepartment.reset();
		Assertions.assertEquals(0, securityDepartment.getCountAccidents());
	}

	@Test
	void reactorRunSuccess() {
		Assertions.assertDoesNotThrow(reactorDepartment::run);
	}

	@Test
	void reactorStopSuccess() {
		reactorDepartment.run();
		Assertions.assertDoesNotThrow(reactorDepartment::stop);
	}

	@Test
	void reactorTwiceRunException() {
		reactorDepartment.run();
		Assertions.assertThrows(RuntimeException.class, reactorDepartment::run);
	}

	@Test
	void reactorStopException() {
		Assertions.assertThrows(RuntimeException.class, reactorDepartment::stop);
	}

	@Test
	void economicDepartmentSuccess() {
		Assertions.assertEquals(new BigDecimal(5.0).doubleValue(),
				economicDepartment.computeYearIncomes(10).doubleValue());
		Assertions.assertEquals(new BigDecimal(50_000_000).doubleValue(),
				economicDepartment.computeYearIncomes(100_000_000).doubleValue());
		Assertions.assertEquals(new BigDecimal(1_000_000_000L).doubleValue(),
				economicDepartment.computeYearIncomes(2_000_000_000).doubleValue());
	}

	@Test
	void nuclearStationWorked() {
		Assertions.assertDoesNotThrow(() -> nuclearStation.start(1));
	}

}
