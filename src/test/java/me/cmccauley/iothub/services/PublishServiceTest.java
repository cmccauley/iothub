package me.cmccauley.iothub.services;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
public class PublishServiceTest {

    @InjectMocks
    private PublishService publishService;

    @Mock
    private MqttService mqttService;

    @Mock
    private TopicRepository topicRepository;

    private Topic topic;

    @Before
    public void setup() {
        publishService.setMqttService(mqttService);
        publishService.setTopicRepository(topicRepository);

        Set<String> params = new HashSet<>();
        params.add("a");
        params.add("b");
        params.add("c");

        topic = new Topic();
        topic.setParameterList(params);
        topic.setName("topic/abc");
    }

    @Test
    public void publish() {
        when(topicRepository.findOne(1L)).thenReturn(topic);

        Map<String, String> message = new HashMap<>();
        message.put("a", "123");
        message.put("b", "123");
        message.put("c", "123");

        publishService.publish(1L, message);
    }

    @Test(expected = RuntimeException.class)
    public void publishInvalid() {
        when(topicRepository.findOne(1L)).thenReturn(topic);

        Map<String, String> message = new HashMap<>();
        message.put("a", "123");
        message.put("b", "123");
        message.put("z", "123");

        publishService.publish(1L, message);
    }

    @Test
    public void publishList() {
        when(topicRepository.findOne(1L)).thenReturn(topic);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message1 = new HashMap<>();
        message1.put("a", "123");
        message1.put("b", "123");
        message1.put("c", "123");

        Map<String, String> message2 = new HashMap<>();
        message2.put("a", "456");
        message2.put("b", "456");
        message2.put("c", "456");

        messages.add(message1);
        messages.add(message2);

        publishService.publishList(1L, messages);
    }

    @Test(expected = RuntimeException.class)
    public void publishInvalidList() {
        when(topicRepository.findOne(1L)).thenReturn(topic);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message1 = new HashMap<>();
        message1.put("a", "123");
        message1.put("b", "123");
        message1.put("c", "123");

        Map<String, String> message2 = new HashMap<>();
        message2.put("a", "456");
        message2.put("b", "456");
        message2.put("z", "456");

        messages.add(message1);
        messages.add(message2);

        publishService.publishList(1L, messages);
    }
}