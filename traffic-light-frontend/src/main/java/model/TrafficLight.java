package model;

public class TrafficLight {
    private boolean isGreen;
    private boolean isRed;

    public void turnGreen() {
        isGreen = true;
        isRed = false;
    }

    public void turnRed() {
        isGreen = false;
        isRed = true;
    }

    public void turnYellow() {
        isGreen = false;
        isRed = false;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public boolean isRed() {
        return isRed;
    }
}