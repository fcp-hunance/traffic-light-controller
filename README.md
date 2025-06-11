# 🚦 Traffic Light Controller – Desktop & ESP32 Integration

A Java desktop application built with Spring Core to control traffic light logic using an ESP32 microcontroller board via a modern Spring Boot backend and MQTT communication.

---

## 🛠 Project Structure

The project consists of two main modules:

- **traffic-light-server**: A Spring Boot REST backend with an integrated MQTT client to communicate with the ESP32.
- **traffic-light-client**: A Spring Core JavaFX desktop application with a graphical representation of a traffic light that sends REST requests to the backend.

---

## 🔧 Technologies Used

- **Java 21**
- **Maven**
- **Spring Boot** (REST + MQTT using Eclipse Paho)
- **Spring Core**
- **JavaFX** (GUI)
- **ESP32** (Microcontroller with LED + Buzzer)
- **Mosquitto** (MQTT-Broker)

---

## 📦 Features

- Graphical control of the traffic light (RED, YELLOW, GREEN) with sequence settings.
- REST interface between the JavaFX application and the Spring Boot backend.
- MQTT communication from the backend to the ESP32.
- Real-time visualization of the current traffic light status in the GUI.
- Extendable for acoustic signals (buzzer) or traffic detection features.

---

## 🔄 Communication Flow

[JavaFX GUI] ⇄ REST ⇄ [Spring Boot Backend] ⇄ MQTT ⇄ [ESP32 mit LED/Buzzer]

---

## 🚧 Next Steps / Goals

- ✅ First GUI prototypes with graphical traffic light display.
- ⏳ REST endpoints for controlling the traffic light (POST /traffic/light/{color}).
- ⏳ Configure MQTT connection to Mosquitto broker.
- ⏳ Develop ESP32 firmware to process MQTT commands.
- ✅ Display error handling and system feedback in the GUI.

---

## 👨‍💻 Authors

- Fernando Conde Pereira
- Marten Ali Aboul Kataa

---

## 📄 License

This project is licensed under the MIT License.