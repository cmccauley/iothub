package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttLogService {

    private final MqttMessageRepository mqttMessageRepository;

    @Autowired
    public MqttLogService(MqttMessageRepository mqttMessageRepository) {
        this.mqttMessageRepository = mqttMessageRepository;
    }

    public void addLog(MqttMessage mqttMessage){
        mqttMessageRepository.save(mqttMessage);
    }
}
