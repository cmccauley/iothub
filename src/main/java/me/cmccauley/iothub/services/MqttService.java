package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MqttService {
    private final static Logger LOG = LoggerFactory.getLogger(MqttService.class);

    private final MqttClient mqttClient;

    private final MqttCallback defaultCallback;

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public MqttService(MqttClient mqttClient, MqttCallback defaultCallback, SubscriptionRepository subscriptionRepository) {
        this.mqttClient = mqttClient;
        this.defaultCallback = defaultCallback;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void start() throws MqttException {
        mqttClient.setCallback(defaultCallback);
        mqttClient.connect();
    }

    public void publish(String topic, String message) {
        try {
            mqttClient.publish(topic, new MqttMessage(message.getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public MqttCallback getDefaultCallback() {
        return defaultCallback;
    }

    public SubscriptionRepository getSubscriptionRepository() {
        return subscriptionRepository;
    }

}
