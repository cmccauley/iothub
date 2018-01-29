package me.cmccauley.iothub.services.mqtt.strategies;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface PublishStrategy {
    void publish(String topic, String message) throws MqttException;
}
