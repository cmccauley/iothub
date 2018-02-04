package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.Subscription;
import me.cmccauley.iothub.data.repositories.SubscriptionRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MqttClient mqttClient;

    // Keep track of subscriptions as the MqttClient doesn't have functionality to get current subscriptions.
    private final Set<String> subscriptions = new HashSet<>();

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, MqttClient mqttClient) {
        this.subscriptionRepository = subscriptionRepository;
        this.mqttClient = mqttClient;
    }

    public void addSubscription(String topic) {
        getSubscriptionRepository().save(new Subscription(topic, true));
        try {
            mqttClient.subscribe(topic); // Todo: Handle Active/Inactive boolean7
            subscriptions.add(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void updateSubscription(Subscription subscription){
        // Todo: if-exist check somewhere
        getSubscriptionRepository().save(subscription);
    }

    public void deleteSubscription(String topic) {
        getSubscriptionRepository().deleteByTopic(topic);
        try {
            mqttClient.unsubscribe(topic);
            subscriptions.remove(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public List<Subscription> getSubcriptions(){
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscription(Long id){
        return getSubscriptionRepository().findOne(id);
    }

    public void subscribeFromDatabase() {
        final List<Subscription> databaseSubscriptions = subscriptionRepository.findAll();
        final List<Subscription> filteredSubs = databaseSubscriptions.stream()
                .filter(dbSub ->
                        subscriptions.contains(dbSub.getTopic())).collect(Collectors.toList());
        filteredSubs.forEach(filteredSub -> {
            try {
                mqttClient.subscribe(filteredSub.getTopic());
                subscriptions.add(filteredSub.getTopic());
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

    public Set<String> getSubscriptions() {
        return subscriptions;
    }
}
