package me.cmccauley.iothub.services.mqtt.messages.impl;

import me.cmccauley.iothub.services.mqtt.messages.HubMqttMessage;

public class SimpleMessage implements HubMqttMessage{

    private String message;

    public SimpleMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String[] getMessages() {
        return new String[] {message};
    }
}
