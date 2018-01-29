package me.cmccauley.iothub.web.dto.mqtt;

public class SubscribeRequest {

    private String channel;

    public SubscribeRequest() {}

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
