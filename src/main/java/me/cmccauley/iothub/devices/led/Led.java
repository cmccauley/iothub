package me.cmccauley.iothub.devices.led;

public class Led {
    private int id;
    private String hue;
    private String saturation;
    private String value;

    public Led(int id, String hue, String saturation, String value) {
        this.id = id;
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHue() {
        return hue;
    }

    public void setHue(String hue) {
        this.hue = hue;
    }

    public String getSaturation() {
        return saturation;
    }

    public void setSaturation(String saturation) {
        this.saturation = saturation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
