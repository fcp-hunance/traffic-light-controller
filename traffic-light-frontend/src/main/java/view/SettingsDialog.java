package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import controller.TrafficController;

public class SettingsDialog {
    private final TrafficController controller;
    private final Stage dialogStage;
    private Spinner<Integer> carGreenSpinner;
    private Spinner<Integer> pedestrianGreenSpinner;
    private Spinner<Integer> delaySpinner;

    public SettingsDialog(TrafficController controller) {
        this.controller = controller;
        this.dialogStage = new Stage();
        initializeUI();
    }

    private void initializeUI() {
        dialogStage.setTitle("Traffic Light Settings");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Car Green Duration
        grid.add(new Label("Car Green Duration (sec):"), 0, 0);
        carGreenSpinner = new Spinner<>(10, 120,
                controller.getTrafficTimer().getCarGreenDuration(), 1);
        grid.add(carGreenSpinner, 1, 0);

        // Pedestrian Green Duration
        grid.add(new Label("Pedestrian Green Duration (sec):"), 0, 1);
        pedestrianGreenSpinner = new Spinner<>(5, 60,
                controller.getTrafficTimer().getPedestrianGreenDuration(), 1);
        grid.add(pedestrianGreenSpinner, 1, 1);

        // Change Delay
        grid.add(new Label("Change Delay After Button (sec):"), 0, 2);
        delaySpinner = new Spinner<>(1, 30,
                controller.getTrafficTimer().getChangeDelay(), 1);
        grid.add(delaySpinner, 1, 2);

        // Buttons
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(10, 0, 0, 0));

        Button saveButton = new Button("Save Settings");
        saveButton.setOnAction(e -> saveSettings());

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> dialogStage.close());

        buttonBar.getButtons().addAll(saveButton, cancelButton);
        grid.add(buttonBar, 0, 3, 2, 1);

        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
    }

    public void show() {
        dialogStage.showAndWait();
    }

    private void saveSettings() {
        int carGreen = carGreenSpinner.getValue();
        int pedestrianGreen = pedestrianGreenSpinner.getValue();
        int delay = delaySpinner.getValue();

        controller.updateSettings(carGreen, pedestrianGreen, delay);
        dialogStage.close();
    }
}