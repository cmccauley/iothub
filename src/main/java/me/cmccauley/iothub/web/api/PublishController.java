package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.services.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PublishController {

    private final PublishService publishService;

    @Autowired
    public PublishController(PublishService publishService) {
        this.publishService = publishService;
    }

    @PostMapping("/publish/topic/{id}")
    public ResponseEntity publishMessage(@PathVariable(value = "id") Long topicId, @RequestBody Map<String, String> message) {
        publishService.publish(topicId, message);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
