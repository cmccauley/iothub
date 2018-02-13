package me.cmccauley.iothub.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface CallbackMessageHandler {

    void handleCallbackMessage(String topicName, MqttMessage pahoMqttMessage);

}
