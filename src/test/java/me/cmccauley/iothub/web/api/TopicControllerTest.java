package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import me.cmccauley.iothub.services.TopicService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TopicControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String topicName1 = "channel/savedTopic1";
    private String topicName2 = "channel/savedTopic2";

    private Topic savedTopic1;
    private Topic savedTopic2;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        this.savedTopic1 = topicRepository.save(new Topic(topicName1, Collections.EMPTY_SET));
        this.savedTopic2 = topicRepository.save(new Topic(topicName2, Collections.EMPTY_SET));
    }

    @Test
    public void topicNotFound() throws Exception {
        mockMvc.perform(get("/topics/123")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void createTopic() throws Exception {
        Topic createdTopic = new Topic("createdTopic", Collections.EMPTY_SET);
        String topicJson = json(createdTopic);

        MvcResult mvcResult = mockMvc.perform(post("/topics")
                .contentType(contentType)
                .content(topicJson))
                .andExpect(status().isCreated()).andReturn();
        String location = mvcResult.getResponse().getHeader("location");
        assertTrue(location.contains("/topics"));
    }

    @Test
    public void getTopicById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/topics/" + savedTopic1.getId())).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(topicName1));
    }

    @Test
    public void getAllTopics() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/topics")).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(topicName1) && response.contains(topicName2));
    }

    @Test
    public void deleteTopic() throws Exception {
        mockMvc.perform(delete("/topics/" + savedTopic1.getId())).andExpect(status().isOk());
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