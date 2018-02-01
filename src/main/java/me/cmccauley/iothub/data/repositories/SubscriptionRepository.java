package me.cmccauley.iothub.data.repositories;

import me.cmccauley.iothub.data.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    void deleteByTopic(String topic);
}
