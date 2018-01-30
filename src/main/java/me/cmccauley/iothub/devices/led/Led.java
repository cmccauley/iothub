package me.cmccauley.iothub.devices.led;

public class Led {
    private int id;
    private int hue;
    private int saturation;
    private int value;

    public Led(int id, int hue, int saturation, int value) {
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

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return String.format("%d,%d,%d,%d", id, hue, saturation, value);
    }
}
