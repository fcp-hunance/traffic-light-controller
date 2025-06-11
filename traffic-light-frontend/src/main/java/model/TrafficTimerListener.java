package model;

public interface TrafficTimerListener {
    void onChangeToYellowLight();
    void onChangeToPedestrianGreen();
    void onReturnToCarGreen();
}
