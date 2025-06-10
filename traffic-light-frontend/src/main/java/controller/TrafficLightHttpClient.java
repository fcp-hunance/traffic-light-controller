package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class TrafficLightHttpClient {

    private final RestTemplate restTemplate;

    public TrafficLightHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendPostRequest(String url) {
        try {
            restTemplate.postForObject(url, null, String.class);
            System.out.println("POST request sent to: " + url);
        } catch (ResourceAccessException e) {
            showErrorAlert("Connection Failed", "Could not reach the server. Check if the server is running.");
        } catch (HttpClientErrorException e) {
            showErrorAlert("Server Error", "HTTP Error: " + e.getStatusCode() + " - " + e.getStatusText());
        } catch (Exception e) {
            showErrorAlert("Unexpected Error", "Failed to send request: " + e.getMessage());
        }
    }

    private void showErrorAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void onChangeToPedestrianGreen() {
        sendPostRequest("http://localhost:8080/traffic/light/red");
    }

    public void onReturnToCarGreen() {
        sendPostRequest("http://localhost:8080/traffic/light/green");
    }
}