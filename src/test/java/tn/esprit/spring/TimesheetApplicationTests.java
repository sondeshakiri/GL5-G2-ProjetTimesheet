package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

@SpringBootTest
@ActiveProfiles({"test-coverage", "test"})  // Activate both profiles
class TimesheetApplicationTests {
	private Environment env;

	/**
	 * This test ensures that the Spring application context loads successfully.
	 * It is intentionally left empty as it serves only to verify the application
	 * context startup without any exceptions.
	 */
	@Test
	void contextLoads() {
		// Context load test, no implementation needed
		System.out.println("Active profiles: " + Arrays.toString(env.getActiveProfiles()));

	}
}
