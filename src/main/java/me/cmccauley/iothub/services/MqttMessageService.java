package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MqttMessageService {
    private final static Logger LOG = LoggerFactory.getLogger(MqttMessageService.class);

    private MqttMessageRepository mqttMessageRepository;
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public MqttMessageService(MqttMessageRepository mqttMessageRepository, SubscriptionRepository subscriptionRepository) {
        this.mqttMessageRepository = mqttMessageRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void handleCallbackMessage(String topic, String receivedMessage) {
        final Subscription subscription = subscriptionRepository.findByTopicNameAndActiveTrue(topic);
        if (subscription != null) {
            final MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setSubscription(subscription);
            mqttMessage.setMessage(receivedMessage);

            mqttMessageRepository.save(mqttMessage);
        } else {
            LOG.error("Message received for a topic that was not found in the system. Topic:{}, Message:{}", topic, receivedMessage);
        }
    }

    public MqttMessage getMqttMessageById(Long subscriptionId) {
        return mqttMessageRepository.findOne(subscriptionId);
    }

    public Collection<MqttMessage> getMqttMessagesBySubscriptionId(Long subscriptionId) {
        return mqttMessageRepository.findAllBySubscriptionId(subscriptionId);
    }

    public Collection<MqttMessage> getAllMqttMessages() {
        return mqttMessageRepository.findAll();
    }

    public MqttMessageRepository getMqttMessageRepository() {
        return mqttMessageRepository;
    }

    public void setMqttMessageRepository(MqttMessageRepository mqttMessageRepository) {
        this.mqttMessageRepository = mqttMessageRepository;
    }

    public SubscriptionRepository getSubscriptionRepository() {
        return subscriptionRepository;
    }

    public void setSubscriptionRepository(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
}
