package me.cmccauley.iothub;

import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IothubApplicationTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MqttMessageRepository employeeRepository;

	@Test
	public void contextLoads() {
	}

}
