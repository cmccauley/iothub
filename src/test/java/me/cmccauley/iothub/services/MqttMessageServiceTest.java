package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MqttMessageServiceTest {

    @MockBean
    private MqttMessageRepository mqttMessageRepository;


    @Test
    public void addLog() {
    }
}