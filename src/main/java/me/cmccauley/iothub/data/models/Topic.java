package me.cmccauley.iothub.data.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topic", schema = "iothub")
public class Topic {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated
    private MessageType messageType;

    @ElementCollection
    private List<String> parameterList;

    public Topic(String name, MessageType messageType, List<String> parameterList) {
        this.name = name;
        this.messageType = messageType;
        this.parameterList = parameterList;
    }

    public Long getId() {
        return id;
    }

    public Topic() {
    }

    public static Topic led_builder_example() {
        List<String> parameters = new ArrayList<>();
        parameters.add("H");
        parameters.add("S");
        parameters.add("V");

        return new Topic("/bedroom/led", MessageType.JSON, parameters);
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

    public List<String> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<String> parameterList) {
        this.parameterList = parameterList;
    }
}
