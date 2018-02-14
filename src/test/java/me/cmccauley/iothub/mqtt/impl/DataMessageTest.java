package me.cmccauley.iothub.mqtt.impl;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class DataMessageTest {

    @InjectMocks
    private DataMessageHandler dataMessageHandler;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private MqttMessageRepository mqttMessageRepository;

    @Before
    public void setup() {
        dataMessageHandler.setSubscriptionRepository(subscriptionRepository);
        dataMessageHandler.setMqttMessageRepository(mqttMessageRepository);
    }

    @Test
    public void handleCallbackMessage() {
        org.eclipse.paho.client.mqttv3.MqttMessage pahoMqttMessage = new org.eclipse.paho.client.mqttv3.MqttMessage();
        pahoMqttMessage.setPayload("message".getBytes());
        Subscription subscription = new Subscription();
        subscription.setTopicName("topic1");

        when(subscriptionRepository.findByTopicName(subscription.getTopicName())).thenReturn(subscription);
        when(mqttMessageRepository.save(isA(MqttMessage.class))).thenReturn(null);
        dataMessageHandler.handleCallbackMessage("topic1", pahoMqttMessage);
    }
}