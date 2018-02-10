package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttMessageService {

    private final MqttMessageRepository mqttMessageRepository;

    @Autowired
    public MqttMessageService(MqttMessageRepository mqttMessageRepository) {
        this.mqttMessageRepository = mqttMessageRepository;
    }

    public void addMessage(MqttMessage mqttMessage){
        mqttMessageRepository.save(mqttMessage);
    }
}
