package me.cmccauley.iothub.dto.mqtt;

public class PublishRequest {

    private String message;

    public PublishRequest(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
