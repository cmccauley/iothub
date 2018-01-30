package me.cmccauley.iothub.services.mqtt.messages.impl;

import me.cmccauley.iothub.devices.led.Led;
import me.cmccauley.iothub.services.mqtt.messages.HubMqttMessage;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class LedMessage implements HubMqttMessage {

    private List<Led> leds;

    @Value("${message.max_size}")
    private int MAX_SIZE = 100;

    private final String DELIMITER = ";";


    public List<Led> getLeds() {
        return leds;
    }

    public void setLeds(List<Led> leds) {
        this.leds = leds;
    }

    @Override
    public List<String> getMessages() {
        final List<String> ledMessages = new ArrayList<>();

        final StringBuilder messageStringBuilder = new StringBuilder();
        for(Led led : leds)
        {
            int ledMsgSize = led.toString().getBytes().length;

            if(ledMsgSize > MAX_SIZE)
                throw new IllegalArgumentException("Led bigger than Message");

            int futureSize = ledMsgSize + messageStringBuilder.toString().getBytes().length;

            if(futureSize  <= MAX_SIZE)
            {
                messageStringBuilder.append(led);
                messageStringBuilder.append(DELIMITER);
            }
            else
            {
                ledMessages.add(messageStringBuilder.toString());
                messageStringBuilder.setLength(0);
            }
        }

        if(messageStringBuilder.length() > 0){
            ledMessages.add(messageStringBuilder.toString());
        }

        return ledMessages;
    }
}
