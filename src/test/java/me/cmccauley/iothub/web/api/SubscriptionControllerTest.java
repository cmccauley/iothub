package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.IothubApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
public class SubscriptionControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createSubscription() {
    }

    @Test
    public void getSubscriptionById() {
    }

    @Test
    public void getAllSubscriptions() {
    }

    @Test
    public void deleteSubscription() {
    }

    @Test
    public void getSubscriptionService() {
    }
}