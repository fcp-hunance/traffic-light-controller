package controller;

import model.TrafficLight;
import model.PedestrianLight;
import model.TrafficTimer;
import model.TrafficTimerListener;


public class TrafficController implements TrafficTimerListener {
    private final TrafficLight trafficLight;
    private final PedestrianLight pedestrianLight;
    private final TrafficTimer trafficTimer;
    private Runnable onLightChangeCallback;
    private final TrafficLightHttpClient httpClient;

    public TrafficController(TrafficLight trafficLight, PedestrianLight pedestrianLight, TrafficTimer trafficTimer, TrafficLightHttpClient httpClient) {
        this.trafficLight = trafficLight;
        this.pedestrianLight = pedestrianLight;
        this.trafficTimer = trafficTimer;
        this.trafficTimer.setListener(this);
        this.httpClient = httpClient;

        initializeSystem();
    }

    public void setOnLightChangeCallback(Runnable callback) {
        this.onLightChangeCallback = callback;
    }

    private void initializeSystem() {
        trafficLight.turnGreen();
        pedestrianLight.turnRed();
    }

    public void pedestrianButtonPressed() {
        System.out.println("Pedestrian Button pressed!");
        if (trafficLight.isGreen() && !trafficTimer.isChanging()) {
            trafficTimer.startSequence();
        }
    }

    public void updateSettings(int pedestrianGreenDuration, int changeDelay) {
        trafficTimer.setDurations(pedestrianGreenDuration, changeDelay);
        httpClient.onSettingsUpdate();
    }

    @Override
    public void onChangeToYellowLight() {
        System.out.println("Lights changed: Cars Yellow, Pedestrians Red");
        trafficLight.turnYellow();
        pedestrianLight.turnRed();
        notifyUI();
    }

    @Override
    public void onChangeToPedestrianGreen() {
        trafficLight.turnRed();
        pedestrianLight.turnGreen();
        System.out.println("Lights changed: Pedestrians Green, Cars Red");
        notifyUI();
        httpClient.onChangePedestrianButtonPressed();
    }

    @Override
    public void onReturnToCarGreen() {
        trafficLight.turnGreen();
        pedestrianLight.turnRed();
        System.out.println("Lights changed: Cars Green, Pedestrians Red");
        notifyUI();
    }

    private void notifyUI() {
        if (onLightChangeCallback != null) {
            // Run on JavaFX Application Thread because updateLights modifies UI
            javafx.application.Platform.runLater(onLightChangeCallback);
        }
    }

    // Getters for the view
    public TrafficLight getTrafficLight() { return trafficLight; }
    public PedestrianLight getPedestrianLight() { return pedestrianLight; }
    public TrafficTimer getTrafficTimer() { return trafficTimer; }
}
