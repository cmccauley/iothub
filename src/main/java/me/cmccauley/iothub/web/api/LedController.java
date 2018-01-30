package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.devices.led.Led;
import me.cmccauley.iothub.services.mqtt.MqttService;
import me.cmccauley.iothub.services.mqtt.messages.impl.LedMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/led")
public class LedController {

    @Autowired
    private MqttService mqttService;

    @RequestMapping("/lit")
    public String lit() throws MqttException {
        // That is lit !

        List<Led> leds = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            Led l = new Led(i, (i * i) % 255, 255, 50);
            leds.add(l);
        }

        LedMessage message = new LedMessage();
        message.setLeds(leds);

        mqttService.publish("/livingroom/leds", message);
        return "that was lit";
    }
}
