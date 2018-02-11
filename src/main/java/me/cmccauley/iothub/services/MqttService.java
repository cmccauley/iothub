package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import me.cmccauley.iothub.mqtt.messages.HubMqttMessage;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MqttService {

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
        mqttClient.subscribe("#");
    }

    public void publish(String topic, HubMqttMessage message) {
        message.getMessages().forEach(msg -> {
            try {
                System.out.println(msg);
                mqttClient.publish(topic, new MqttMessage(msg.getBytes()));
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }

    public void publish(String topic, Map<String, String> message) {

        StringBuilder builder = new StringBuilder();
        message.forEach((key, value) -> {
            builder.append(key);
            builder.append("=");
            builder.append(value);
            builder.append(";");
        });

        try {
            mqttClient.publish(topic, new MqttMessage(builder.toString().getBytes()));
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
