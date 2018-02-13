package me.cmccauley.iothub.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfiguration {

    private final MqttCallback defaultCallback;
    @Value("")
    private String username;
    @Value("")
    private String password;

    @Autowired
    public MqttConfiguration(MqttCallback defaultCallback) {
        this.defaultCallback = defaultCallback;
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient("tcp://localhost:1884", "iothub");
        mqttClient.setCallback(defaultCallback);
        return mqttClient;
    }

}
