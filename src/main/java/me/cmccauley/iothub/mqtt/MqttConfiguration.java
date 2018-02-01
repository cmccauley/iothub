package me.cmccauley.iothub.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfiguration {

    @Value("")
    private String username;

    @Value("")
    private String password;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        return new MqttClient("tcp://localhost:1883", "iothub");
    }

    @Bean
    public MqttConnectOptions mqttConnectOptions() throws MqttException {
        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName("dispatcher");
        mqttConnectOptions.setPassword("alice".toCharArray());
        return new MqttConnectOptions();
    }

}
