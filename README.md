# 🚦 Traffic Light Controller – Desktop & ESP32 Integration

A Java desktop application built with Spring Core to control traffic light logic using an ESP32 microcontroller board via a modern Spring Boot backend and MQTT communication.

---

## 🛠 Project Structure

The project consists of two main modules:

- **traffic-light-server**: A Spring Boot REST backend with an integrated MQTT client to communicate with the ESP32.
- **traffic-light-client**: A Spring Core JavaFX desktop application with a graphical representation of a traffic light that sends REST requests to the backend.

---

## 🔧 Technologies Used

- **Java 21 (Liberica-Full)**
- **Maven**
- **Spring Boot** (REST + MQTT using Eclipse Paho)
- **Spring Core**
- **JavaFX** (GUI)
- **ESP32** (Microcontroller with LED + Buzzer)
- **Mosquitto** (MQTT-Broker)

---

## 📦 Features

- Graphical control and real-time visualization of a traffic light and pedestrian light.
- REST interface between JavaFX application Spring Boot backend.
- MQTT communication with the ESP32 for executing commands.
- Customizable settings: pedestrian green light duration & delay before state change.
- Extendable for acoustic signals (buzzer) or traffic detection sensors. 
- Error handling and feedback system in the GUI.
---

## 🔄 Communication Flow

[JavaFX GUI] ⇄ REST ⇄ [Spring Boot Backend] ⇄ MQTT ⇄ [ESP32 mit LED/Buzzer]

## 📡 API & Communication Details

### 🔧 Settings Endpoint (POST /traffic/settings)
Sends initial and updated configuration from GUI to backend.   
Request Example:
```json
{
    "pedestrianGreenDuration": 10
}
```
```json
{
  "changeDelay": 5
}
```
### 🚶 Pedestrian Request (POST /traffic/pedestrian)
Triggered each time the pedestrian button is pressed in the GUI.

🔁 MQTT Topics
| Topic                            | Description                        | Payload Example |
| -------------------------------- | ---------------------------------- | --------------- |
| `traffic/pedestrian`             | Pedestrian button pressed          | *(empty)*       |
| `traffic/settings/greenDuration` | Duration of pedestrian green light | `"15"`          |
| `traffic/settings/changeDelay`   | Delay before switching lights      | `"5"`           |


#### Command-line MQTT Publish Example:
```bash
  mosquitto_pub -h <broker_address> -t "traffic/settings/greenDuration" -m "15"
  mosquitto_pub -h <broker_address> -t "traffic/settings/changeDelay" -m "5"
```
---

## 🚀 Getting Started
### ✅ Prerequisites
- Java 21 (Liberica Full JDK)
- Maven
- Mosquitto MQTT Broker (Local or remote)
- ESP32 flashed with compatible firmware
- Serial/USB connection for ESP32

### 🛠 Setup Instructions
#### Clone the repository
```bash
    git clone https://github.com/fcp-hunance/traffic-light-controller.git
    cd traffic-light-controller 
```
#### Start the MQTT broker (e.g., Mosquitto)
```bash
    systemctl start mosquitto
```
#### Start the backend
```bash
    cd traffic-light-backend
    mvn spring-boot:run
```
#### Run the desktop GUI
```bash
cd ../traffic-light-client
mvn javafx:run
```
#### Flash and start ESP32
Ensure your ESP32 is subscribed to the appropriate MQTT topics.
LEDs and buzzer should react according to messages.

## 🚧 Next Steps / Goals

- ✅ First GUI prototypes with graphical traffic light display.
- ⏳ REST endpoints for controlling the traffic light and settings (POST /traffic/).
- ⏳ Configure MQTT connection to Mosquitto broker.
- ✅ Develop ESP32 firmware to process MQTT commands.
- ✅ Display error handling and system feedback in the GUI.
- 🔜 Advanced traffic logic (e.g., automatic cycles, traffic sensors).
- 🔜 Web-based dashboard and mobile interface.

---
## 🧪 Testing
Use tools like MQTT Explorer to monitor messages.   
Validate backend endpoints with Postman.  
ESP32 serial monitor (via Arduino IDE or PlatformIO) to debug MQTT reception.

## 👨‍💻 Authors

- Fernando Conde Pereira
- Marten Ali Aboul Kataa

---

## 📄 License

This project is licensed under the MIT License.