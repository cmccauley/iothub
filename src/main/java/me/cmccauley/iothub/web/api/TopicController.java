package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.MqttMessage;
import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
import me.cmccauley.iothub.services.MqttService;
import me.cmccauley.iothub.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("/topics")
    public Topic createTopic(@Valid @RequestBody Topic topic) {
        return topicService.createTopic(topic);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable(value = "id") Long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        if(topic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(topic);
    }
}
