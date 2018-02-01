package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import me.cmccauley.iothub.mqtt.DefaultCallback;
import me.cmccauley.iothub.mqtt.messages.HubMqttMessage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    private final MqttClient mqttClient;

    private final DefaultCallback defaultCallback;

    private final MqttConnectOptions mqttConnectOptions;

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public MqttService(MqttClient mqttClient, DefaultCallback defaultCallback, MqttConnectOptions mqttConnectOptions, SubscriptionRepository subscriptionRepository) {
        this.mqttClient = mqttClient;
        this.defaultCallback = defaultCallback;
        this.mqttConnectOptions = mqttConnectOptions;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void start() throws MqttException {
        mqttClient.setCallback(defaultCallback);
        mqttClient.connect(mqttConnectOptions);
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

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public DefaultCallback getDefaultCallback() {
        return defaultCallback;
    }

    public MqttConnectOptions getMqttConnectOptions() {
        return mqttConnectOptions;
    }

    public SubscriptionRepository getSubscriptionRepository() {
        return subscriptionRepository;
    }

}
