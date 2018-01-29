package me.cmccauley.iothub.services.mqtt.strategies.impl;

import me.cmccauley.iothub.services.mqtt.strategies.PublishStrategy;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SplittingStrategy implements PublishStrategy {

    private MqttClient mqttClient;

    @Autowired
    public SplittingStrategy(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void publish(String topic, String message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());

        mqttClient.publish(topic, mqttMessage);
    }
}
