package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MqttClient mqttClient;

    // Keep track of loadedSubscriptions as the MqttClient doesn't have functionality to get current loadedSubscriptions.
    private final Set<String> loadedSubscriptions = new HashSet<>();

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, MqttClient mqttClient) {
        this.subscriptionRepository = subscriptionRepository;
        this.mqttClient = mqttClient;
    }

    public Subscription createSubscription(Subscription subscription) {
        return getSubscriptionRepository().save(subscription);
    }

    public Subscription getSubscriptionById(Long id)
    {
        return subscriptionRepository.findOne(id);
    }

    public void updateSubscription(Subscription subscription) {
        getSubscriptionRepository().save(subscription);
    }

    public void deleteSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findOne(id);
        getSubscriptionRepository().delete(id);
        try {
            mqttClient.unsubscribe(subscription.getTopicName());
            loadedSubscriptions.remove(subscription.getTopicName());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public Collection<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscription(Long id) {
        return getSubscriptionRepository().findOne(id);
    }

    public void subscribeFromDatabase() {
        final List<Subscription> databaseSubscriptions = subscriptionRepository.findAll();
        final List<Subscription> filteredSubs = databaseSubscriptions.stream()
                .filter(dbSub ->
                        loadedSubscriptions.contains(dbSub.getTopicName())).collect(Collectors.toList());
        filteredSubs.forEach(filteredSub -> {
            try {
                mqttClient.subscribe(filteredSub.getTopicName());
                loadedSubscriptions.add(filteredSub.getTopicName());
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }

    public SubscriptionRepository getSubscriptionRepository() {
        return subscriptionRepository;
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public Set<String> getLoadedSubscriptions() {
        return loadedSubscriptions;
    }
}
