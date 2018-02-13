package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SubscriptionService {
    private final static Logger LOG = LoggerFactory.getLogger(SubscriptionService.class);

    private final SubscriptionRepository subscriptionRepository;

    private final MqttService mqttService;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, MqttService mqttService) {
        this.subscriptionRepository = subscriptionRepository;
        this.mqttService = mqttService;
    }

    public Subscription createSubscription(Subscription subscription) {
        return getSubscriptionRepository().save(subscription);
    }

    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findOne(id);
    }

    public Subscription getSubscriptionByName(String subscriptionName) {
        return subscriptionRepository.findByTopicName(subscriptionName);
    }

    public void updateSubscription(Subscription subscription) {
        getSubscriptionRepository().save(subscription);
        if (subscription.isActive()) {
            mqttService.subscribe(subscription.getTopicName());
        } else {
            mqttService.unsubscribe(subscription.getTopicName());
        }
    }

    public void deleteSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findOne(id);
        getSubscriptionRepository().delete(id);
        mqttService.unsubscribe(subscription.getTopicName());
    }

    public Collection<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscription(Long id) {
        return getSubscriptionRepository().findOne(id);
    }

    public void reloadSubscriptions() {
        final List<Subscription> databaseSubscriptions = subscriptionRepository.findAll();
        databaseSubscriptions.stream().filter(Subscription::isActive).forEach(databaseSubscription -> {
            mqttService.subscribe(databaseSubscription.getTopicName());
        });
    }

    public SubscriptionRepository getSubscriptionRepository() {
        return subscriptionRepository;
    }

    public MqttService getMqttService() {
        return mqttService;
    }
}
