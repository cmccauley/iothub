package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PublishControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Topic savedTopic1;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Topic topic = new Topic();
        Set<String> params = new HashSet<>();
        params.add("a");
        params.add("b");
        params.add("c");
        topic.setName("topic1");
        topic.setParameterList(params);
        this.savedTopic1 = topicRepository.save(topic);
    }

    @Test
    public void publishMessage() throws Exception {
        Map<String, String> message = new HashMap<>();
        message.put("a", "123");
        message.put("b", "123");
        message.put("c", "123");

        mockMvc.perform(post("/publish/topic/" + savedTopic1.getId())
                .contentType(contentType)
                .content(json(message)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void publishMessageList() throws Exception {
        List<Map<String, String>> messageList = new ArrayList<>();

        Map<String, String> message1 = new HashMap<>();
        message1.put("a", "123");
        message1.put("b", "123");
        message1.put("c", "123");

        Map<String, String> message2 = new HashMap<>();
        message2.put("a", "456");
        message2.put("b", "456");
        message2.put("c", "456");

        messageList.add(message1);
        messageList.add(message2);


        mockMvc.perform(post("/publish/topic/" + savedTopic1.getId() + "/list")
                .contentType(contentType)
                .content(json(messageList)))
                .andExpect(status().isAccepted());
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