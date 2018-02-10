package me.cmccauley.iothub.data.models;

import javax.persistence.*;

@Entity
@Table(name = "subscription", schema = "iothub")
public class Subscription {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topicName")
    private String topicName;

    @Column(name = "active")
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
