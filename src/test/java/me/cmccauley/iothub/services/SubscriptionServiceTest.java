package me.cmccauley.iothub.services;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
public class SubscriptionServiceTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MqttMessageRepository mqttMessageRepository;

    @Before
    public void setup() throws Exception {
    }

    @Test
    public void createSubscription() {
        Subscription subscription = new Subscription();
        subscription.setActive(true);
        subscription.setTopicName("hello");
        Subscription created = subscriptionService.createSubscription(subscription);

        MqttMessage message = new MqttMessage();
        message.setSubscription(created);
        message.setMessage("hello");

        MqttMessage returnMsg = mqttMessageRepository.save(message);
        System.out.println("");
    }

    @Test
    public void getSubscriptionById() {
    }

    @Test
    public void getSubscriptionByName() {
    }

    @Test
    public void updateSubscription() {
    }

    @Test
    public void deleteSubscription() {
    }

    @Test
    public void getAllSubscriptions() {
    }

    @Test
    public void getSubscription() {
    }

    @Test
    public void subscribeFromDatabase() {
    }
}