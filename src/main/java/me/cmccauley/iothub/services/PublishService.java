package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PublishService {

    private final MqttService mqttService;
    private final TopicRepository topicRepository;

    @Autowired
    public PublishService(MqttService mqttService, TopicRepository topicRepository) {
        this.mqttService = mqttService;
        this.topicRepository = topicRepository;
    }

    public void publish(Long topicId, Map<String, String> message) {
        final Topic topic = topicRepository.getOne(topicId);
        if (isValid(topic, message)) {
            mqttService.publish(topic.getName(), message);
        } else {
            throw new RuntimeException("Dat Validation... it works. You Haxxor. Valid params:" + topic.getParameterList());
        }
    }

    private boolean isValid(Topic topic, Map<String, String> message) {
        return message.keySet().containsAll(topic.getParameterList());
    }

}
