package me.cmccauley.iothub.web.api;

import me.cmccauley.iothub.data.models.Topic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Map;

public class PublishController {


    @PostMapping("/publish/topic/{topicId}")
    public ResponseEntity publishMessage(@Valid @RequestBody Map message) {
        Topic ledTopic = Topic.led_builder_example();

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
