package me.cmccauley.iothub.mqtt.impl;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AnnounceMessageTest {

    @InjectMocks
    private AnnounceMessageHandler announceMessageHandler;

    @Mock
    private TopicRepository topicRepository;

    @Before
    public void setup() {
        announceMessageHandler.setTopicRepository(topicRepository);
    }

    @Test
    public void handleCallbackMessage_newAnnouncement() {
        Topic topic = new Topic();
        MqttMessage pahoMqttMessage = new MqttMessage();
        pahoMqttMessage.setPayload("A,B,C".getBytes());

        when(topicRepository.findOneByName("abc")).thenReturn(topic);
        when(topicRepository.save(topic)).thenReturn(null);

        announceMessageHandler.handleCallbackMessage("/announce/abc", pahoMqttMessage);

        verify(topicRepository, times(1)).save(isA(Topic.class));
    }

    @Test
    public void handleCallbackMessage_updateAnnouncement() {
        Topic topic = new Topic();
        MqttMessage pahoMqttMessage = new MqttMessage();
        pahoMqttMessage.setPayload("A,B,C".getBytes());

        when(topicRepository.findOneByName("abc")).thenReturn(topic);
        when(topicRepository.save(topic)).thenReturn(topic);

        announceMessageHandler.handleCallbackMessage("/announce/abc", pahoMqttMessage);

        verify(topicRepository, times(1)).save(isA(Topic.class));
    }
}