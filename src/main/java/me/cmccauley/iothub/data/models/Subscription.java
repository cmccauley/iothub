package me.cmccauley.iothub.data.models;

import javax.persistence.*;

@Entity
@Table(name = "subscription", schema = "iothub")
public class Subscription {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "active")
    private boolean active;

    public Subscription(Long id, String topic, boolean active) {
        this.id = id;
        this.topic = topic;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
