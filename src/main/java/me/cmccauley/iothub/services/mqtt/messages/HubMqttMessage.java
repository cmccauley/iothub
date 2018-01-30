package me.cmccauley.iothub.services.mqtt.messages;

import java.util.List;

public interface HubMqttMessage {

    List<String> getMessages();
}
