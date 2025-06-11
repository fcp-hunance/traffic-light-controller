package com.example.trafficlight.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service
public class MqttService {
    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Value("${mqtt.broker.url:tcp://192.168.0.112:1883}")
    private String brokerUrl;

    private MqttClient client;

    @Bean
    public void init() {
        // Initialisierungscode
    }

    public void publish(String topic, String payload) {
        if (client == null || !client.isConnected()) {
            logger.warn("MQTT client not connected. Message not sent.");
            return;
        }

        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1);
            client.publish(topic, message);
            logger.debug("Published to {}: {}", topic, payload);
        } catch (MqttException e) {
            logger.error("Failed to publish MQTT message", e);
        }
    }

    @Bean(destroyMethod = "cleanup")
    public void cleanup() {
        // Aufräumcode
    }
}