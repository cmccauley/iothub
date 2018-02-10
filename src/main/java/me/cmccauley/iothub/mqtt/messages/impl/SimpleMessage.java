package me.cmccauley.iothub.mqtt.messages.impl;

import me.cmccauley.iothub.mqtt.messages.HubMqttMessage;

import java.util.Collections;
import java.util.List;

public class SimpleMessage implements HubMqttMessage{

    private String message;

    public SimpleMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public List<String> getMessages() {
        return Collections.singletonList(message);
    }

}
