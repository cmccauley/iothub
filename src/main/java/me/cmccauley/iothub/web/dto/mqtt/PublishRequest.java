package me.cmccauley.iothub.web.dto.mqtt;

public class PublishRequest {

    private String message;
    private String channel;

    public PublishRequest(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
