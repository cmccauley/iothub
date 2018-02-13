package me.cmccauley.iothub.mqtt;

import me.cmccauley.iothub.services.MqttMessageService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultCallback implements MqttCallback {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCallback.class);

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
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        LOG.info("Message received:\n\t" + new String(mqttMessage.getPayload()));
        mqttMessageService.handleCallbackMessage(topic, new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
