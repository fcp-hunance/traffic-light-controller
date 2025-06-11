package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.TrafficTimer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class TrafficLightHttpClient {

    private final RestTemplate restTemplate;
    private final TrafficTimer trafficTimer;

    public TrafficLightHttpClient(RestTemplate restTemplate, TrafficTimer trafficTimer) {
        this.restTemplate = restTemplate;
        this.trafficTimer = trafficTimer;
    }

    public void sendPostRequest(String url, Object requestBody) {
        try {
            restTemplate.postForObject(url, requestBody, String.class);
            System.out.println("POST request sent to: " + url);
        } catch (ResourceAccessException e) {
            showErrorAlert("Connection Failed", "Could not reach the server. Check if the server is running.");
        } catch (HttpClientErrorException e) {
            showErrorAlert("Server Error", "HTTP Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            showErrorAlert("Unexpected Error", "Failed to send request: " + e.getMessage());
        }
    }

    public void sendPostRequest(String url) {
        sendPostRequest(url, null);
    }

    public void showErrorAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void onChangePedestrianButtonPressed() {
        sendPostRequest("http://localhost:8080/traffic/pedestrian");
    }

    public void onSettingsUpdate() {
        System.out.println("Settings to server, "+trafficTimer.getPedestrianGreenDuration()+", "+trafficTimer.getChangeDelay());
        sendPostRequest("http://localhost:8080/traffic/settings/pedestrianGreenDuration", trafficTimer.getPedestrianGreenDuration());
        sendPostRequest("http://localhost:8080/traffic/settings/changeDelay", trafficTimer.getChangeDelay());
    }
}