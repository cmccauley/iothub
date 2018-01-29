package me.cmccauley.iothub.web.dto.mqtt;

import java.util.Collection;

public class TopicsResponse {

    private Collection<TopicResponse> topicResponses;

    public Collection<TopicResponse> getTopicResponses() {
        return topicResponses;
    }

    public void setTopicResponses(Collection<TopicResponse> topicResponses) {
        this.topicResponses = topicResponses;
    }
}
