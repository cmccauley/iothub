package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.services.MqttService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HubController {

    @Autowired
    private MqttService mqttService;

    @RequestMapping()
    public String home()
    {
        return "home";
    }

    @RequestMapping("/connect")
    public String start() {
        try {
            mqttService.start();
            return "success";
        } catch (MqttException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @RequestMapping("/publish")
    public String publish() throws MqttException {
        return "";
    }

}
