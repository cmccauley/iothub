package me.cmccauley.iothub.services;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class MqttServiceTest {

    @InjectMocks
    private MqttService mqttService;

    @Mock
    private MqttClient mqttClient;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Before
    public void setup() {
        mqttService.setSubscriptionRepository(subscriptionRepository);
        mqttService.setMqttClient(mqttClient);
    }

    @Test
    public void start() throws MqttException {
        Subscription subscription = new Subscription();
        subscription.setTopicName("topic1");
        subscription.setActive(true);
        doNothing().when(mqttClient).connect();
        when(subscriptionRepository.findAll()).thenReturn(Collections.singletonList(subscription));
        mqttService.start();

        verify(mqttClient, atLeastOnce()).connect();
        verify(mqttClient, atLeastOnce()).subscribe(subscription.getTopicName());
    }

    @Test
    public void publish() throws MqttException {
        org.eclipse.paho.client.mqttv3.MqttMessage pahoMqttMessage = new org.eclipse.paho.client.mqttv3.MqttMessage("message1".getBytes());

        doNothing().when(mqttClient).publish("topic1", pahoMqttMessage);

        mqttService.publish("topic1", "message1");

        verify(mqttClient, atLeastOnce()).publish(eq("topic1"), isA(org.eclipse.paho.client.mqttv3.MqttMessage.class));
    }

    @Test
    public void subscribe() throws MqttException {
        doNothing().when(mqttClient).subscribe("topic1");
        mqttService.subscribe("topic1");

        verify(mqttClient, atLeastOnce()).subscribe("topic1");
    }

    @Test
    public void unsubscribe() throws MqttException {
        doNothing().when(mqttClient).unsubscribe("topic1");
        mqttService.unsubscribe("topic1");

        verify(mqttClient, atLeastOnce()).unsubscribe("topic1");
    }

    @Test
    public void loadActiveSubscriptions() throws MqttException {
        Subscription subscription1 = new Subscription();
        subscription1.setTopicName("topic1");
        subscription1.setActive(true);

        Subscription subscription2 = new Subscription();
        subscription2.setTopicName("topic2");
        subscription2.setActive(true);

        List<Subscription> activeSubscriptionList = new ArrayList<>();
        activeSubscriptionList.add(subscription1);
        activeSubscriptionList.add(subscription2);

        when(subscriptionRepository.findAll()).thenReturn(activeSubscriptionList);

        //When
        mqttService.loadActiveSubscriptions();
        //Then
        verify(mqttClient, times(1)).subscribe("topic1");
        verify(mqttClient, times(1)).subscribe("topic2");
    }
}