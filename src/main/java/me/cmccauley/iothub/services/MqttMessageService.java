package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    public MqttMessage getMqttMessageById(Long id)
    {
        return mqttMessageRepository.findOne(id);
    }

    public Collection<MqttMessage> getAllMqttMessages()
    {
        return mqttMessageRepository.findAll();
    }
}
