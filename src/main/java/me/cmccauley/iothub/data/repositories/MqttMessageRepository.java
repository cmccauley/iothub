package me.cmccauley.iothub.data.repositories;

import me.cmccauley.iothub.data.models.MqttMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MqttMessageRepository extends JpaRepository<MqttMessage, Long> {
    Collection<MqttMessage> findAllBySubscriptionId(Long subcriptionId);
}
