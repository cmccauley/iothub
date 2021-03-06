package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MqttService {
    private final static Logger LOG = LoggerFactory.getLogger(MqttService.class);

    private MqttClient mqttClient;
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public MqttService(MqttClient mqttClient, SubscriptionRepository subscriptionRepository) {
        this.mqttClient = mqttClient;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void start() throws MqttException {
        mqttClient.connect();
        mqttClient.subscribe("/announce/#");
        loadActiveSubscriptions();
    }

    public void publish(String topic, String message) {
        try {
            mqttClient.publish(topic, new MqttMessage(message.getBytes()));
        } catch (MqttException e) {
            LOG.error("Error publishing topic {} with message {}. {}", topic, message, e.getMessage());
        }
    }

    public void subscribe(String topic) {
        try {
            mqttClient.subscribe(topic);
        } catch (MqttException e) {
            LOG.error("Error subscribing to topic {}. {}", topic, e.getMessage());
        }
    }

    public void unsubscribe(String topic) {
        try {
            mqttClient.unsubscribe(topic);
        } catch (MqttException e) {
            LOG.warn("Error while unsubscribing to topic {}. {}", topic, e.getMessage());
        }
    }

    public void loadActiveSubscriptions() {
        final List<Subscription> databaseSubscriptions = subscriptionRepository.findAll();
        databaseSubscriptions.stream().filter(Subscription::isActive).forEach(databaseSubscription -> {
            subscribe(databaseSubscription.getTopicName());
        });
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public SubscriptionRepository getSubscriptionRepository() {
        return subscriptionRepository;
    }

    public void setSubscriptionRepository(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
}
