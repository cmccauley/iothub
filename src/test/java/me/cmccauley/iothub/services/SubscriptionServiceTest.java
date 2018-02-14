package me.cmccauley.iothub.services;

import me.cmccauley.iothub.IothubApplication;
import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IothubApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private MqttService mqttService;

    @Before
    public void setup() throws Exception {
        subscriptionService.setMqttService(mqttService);
        subscriptionService.setSubscriptionRepository(subscriptionRepository);
    }

    @Test
    public void createSubscription() {
        Subscription subscription = new Subscription();
        subscription.setTopicName("topic1");
        subscription.setActive(true);
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        //When
        Subscription savedSubscription = subscriptionService.createSubscription(subscription);
        //Then
        assertEquals(subscription, savedSubscription);
    }

    @Test
    public void getSubscriptionById() {
        Subscription subscription = new Subscription();
        subscription.setTopicName("topic1");
        subscription.setActive(true);

        when(subscriptionRepository.findOne(1L)).thenReturn(subscription);

        //When
        Subscription savedSubscription = subscriptionService.getSubscriptionById(1L);
        //Then
        assertEquals(subscription, savedSubscription);
    }

    @Test
    public void getSubscriptionByName() {
        Subscription subscription = new Subscription();
        subscription.setTopicName("topic1");
        subscription.setActive(true);

        when(subscriptionRepository.findByTopicName(subscription.getTopicName())).thenReturn(subscription);

        //When
        Subscription savedSubscription = subscriptionService.getSubscriptionByName(subscription.getTopicName());
        //Then
        assertEquals(subscription, savedSubscription);
    }

    @Test
    public void updateActiveSubscription() {
        Subscription subscription = new Subscription();
        subscription.setTopicName("topic1");
        subscription.setActive(true);

        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        //When
        subscriptionService.updateSubscription(subscription);
        //Then
        verify(subscriptionRepository, atLeastOnce()).save(subscription);
        verify(mqttService, atLeastOnce()).subscribe(subscription.getTopicName());
    }

    @Test
    public void updateInactiveSubscription() {
        Subscription subscription = new Subscription();
        subscription.setTopicName("topic1");
        subscription.setActive(false);

        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        //When
        subscriptionService.updateSubscription(subscription);
        //Then
        verify(subscriptionRepository, atLeastOnce()).save(subscription);
        verify(mqttService, never()).subscribe(subscription.getTopicName());
        verify(mqttService, atLeastOnce()).unsubscribe(subscription.getTopicName());
    }

    @Test
    public void deleteSubscription() {
        Subscription subscription = new Subscription();
        subscription.setTopicName("topic1");
        subscription.setActive(true);

        when(subscriptionRepository.findOne(1L)).thenReturn(subscription);
        doNothing().when(subscriptionRepository).delete(1L);

        //When
        subscriptionService.deleteSubscription(1L);
        //Then
        verify(subscriptionRepository, atLeastOnce()).delete(1L);
        verify(mqttService, atLeastOnce()).unsubscribe(subscription.getTopicName());
    }

    @Test
    public void getAllSubscriptions() {
        Subscription subscription1 = new Subscription();
        subscription1.setTopicName("topic1");
        subscription1.setActive(true);

        Subscription subscription2 = new Subscription();
        subscription1.setTopicName("topic2");
        subscription1.setActive(true);

        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);

        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        //When
        Collection<Subscription> allSubscriptions = subscriptionService.getAllSubscriptions();
        //Then
        assertTrue(allSubscriptions.containsAll(subscriptions));
    }
}