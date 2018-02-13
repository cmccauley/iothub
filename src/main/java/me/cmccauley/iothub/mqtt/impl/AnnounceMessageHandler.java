package me.cmccauley.iothub.mqtt.impl;

import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import me.cmccauley.iothub.mqtt.CallbackMessageHandler;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class AnnounceMessageHandler implements CallbackMessageHandler {

    private static final String DELIMITER = ",";

    private TopicRepository topicRepository;

    @Autowired
    public AnnounceMessageHandler(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void handleCallbackMessage(String topicName, MqttMessage pahoMqttMessage) {
        String trimmedTopicName = topicName.substring("/announce".length(), topicName.length());

        String message = new String(pahoMqttMessage.getPayload());

        Set<String> params = new HashSet<>(Arrays.asList(message.split(DELIMITER)));

        Topic topic = topicRepository.findOneByName(trimmedTopicName);
        if(topic != null) {
            topic.setParameterList(params);
        } else {
            topic = new Topic();
            topic.setName(trimmedTopicName);
            topic.setParameterList(params);
        }

        topicRepository.save(topic);
    }

    public TopicRepository getTopicRepository() {
        return topicRepository;
    }

    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
}
