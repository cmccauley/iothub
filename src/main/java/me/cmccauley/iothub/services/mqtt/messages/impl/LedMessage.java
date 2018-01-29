package me.cmccauley.iothub.services.mqtt.messages.impl;

import me.cmccauley.iothub.devices.led.Led;
import me.cmccauley.iothub.services.mqtt.messages.HubMqttMessage;

import java.util.List;

public class LedMessage implements HubMqttMessage {

    private List<Led> leds;

    @Override
    public String[] getMessages() {
        return new String[0];
    }
}
