package me.cmccauley.iothub.data.models;

import me.cmccauley.iothub.enums.MessageType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topic", schema = "iothub")
public class Topic {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Enumerated
    private MessageType messageType;

    @ElementCollection
    private Set<String> parameterList;

    public Topic(String name, MessageType messageType, Set<String> parameterList) {
        this.name = name;
        this.messageType = messageType;
        this.parameterList = parameterList;
    }

    public Topic() {
    }

    public static Topic led_builder_example() {
        Set<String> parameters = new HashSet<>();
        parameters.add("H");
        parameters.add("S");
        parameters.add("V");

        return new Topic("/bedroom/led", MessageType.JSON, parameters);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Set<String> getParameterList() {
        return parameterList;
    }

    public void setParameterList(Set<String> parameterList) {
        this.parameterList = parameterList;
    }
}
