package me.cmccauley.iothub.api;

import me.cmccauley.iothub.services.mqtt.MqttService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class HubController {

    @Autowired
    private MqttService mqttService;

    @RequestMapping("/start")
    public String start() {
        try {
            mqttService.start();
            return "success";
        } catch (MqttException e) {
            e.printStackTrace();
            return "fuck";
        }
    }

    @RequestMapping("/leds")
    public String leds()
    {
      return "";
    }

    @RequestMapping("/publish")
    public String publish() {
       return "";
    }

    @RequestMapping("/subscribe")
    public String subscribe() {
        return "";
    }

}