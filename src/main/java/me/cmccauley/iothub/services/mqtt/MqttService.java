package me.cmccauley.iothub.services.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MqttService {

    @Autowired
    private MqttClient mqttClient;
    @Autowired
    private DefaultCallback defaultCallback;
    @Autowired
    private MqttConnectOptions mqttConnectOptions;

    public void start() throws MqttException{
        mqttClient.setCallback(defaultCallback);
        mqttClient.connect(mqttConnectOptions);
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public DefaultCallback getDefaultCallback() {
        return defaultCallback;
    }

    public void setDefaultCallback(DefaultCallback defaultCallback) {
        this.defaultCallback = defaultCallback;
    }

    public MqttConnectOptions getMqttConnectOptions() {
        return mqttConnectOptions;
    }

    public void setMqttConnectOptions(MqttConnectOptions mqttConnectOptions) {
        this.mqttConnectOptions = mqttConnectOptions;
    }
}