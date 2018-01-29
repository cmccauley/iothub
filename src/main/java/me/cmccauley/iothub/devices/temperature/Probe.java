package me.cmccauley.iothub.devices.temperature;

public class Probe {

    private int celsius;

    public Probe(int celsius) {
        this.celsius = celsius;
    }

    public int getCelsius() {
        return celsius;
    }

    public void setCelsius(int celsius) {
        this.celsius = celsius;
    }
}
