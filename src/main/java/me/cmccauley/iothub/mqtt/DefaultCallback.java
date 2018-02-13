package me.cmccauley.iothub.mqtt;

import me.cmccauley.iothub.services.MqttMessageService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultCallback implements MqttCallback {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCallback.class);

    private Map<String, CallbackMessageHandler> callbackStrategies;

    @Autowired
    public DefaultCallback(Map<String, CallbackMessageHandler> callbackStrategies) {
        this.callbackStrategies = callbackStrategies;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("connection lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        LOG.info("Message received:" + new String(mqttMessage.getPayload()));

        CallbackMessageHandler message;
        if (topic.startsWith("/announce")) {
            message = callbackStrategies.get("announceMessageHandler");
        } else {
            message = callbackStrategies.get("dataMessageHandler");
        }

        message.handleCallbackMessage(topic, mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public Map<String, CallbackMessageHandler> getCallbackStrategies() {
        return callbackStrategies;
    }

    public void setCallbackStrategies(Map<String, CallbackMessageHandler> callbackStrategies) {
        this.callbackStrategies = callbackStrategies;
    }
}
