package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import me.cmccauley.iothub.services.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TopicController {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @PostMapping("/topics")
    public Topic createTopic(@Valid @RequestBody Topic topic) {
        return topicRepository.save(topic);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable(value = "id") Long topicId) {
        Topic topic = topicRepository.findOne(topicId);
        if(topic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(topic);
    }

    public TopicRepository getTopicRepository() {
        return topicRepository;
    }
}
