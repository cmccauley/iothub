package me.cmccauley.iothub.mqtt;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.services.MqttMessageService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultCallback implements MqttCallback {

    private final MqttMessageService mqttMessageService;

    @Autowired
    public DefaultCallback(MqttMessageService mqttMessageService) {
        this.mqttMessageService = mqttMessageService;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("connection lost");
    }

    @Override
    public void messageArrived(String topic, org.eclipse.paho.client.mqttv3.MqttMessage mqttMessage) throws Exception {
        System.out.println("Message received:\n\t" + new String(mqttMessage.getPayload()));
        mqttMessageService.addMessage(new MqttMessage(topic, new String(mqttMessage.getPayload())));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
