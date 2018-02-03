package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.repositories.MqttLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class MqttLogServiceTest {

    @MockBean
    private MqttLogRepository mqttLogRepository;


    @Test
    public void addLog() {
    }
}