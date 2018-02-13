package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PublishService {
    private final static Logger LOG = LoggerFactory.getLogger(PublishService.class);

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
            mqttService.publish(topic.getName(), convertToKeyValue(message));
        } else {
            throw new RuntimeException("Publish message is invalid. Valid params:" + topic.getParameterList());
        }
    }

    public void publishList(Long topicId, List<Map<String, String>> messageList) {
        final Topic topic = topicRepository.getOne(topicId);
        // Validate the entire message before sending data.
        if (messageList.stream().anyMatch(message -> !isValid(topic, message))) {
            throw new RuntimeException("Publish message is invalid. Valid params:" + topic.getParameterList());
        }
        messageList.forEach(message -> mqttService.publish(topic.getName(), convertToKeyValue(message)));
    }

    private boolean isValid(Topic topic, Map<String, String> message) {
        return message.keySet().containsAll(topic.getParameterList());
    }

    private String convertToKeyValue(Map<String, String> messageMap) {
        StringBuilder builder = new StringBuilder();
        messageMap.forEach((key, value) -> {
            builder.append(key);
            builder.append("=");
            builder.append(value);
            builder.append(";");
        });
        return builder.toString();
    }

}
