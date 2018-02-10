package me.cmccauley.iothub.web.dto.mqtt;

public class StatusResponse {

    private boolean isConnected;

    public StatusResponse() {
    }

    public StatusResponse(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
