package me.cmccauley.iothub;

import me.cmccauley.iothub.services.MqttService;
import me.cmccauley.iothub.services.SubscriptionService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartupInit {
    private static final Logger LOG = LoggerFactory.getLogger(StartupInit.class);

    private final MqttService mqttService;
    @Autowired
    public StartupInit(MqttService mqttService ) {
        this.mqttService = mqttService;
    }

    @PostConstruct
    public void init() {
        // Attempt to connect to the MqttClient when starting. This will fail if the Mqtt server is not available.
        try {
            mqttService.start();
            LOG.info("Mqtt Client successfully connected during startup");
        } catch (MqttException e) {
            LOG.warn("Failed to connect the Mqtt Client during startup");
        }
    }
}
