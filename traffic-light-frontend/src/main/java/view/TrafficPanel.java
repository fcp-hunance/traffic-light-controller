package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import controller.TrafficController;

public class TrafficPanel extends AnchorPane {
    private final TrafficController controller;

    @FXML private Circle trafficRed;
    @FXML private Circle trafficYellow;
    @FXML private Circle trafficGreen;
    @FXML private Circle pedestrianRed;
    @FXML private Circle pedestrianGreen;

    public TrafficPanel(TrafficController controller) {
        this.controller = controller;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrafficPanel.fxml"));
            loader.setController(this);
            AnchorPane root= loader.load();
            this.getChildren().add(root);

            controller.setOnLightChangeCallback(this::updateLights);

            // Initial update
            updateLights();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLights() {
        // Update traffic light
        if (controller.getTrafficLight().isGreen()) {
            trafficRed.setFill(javafx.scene.paint.Color.DARKGRAY);
            trafficYellow.setFill(javafx.scene.paint.Color.DARKGRAY);
            trafficGreen.setFill(javafx.scene.paint.Color.GREEN);
        } else if (controller.getTrafficLight().isRed()) {
            trafficRed.setFill(javafx.scene.paint.Color.RED);
            trafficYellow.setFill(javafx.scene.paint.Color.DARKGRAY);
            trafficGreen.setFill(javafx.scene.paint.Color.DARKGRAY);
        } else {
            trafficRed.setFill(javafx.scene.paint.Color.DARKGRAY);
            trafficYellow.setFill(javafx.scene.paint.Color.YELLOW);
            trafficGreen.setFill(javafx.scene.paint.Color.DARKGRAY);
        }

        // Update pedestrian light
        if (controller.getPedestrianLight().isGreen()) {
            pedestrianRed.setFill(javafx.scene.paint.Color.DARKGRAY);
            pedestrianGreen.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            pedestrianRed.setFill(javafx.scene.paint.Color.RED);
            pedestrianGreen.setFill(javafx.scene.paint.Color.DARKGRAY);
        }
    }
}