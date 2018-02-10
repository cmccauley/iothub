package me.cmccauley.iothub.services;

import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void createTopic(){

    }

    public List<Topic> getAllTopics(){
        return null;
    }

    public TopicRepository getTopicRepository() {
        return topicRepository;
    }
}
