package config;

import controller.TrafficLightHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import model.TrafficLight;
import model.PedestrianLight;
import model.TrafficTimer;
import controller.TrafficController;
import org.springframework.web.client.RestTemplate;
import view.MainFrame;

@Configuration
@ComponentScan(basePackages = {"view", "controller", "model"})
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TrafficLight trafficLight() {
        return new TrafficLight();
    }

    @Bean
    public PedestrianLight pedestrianLight() {
        return new PedestrianLight();
    }

    @Bean
    public TrafficTimer trafficTimer() {
        return new TrafficTimer( 10, 5); // Default values: carGreen=30s, pedestrianGreen=10s, delay=5s
    }

    @Bean
    public TrafficController trafficController(TrafficLightHttpClient httpClient) {
        return new TrafficController(trafficLight(), pedestrianLight(), trafficTimer(), httpClient);
    }
}