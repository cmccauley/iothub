package me.cmccauley.iothub.mqtt;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.services.MqttMessageService;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
public class DefaultCallbackTest {

    @InjectMocks
    private DefaultCallback defaultCallBack;

    @Mock
    private MqttMessageService mqttMessageService;

    @Before
    public void setup() {
        defaultCallBack.setMqttMessageService(mqttMessageService);
    }

    @Test
    public void messageArrived() throws Exception {
        doNothing().when(mqttMessageService).handleCallbackMessage("topic1", "message1");

        MqttMessage pahoMqttMessage = new MqttMessage();
        pahoMqttMessage.setPayload("message1".getBytes());
        defaultCallBack.messageArrived("topic1", pahoMqttMessage);

        verify(mqttMessageService, times(1)).handleCallbackMessage("topic1", "message1");
    }
}