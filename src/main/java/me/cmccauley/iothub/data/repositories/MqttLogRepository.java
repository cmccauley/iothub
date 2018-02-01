package me.cmccauley.iothub.data.repositories;

import me.cmccauley.iothub.data.models.MqttLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MqttLogRepository extends JpaRepository<MqttLog, Long> {
}
