package me.cmccauley.iothub.data.repositories;

import me.cmccauley.iothub.data.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findOneByName(String name);
}
