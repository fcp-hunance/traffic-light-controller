package model;

public interface TrafficTimerListener {
    void onPressedPedestrianButton();
    void onChangeToYellowLight();
    void onChangeToPedestrianGreen();
    void onReturnToCarGreen();
}
