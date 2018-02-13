package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import me.cmccauley.iothub.services.SubscriptionService;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SubscriptionControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private Subscription savedSubscription1;
    private Subscription savedSubscription2;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        this.savedSubscription1 = subscriptionRepository.save(new Subscription("channel/savedSubscription1", true));
        this.savedSubscription2 = subscriptionRepository.save(new Subscription("channel/savedSubscription2", false));
    }

    @Test
    public void subscriptionNotFound() throws Exception {
        mockMvc.perform(get("/subscriptions/123")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void createSubscription() throws Exception {
        Subscription createdSubscription = new Subscription("createdSubscription", true);
        String subscriptionJson = json(createdSubscription);

        MvcResult mvcResult = mockMvc.perform(post("/subscriptions")
                .contentType(contentType)
                .content(subscriptionJson))
                .andExpect(status().isCreated()).andReturn();
        String location = mvcResult.getResponse().getHeader("location");
        assertTrue(location.contains("/subscriptions"));
    }

    @Test
    public void getSubscriptionById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/subscriptions/" + savedSubscription1.getId())).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(savedSubscription1.getTopicName()));
    }

    @Test
    public void getAllSubscriptions() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/subscriptions")).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains(savedSubscription1.getTopicName()) && response.contains(savedSubscription2.getTopicName()));
    }

    @Test
    public void deleteSubscription() throws Exception {
        mockMvc.perform(delete("/subscriptions/" + savedSubscription1.getId())).andExpect(status().isOk());
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