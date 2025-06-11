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


    @PostMapping("/pedestrian")
    public ResponseEntity<String> pedestrianButtonPressed() {
        mqttService.publish("traffic/pedestrian", "");
        return ResponseEntity.ok("Pedestrian button pressed");
    }




// Klasse und DurationÜbergabe an MQTT


                public static class PedestrianGreenDurationRequest {
                    private int pedestrianGreen;

                    public int getPedestrianGreen() {
                        return pedestrianGreen;
                    }

                    public void setPedestrianGreen(int pedestrianGreen) {
                        this.pedestrianGreen = pedestrianGreen;
                    }
                }

                @PostMapping ("/settings/pedestrianGreenDuration")
                public ResponseEntity<String> pedestrianGreenDuration(@RequestBody PedestrianGreenDurationRequest request){
                    int duration = request.getPedestrianGreen();
                    mqttService.publish("traffic//settings/pedestrianGreenDuration",String.valueOf(duration));
                    return ResponseEntity.ok("Delay Changed to " + String.valueOf(duration));
                }





// Klasse und Delayübergabe an MQTT

                public static class ChangeDelayRequest {
                    private int delay;

                    public int getChangeDelay() {
                        return delay;
                    }

                    public void setChangeDelay(int delayRequest) {
                        this.delay = delayRequest;
                    }
                }


                @PostMapping ("/settings/changeDelay")
                public ResponseEntity<String> changeDelay(@RequestBody ChangeDelayRequest request){
                    int delay = request.getChangeDelay();
                    mqttService.publish("traffic/settings/changeDelay",String.valueOf(delay));
                    return ResponseEntity.ok("Delay Changed to " + String.valueOf(delay));
                }




    @GetMapping("/ping")
    public String ping() {
        return "Server is running!";
    }
}