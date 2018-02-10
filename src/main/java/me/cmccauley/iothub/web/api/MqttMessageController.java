package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import me.cmccauley.iothub.services.MqttMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("mqttMessageId")
    public MqttMessage getMqttMessageById(@PathVariable(value = "mqttMessageId") Long mqttMessageId) {
        return mqttMessageService.getMqttMessageById(mqttMessageId);
    }

}
