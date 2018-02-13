package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;

@Service
public class MqttMessageService {
    private final static Logger LOG = LoggerFactory.getLogger(MqttMessageService.class);

    private final MqttMessageRepository mqttMessageRepository;
    private final SubscriptionService subscriptionService;

    @Autowired
    public MqttMessageService(MqttMessageRepository mqttMessageRepository, SubscriptionService subscriptionService) {
        this.mqttMessageRepository = mqttMessageRepository;
        this.subscriptionService = subscriptionService;
    }

    public void handleCallbackMessage(String topic, String receivedMessage) {
        final Subscription subscription = subscriptionService.getSubscriptionByName(topic);
        if(subscription != null)
        {
            final MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setSubscription(subscription);
            mqttMessage.setMessage(receivedMessage);

            mqttMessageRepository.save(mqttMessage);
        }
        else
        {
            LOG.error("Message received for a topic that was not found in the system. Topic:{}, Message:{}", topic, receivedMessage);
        }
    }

    public MqttMessage getMqttMessageById(Long subscriptionId) {
        return mqttMessageRepository.findOne(subscriptionId);
    }

    public MqttMessage getMqttMessageBySubscriptionId(Long subscriptionId) {
        return mqttMessageRepository.findOne(subscriptionId);
    }

    public Collection<MqttMessage> getAllMqttMessages() {
        return mqttMessageRepository.findAll();
    }
}
