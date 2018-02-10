package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mqtt")
public class MqttMessageController {

    private final MqttMessageRepository mqttMessageRepository;

    @Autowired
    public MqttMessageController(MqttMessageRepository mqttMessageRepository)
    {
        this.mqttMessageRepository = mqttMessageRepository;
    }

    @GetMapping("/messages")
    public List<MqttMessage> getAllmessages() {
        return mqttMessageRepository.findAll();
    }

    @PostMapping("/messages")
    public MqttMessage createLog(@Valid @RequestBody MqttMessage mqttMessage) {
        return mqttMessageRepository.save(mqttMessage);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<MqttMessage> getMessageById(@PathVariable(value = "id") Long mqttLogId) {
        MqttMessage mqttMessage = mqttMessageRepository.findOne(mqttLogId);
        if(mqttMessage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(mqttMessage);
    }

}