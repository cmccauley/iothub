package me.cmccauley.iothub.data.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscription", schema = "iothub")
public class MqttLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date created_at;
}
