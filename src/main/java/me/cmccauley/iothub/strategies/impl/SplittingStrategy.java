package me.cmccauley.iothub.strategies.impl;

import me.cmccauley.iothub.services.mqtt.MqttService;
import me.cmccauley.iothub.strategies.PublishStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SplittingStrategy implements PublishStrategy{

    @Autowired
    private MqttService mqttService;

    @Override
    public void publish(String message) {

    }
}
