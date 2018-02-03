package me.cmccauley.iothub.mqtt;

import me.cmccauley.iothub.data.models.MqttLog;
import me.cmccauley.iothub.services.MqttLogService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultCallback implements MqttCallback {

    private final MqttLogService mqttLogService;

    @Autowired
    public DefaultCallback(MqttLogService mqttLogService) {
        this.mqttLogService = mqttLogService;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("connection lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message received:\n\t" + new String(mqttMessage.getPayload()));
        mqttLogService.addLog(new MqttLog(topic,new String(mqttMessage.getPayload())));

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
