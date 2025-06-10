package com.example.trafficlight.controller;

import com.example.trafficlight.mqtt.MqttService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/traffic/light")
public class TrafficLightController {
    private final MqttService mqttService;

    public TrafficLightController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    @PostMapping("/{color}")
    public ResponseEntity<String> changeLight(@PathVariable String color) {
        if (!color.matches("red|yellow|green")) {
            return ResponseEntity.badRequest().body("Invalid color. Use red, yellow or green.");
        }

        mqttService.publish("traffic/light", color.toLowerCase());
        return ResponseEntity.ok("Traffic light set to " + color);
    }

    @GetMapping("/ping")
    public String ping() {
        return "Server is running!";
    }
}