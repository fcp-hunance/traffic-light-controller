package model;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficTimer {
    private int pedestrianGreenDuration; // seconds
    private int changeDelay; // seconds after button press

    private Timer timer;
    private boolean isChanging;

    private TrafficTimerListener listener;

    public TrafficTimer(int pedestrianGreenDuration, int changeDelay) {
        this.pedestrianGreenDuration = pedestrianGreenDuration;
        this.changeDelay = changeDelay;
    }

    public void setListener(TrafficTimerListener listener) {
        this.listener = listener;
    }

    public void startSequence() {
        if (isChanging) return;

        isChanging = true;
        listener.onPressedPedestrianButton();
        timer = new Timer();

        // After the delay, change lights to pedestrian green
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onChangeToYellowLight();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onChangeToPedestrianGreen();
                        }

                        // After pedestrianGreenDuration, return to car green
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (listener != null) {
                                    listener.onReturnToCarGreen();
                                }
                                isChanging = false;
                                timer.cancel();
                            }
                        }, pedestrianGreenDuration * 1000L);
                    }
                }, 5 * 1000L);  // 5 seconds yellow light duration
            }
        }, changeDelay * 1000L);
    }

    public void setDurations(int pedestrianGreen, int delay) {
        this.pedestrianGreenDuration = pedestrianGreen;
        this.changeDelay = delay;
    }

    public boolean isChanging() {
        return isChanging;
    }

    // Getters for durations
    public int getPedestrianGreenDuration() { return pedestrianGreenDuration; }
    public int getChangeDelay() { return changeDelay; }
}