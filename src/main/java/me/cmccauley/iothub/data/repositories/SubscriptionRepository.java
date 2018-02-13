package me.cmccauley.iothub.data.repositories;

import me.cmccauley.iothub.data.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByTopicName(String topicName);

    Subscription findByTopicNameAndActiveTrue(String topicName);
}
