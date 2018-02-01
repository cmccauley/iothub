package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.MqttLog;
import me.cmccauley.iothub.data.repositories.MqttLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttLogService {

    private final MqttLogRepository mqttLogRepository;

    @Autowired
    public MqttLogService(MqttLogRepository mqttLogRepository) {
        this.mqttLogRepository = mqttLogRepository;
    }

    public void addLog(MqttLog mqttLog){
        mqttLogRepository.save(mqttLog);
    }
}
