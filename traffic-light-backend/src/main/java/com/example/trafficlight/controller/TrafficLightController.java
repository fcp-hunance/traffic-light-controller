package com.example.trafficlight.controller;

import com.example.trafficlight.mqtt.MqttService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/traffic")
public class TrafficLightController {
    private final MqttService mqttService;

    public TrafficLightController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    // The HTTP request reaches the Spring controller, which publishes a message to the MQTT topic.
    @PostMapping("/pedestrian")
    public ResponseEntity<String> pedestrianButtonPressed() {
        mqttService.publish("traffic/pedestrian", "");
        return ResponseEntity.ok("Pedestrian button pressed");
    }

    // The HTTP request reaches the Spring controller, which receives a JSON payload as a Map, extracts the green light duration, and publishes it to the MQTT broker.
    @PostMapping("/settings/pedestrianGreenDuration")
    public ResponseEntity<String> setPedestrianDuration(@RequestBody java.util.Map<String, Integer> request) {
        int duration = request.get("pedestrianGreenDuration");
        mqttService.publish("traffic/settings/greenDuration", String.valueOf(duration));
        return ResponseEntity.ok("Pedestrian Green Duration set to " + duration);
    }

    // The HTTP request reaches the Spring controller, which receives a JSON payload as a Map, extracts the change of the delay, and publishes it to the MQTT broker.
    @PostMapping("/settings/changeDelay")
    public ResponseEntity<String> setChangeDelay(@RequestBody java.util.Map<String, Integer> request) {
        int delay = request.get("changeDelay");
        mqttService.publish("traffic/settings/changeDelay", String.valueOf(delay));
        return ResponseEntity.ok("Change Delay set to " + delay);
    }

    // A Ping for testing reasons.
    @GetMapping("/ping")
    public String ping() {
        return "Server is running!";
    }
}