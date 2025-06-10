package view;

import controller.TrafficController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainFrame {
    private final TrafficController controller;
    private Stage primaryStage;

    @Autowired
    public MainFrame(TrafficController controller) {
        this.controller = controller;
    }

    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }

    private void createUI() {
        BorderPane root = new BorderPane();

        // Create and configure the traffic panel
        TrafficPanel trafficPanel = new TrafficPanel(controller);
        StackPane centerPane = new StackPane(trafficPanel);
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);

        // Create button panel
        HBox buttonPanel = new HBox(10);
        buttonPanel.setStyle("-fx-padding: 10; -fx-alignment: center;");

        Button pedestrianButton = new Button("Pedestrian Request");
        pedestrianButton.setOnAction(e -> controller.pedestrianButtonPressed());

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> showSettingsDialog());

        buttonPanel.getChildren().addAll(pedestrianButton, settingsButton);
        root.setBottom(buttonPanel);

        // Configure the stage
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zebra Crossing Simulator");
        primaryStage.show();
    }

    private void showSettingsDialog() {
        SettingsDialog dialog = new SettingsDialog(controller);
        dialog.show();
    }
}