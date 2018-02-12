package me.cmccauley.iothub.data.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subscription", schema = "iothub")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    private Set<MqttMessage> mqttMessages;

    @Column(name = "active", nullable = false)
    private boolean active;

    public Subscription() {
    }

    public Subscription(String topicName, boolean active) {
        this.topicName = topicName;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Set<MqttMessage> getMqttMessages() {
        return mqttMessages;
    }

    public void setMqttMessages(Set<MqttMessage> mqttMessages) {
        this.mqttMessages = mqttMessages;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
