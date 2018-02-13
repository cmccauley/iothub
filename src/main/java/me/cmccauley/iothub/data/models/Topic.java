package me.cmccauley.iothub.data.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "topic", schema = "iothub")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ElementCollection
    private Set<String> parameterList;

    public Topic() {
    }

    public Topic(String name, Set<String> parameterList) {
        this.name = name;
        this.parameterList = parameterList;
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

    public Set<String> getParameterList() {
        return parameterList;
    }

    public void setParameterList(Set<String> parameterList) {
        this.parameterList = parameterList;
    }
}
