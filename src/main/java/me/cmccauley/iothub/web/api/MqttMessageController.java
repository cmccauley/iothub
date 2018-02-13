package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.services.MqttMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/mqttmessages")
public class MqttMessageController {

    private final MqttMessageService mqttMessageService;

    @Autowired
    public MqttMessageController(MqttMessageService mqttMessageService) {
        this.mqttMessageService = mqttMessageService;
    }

    @GetMapping
    public Collection<MqttMessage> getAllMqttMessages() {
        return mqttMessageService.getAllMqttMessages();
    }

    @GetMapping("{mqttMessageId}")
    public MqttMessage getMqttMessageById(@PathVariable(value = "mqttMessageId") Long mqttMessageId) {
        return mqttMessageService.getMqttMessageById(mqttMessageId);
    }

    @GetMapping("/subscription/{subscriptionId}")
    public Collection<MqttMessage> getMqttMessagesBySubscriptionId(@PathVariable(value = "subscriptionId") Long subscriptionId) {
        return mqttMessageService.getMqttMessagesBySubscriptionId(subscriptionId);
    }

}
