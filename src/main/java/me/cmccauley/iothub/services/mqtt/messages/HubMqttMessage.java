package me.cmccauley.iothub.services.mqtt.messages;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface HubMqttMessage {
    String[] getMessages();
}
