package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<?> createTopic(@Valid @RequestBody Topic topic) {
        final Topic createdTopic = topicService.createTopic(topic);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdTopic.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{topicId}")
    public ResponseEntity<Topic> getTopicById(@PathVariable(value = "topicId") Long topicId) {
        return Optional
                .ofNullable(topicService.getTopicById(topicId))
                .map(topic -> ResponseEntity.ok().body(topic))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Collection<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @DeleteMapping("{topicId}")
    public void deleteTopic(@PathVariable(value = "topicId") Long topicId) {
        topicService.deleteTopic(topicId);
    }
}
