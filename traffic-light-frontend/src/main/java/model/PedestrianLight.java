package model;

public class PedestrianLight {
    private boolean isGreen;

    public void turnGreen() {
        isGreen = true;
    }

    public void turnRed() {
        isGreen = false;
    }

    public boolean isGreen() {
        return isGreen;
    }
}