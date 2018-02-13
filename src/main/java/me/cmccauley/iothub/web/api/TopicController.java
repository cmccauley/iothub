package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.Topic;
import me.cmccauley.iothub.data.repositories.TopicRepository;
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

    private final TopicRepository topicRepository;

    @Autowired
    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @PostMapping
    public ResponseEntity<?> createTopic(@Valid @RequestBody Topic topic) {
        final Topic createdTopic = topicRepository.save(topic);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdTopic.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{topicId}")
    public ResponseEntity<Topic> getTopicById(@PathVariable(value = "topicId") Long topicId) {
        return Optional
                .ofNullable(topicRepository.findOne(topicId))
                .map(topic -> ResponseEntity.ok().body(topic))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Collection<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @DeleteMapping("{topicId}")
    public void deleteTopic(@PathVariable(value = "topicId") Long topicId) {
        topicRepository.delete(topicId);
    }
}
