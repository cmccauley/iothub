package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.services.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/publish")
public class PublishController {

    private final PublishService publishService;

    @Autowired
    public PublishController(PublishService publishService) {
        this.publishService = publishService;
    }

    @PostMapping("/topic/{topicId}")
    public ResponseEntity<?> publishMessage(@PathVariable(value = "topicId") Long topicId, @RequestBody Map<String, String> message) {
        publishService.publish(topicId, message);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/topic/{topicId}/list")
    public ResponseEntity<?> publishMessageList(@PathVariable(value = "topicId") Long topicId, @RequestBody List<Map<String, String>> messageList) {
        publishService.publishList(topicId, messageList);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
