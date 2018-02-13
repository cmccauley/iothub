package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MqttMessageService {
    private final static Logger LOG = LoggerFactory.getLogger(MqttMessageService.class);

    private MqttMessageRepository mqttMessageRepository;

    @Autowired
    public MqttMessageService(MqttMessageRepository mqttMessageRepository) {
        this.mqttMessageRepository = mqttMessageRepository;
    }

    public MqttMessage getMqttMessageById(Long subscriptionId) {
        return mqttMessageRepository.findOne(subscriptionId);
    }

    public Collection<MqttMessage> getMqttMessagesBySubscriptionId(Long subscriptionId) {
        return mqttMessageRepository.findAllBySubscriptionId(subscriptionId);
    }

    public Collection<MqttMessage> getAllMqttMessages() {
        return mqttMessageRepository.findAll();
    }

    public MqttMessageRepository getMqttMessageRepository() {
        return mqttMessageRepository;
    }

    public void setMqttMessageRepository(MqttMessageRepository mqttMessageRepository) {
        this.mqttMessageRepository = mqttMessageRepository;
    }
}
