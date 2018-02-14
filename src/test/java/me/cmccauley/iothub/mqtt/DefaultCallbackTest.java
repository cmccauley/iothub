package me.cmccauley.iothub.mqtt;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.mqtt.impl.AnnounceMessageHandler;
import me.cmccauley.iothub.mqtt.impl.DataMessageHandler;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class DefaultCallbackTest {

    private DefaultCallback defaultCallBack;

    @Mock
    private AnnounceMessageHandler announceMessageHandler;

    @Mock
    private DataMessageHandler dataMessageHandler;

    private Map<String, CallbackMessageHandler> callbackStrategies = new HashMap<>();

    @Before
    public void setup() {
        defaultCallBack = new DefaultCallback(callbackStrategies);

        callbackStrategies.put("dataMessageHandler", dataMessageHandler);
        callbackStrategies.put("announceMessageHandler", announceMessageHandler);
        defaultCallBack.setCallbackStrategies(callbackStrategies);
    }

    @Test
    public void dataMessageArrived() throws Exception {
        MqttMessage pahoMqttMessage = new MqttMessage();
        pahoMqttMessage.setPayload("abc".getBytes());

        doNothing().when(dataMessageHandler).handleCallbackMessage(any(), any());

        defaultCallBack.messageArrived("/abc", pahoMqttMessage);

        verify(dataMessageHandler, times(1)).handleCallbackMessage(any(), any());
    }

    @Test
    public void announcementMessageArrived() throws Exception {
        MqttMessage pahoMqttMessage = new MqttMessage();
        pahoMqttMessage.setPayload("a,b,c".getBytes());

        defaultCallBack.messageArrived("/announcement/abc", pahoMqttMessage);

        doNothing().when(announceMessageHandler).handleCallbackMessage(any(), any());

        verify(announceMessageHandler, times(1)).handleCallbackMessage(any(), any());
    }
}