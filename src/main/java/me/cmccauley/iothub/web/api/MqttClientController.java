package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.services.MqttService;
import me.cmccauley.iothub.web.dto.mqtt.StatusResponse;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/mqttclient")
public class MqttClientController {

    private final MqttService mqttService;

    @Autowired
    public MqttClientController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    @GetMapping("/status")
    public StatusResponse getStatus() {
        return new StatusResponse(mqttService.getMqttClient().isConnected());
    }

    @PostMapping("/connect")
    public ResponseEntity connect() throws MqttException {
        mqttService.start();
        return ok().build();
    }

    @PostMapping("/disconnect")
    public ResponseEntity disconnect() throws MqttException {
        mqttService.getMqttClient().disconnect();
        return ok().build();
    }

    @PostMapping("/reloadactivesubscriptions")
    public ResponseEntity reloadActiveSubscriptions() {
        mqttService.loadActiveSubscriptions();
        return ok().build();
    }
}
