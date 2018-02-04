package me.cmccauley.iothub.data.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "subscription", schema = "iothub")
public class MqttMessage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "message")
    private String message;

    @Column(nullable = false, updatable = false)
    private Timestamp arrived_at;

    public MqttMessage(String topic, String message) {
        this.topic = topic;
        this.message = message;
        this.arrived_at = new Timestamp(System.currentTimeMillis());
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getArrived_at() {
        return arrived_at;
    }
}
