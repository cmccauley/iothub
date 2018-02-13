package me.cmccauley.iothub.mqtt.impl;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import me.cmccauley.iothub.mqtt.CallbackMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataMessageHandler implements CallbackMessageHandler {
    private final static Logger LOG = LoggerFactory.getLogger(DataMessageHandler.class);

    private MqttMessageRepository mqttMessageRepository;
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public DataMessageHandler(MqttMessageRepository mqttMessageRepository, SubscriptionRepository subscriptionRepository) {
        this.mqttMessageRepository = mqttMessageRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void handleCallbackMessage(String topicName, org.eclipse.paho.client.mqttv3.MqttMessage pahoMqttMessage) {
        final Subscription subscription = subscriptionRepository.findByTopicNameAndActiveTrue(topicName);
        if (subscription != null) {
            String message = new String(pahoMqttMessage.getPayload());
            final MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setSubscription(subscription);
            mqttMessage.setMessage(message);
            mqttMessageRepository.save(mqttMessage);
        } else {
            LOG.error("Message received for a topic that was not found in the system. Topic:{}, Message:{}", topicName, pahoMqttMessage);
        }
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
