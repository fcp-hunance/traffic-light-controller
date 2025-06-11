#include <WiFi.h>
#include <PubSubClient.h>

// WiFi and MQTT Configuration
const char* ssid = "Vodafone-2BDB";
const char* password = "g8GMdACuFb3NngHd";
const char* mqtt_server = "192.168.0.112";
const int mqtt_port = 1883;

// MQTT Topics
const char* topic_pedestrian_trigger = "traffic/pedestrian";
const char* topic_settings_green_duration = "traffic/settings/greenDuration";
const char* topic_settings_change_delay = "traffic/settings/changeDelay";

// LED Pins (ESP32-WROOM)
const int trafficRed = 18;     // GPIO18
const int trafficYellow = 19;  // GPIO19
const int trafficGreen = 21;   // GPIO21
const int pedRed = 22;         // GPIO22
const int pedGreen = 23;       // GPIO23
const int blueLight = 5;       // GPIO05

// Timing Variables
int pedestrianGreenDuration = 10;  // Default: 10 seconds
int changeDelay = 3;              // Default: 3 seconds delay
const int blueBlinkInterval = 500

WiFiClient espClient;
PubSubClient client(espClient);

void setup() {
  Serial.begin(115200);
  
  // Configure LED pins
  pinMode(trafficRed, OUTPUT);
  pinMode(trafficYellow, OUTPUT);
  pinMode(trafficGreen, OUTPUT);
  pinMode(pedRed, OUTPUT);
  pinMode(pedGreen, OUTPUT);
  pinMode(blueLight, OUTPUT);
  
  // Initialize with traffic green, pedestrian red
  setTrafficLight(false, false, true);
  setPedestrianLight(true, false);
  
  setupWiFi();
  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(callback);
}

void loop() {
  if (!client.connected()) {
    reconnectMQTT();
  }
  client.loop();
}

// Blinking Blue Light Function
void blinkBlueLight(bool &keepBlinking) {
  static unsigned long previousMillis = 0;
  static bool ledState = LOW;
  
  unsigned long currentMillis = millis();
  
  if (currentMillis - previousMillis >= blueBlinkInterval) {
    previousMillis = currentMillis;
    ledState = !ledState;
    digitalWrite(blueLight, ledState);
  }
  
  if (!keepBlinking) {
    digitalWrite(blueLight, LOW);
  }
}

// LED Control Functions
void setTrafficLight(bool red, bool yellow, bool green) {
  digitalWrite(trafficRed, red ? HIGH : LOW);
  digitalWrite(trafficYellow, yellow ? HIGH : LOW);
  digitalWrite(trafficGreen, green ? HIGH : LOW);
}

void setPedestrianLight(bool red, bool green) {
  digitalWrite(pedRed, red ? HIGH : LOW);
  digitalWrite(pedGreen, green ? HIGH : LOW);
}

// WiFi Connection
void setupWiFi() {
  delay(10);
  Serial.println("\nConnecting to: " + String(ssid));
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nWiFi Connected! IP: " + WiFi.localIP().toString());
}

// MQTT Connection
void reconnectMQTT() {
  while (!client.connected()) {
    Serial.print("Connecting to MQTT...");
    if (client.connect("ESP32-TrafficLight")) {
      Serial.println("Connected!");
      
      // Subscribe to topics
      client.subscribe(topic_pedestrian_trigger);
      client.subscribe(topic_settings_green_duration);
      client.subscribe(topic_settings_change_delay);
    } else {
      Serial.println("Failed. Retrying in 5s...");
      delay(5000);
    }
  }
}

// MQTT Message Handler
void callback(char* topic, byte* payload, unsigned int length) {
  String message;
  for (int i = 0; i < length; i++) {
    message += (char)payload[i];
  }

  Serial.println("MQTT Message: [" + String(topic) + "] " + message);

  if (strcmp(topic, topic_settings_green_duration) == 0) {
    pedestrianGreenDuration = message.toInt();
    Serial.println("Green Duration: " + String(pedestrianGreenDuration) + "s");
  } 
  else if (strcmp(topic, topic_settings_change_delay) == 0) {
    changeDelay = message.toInt();
    Serial.println("Change Delay: " + String(changeDelay) + "s");
  }
  else if (strcmp(topic, topic_pedestrian_trigger) == 0) {
    pedestrianSequence();
  }
}

// Pedestrian Crossing Sequence
void pedestrianSequence() {
  Serial.println("Starting pedestrian sequence...");
  bool shouldBlink = true;
  
  // Initial delay with blinking blue light
  unsigned long startTime = millis();
  while (millis() - startTime < changeDelay * 1000) {
    blinkBlueLight(shouldBlink);
    delay(10); // Small delay to prevent watchdog trigger
  }
  
  // Yellow light with blinking blue light
  setTrafficLight(false, true, false);
  startTime = millis();
  while (millis() - startTime < 5000) {
    blinkBlueLight(shouldBlink);
    delay(10);
  }
  
  // Stop blinking and turn off blue light
  shouldBlink = false;
  digitalWrite(blueLight, LOW);
  
  // Red traffic light, green pedestrian
  setTrafficLight(true, false, false);
  setPedestrianLight(false, true);
  delay(pedestrianGreenDuration * 1000);
  
  // Return to normal
  setTrafficLight(false, false, true);
  setPedestrianLight(true, false);
  
  Serial.println("Pedestrian sequence completed");
}