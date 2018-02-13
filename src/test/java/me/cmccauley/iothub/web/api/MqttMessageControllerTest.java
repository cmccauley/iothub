package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.MqttMessageRepository;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
public class MqttMessageControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private Subscription savedSubscription1;

    private MqttMessage savedMqttMessage1;
    private MqttMessage savedMqttMessage2;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MqttMessageRepository mqttMessageRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        this.mqttMessageRepository.deleteAllInBatch();
        this.subscriptionRepository.deleteAllInBatch();

        this.savedSubscription1 = subscriptionRepository.save(new Subscription("channel/savedSubscription1", true));

        this.savedMqttMessage1 = mqttMessageRepository.save(new MqttMessage(this.savedSubscription1, "message1"));
        this.savedMqttMessage2 = mqttMessageRepository.save(new MqttMessage(this.savedSubscription1, "message2"));
    }

    @Test
    public void getMqttMessageById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/mqttmessages/" + savedMqttMessage1.getId())).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(savedMqttMessage1.getMessage()));
    }

    @Test
    public void getAllMqttMessages() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/mqttmessages/")).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(savedMqttMessage1.getMessage()) && response.contains(savedMqttMessage2.getMessage()));
    }

    @Test
    public void getMqttMessageBySubscriptionId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/mqttmessages/subscription/" + savedSubscription1.getId())).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(savedMqttMessage1.getMessage()));
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}