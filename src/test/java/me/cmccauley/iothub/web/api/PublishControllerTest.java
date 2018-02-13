package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.IothubApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
public class PublishControllerTest {

    @Test
    public void publishMessage() {
    }

    @Test
    public void publishMessageList() {
    }
}