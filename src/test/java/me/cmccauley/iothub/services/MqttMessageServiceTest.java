package me.cmccauley.iothub.services;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
public class MqttMessageServiceTest {

    @InjectMocks
    private MqttMessageService mqttMessageService;

    @Mock
    private MqttMessageRepository mqttMessageRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    private Subscription activeSubscription;
    private Subscription inactiveSubscription;

    @Before
    public void setup() {
        mqttMessageService.setMqttMessageRepository(mqttMessageRepository);
        activeSubscription = new Subscription();
        activeSubscription.setTopicName("topic1");
        activeSubscription.setActive(true);

        inactiveSubscription = new Subscription();
        inactiveSubscription.setTopicName("topic2");
        inactiveSubscription.setActive(false);
    }

    @Test
    public void getMqttMessageById() {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setMessage("message");
        mqttMessage.setSubscription(activeSubscription);

        when(mqttMessageRepository.findOne(1L)).thenReturn(mqttMessage);

        //When
        MqttMessage savedMqttMessage = mqttMessageService.getMqttMessageById(1L);
        //Then
        assertEquals(mqttMessage, savedMqttMessage);
    }

    @Test
    public void getMqttMessagesBySubscriptionId() {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setMessage("message");
        mqttMessage.setSubscription(activeSubscription);

        MqttMessage mqttMessage2 = new MqttMessage();
        mqttMessage2.setMessage("message2");
        mqttMessage2.setSubscription(activeSubscription);

        List<MqttMessage> mqttMessageList = new ArrayList<>();
        mqttMessageList.add(mqttMessage);
        mqttMessageList.add(mqttMessage2);

        when(mqttMessageRepository.findAllBySubscriptionId(1L)).thenReturn(mqttMessageList);

        //When
        Collection<MqttMessage> savedMqttMessage = mqttMessageService.getMqttMessagesBySubscriptionId(1L);
        //Then
        assertTrue(savedMqttMessage.containsAll(mqttMessageList));
    }

    @Test
    public void getAllMqttMessages() {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setMessage("message");
        mqttMessage.setSubscription(activeSubscription);

        MqttMessage mqttMessage2 = new MqttMessage();
        mqttMessage2.setMessage("message2");
        mqttMessage2.setSubscription(activeSubscription);

        List<MqttMessage> mqttMessageList = new ArrayList<>();
        mqttMessageList.add(mqttMessage);
        mqttMessageList.add(mqttMessage2);

        when(mqttMessageRepository.findAll()).thenReturn(mqttMessageList);

        //When
        Collection<MqttMessage> savedMqttMessage = mqttMessageService.getAllMqttMessages();
        //Then
        assertTrue(savedMqttMessage.containsAll(mqttMessageList));
    }
}