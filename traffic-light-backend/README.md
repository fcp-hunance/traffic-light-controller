# TrafficLightController (Backend)

## Project Description

This is a simple Spring Boot backend application that provides an HTTP interface to control a traffic light. The controller receives HTTP requests and forwards commands to a microcontroller, which is responsible for switching the light state.

## API Endpoints

### 1. Direct Color Switching (Current Implementation)

POST /traffic/light/{color}

- `{color}` can be: `red`, `yellow`, or `green`
- Example:  
  `http://localhost:8080/traffic/light/red`

⚠️ **Note**: This endpoint implies that the server determines the target color. However, in the current architecture, the backend only forwards a signal. The actual logic for changing the light is implemented in the microcontroller.



### 2. Event-Based Switching (Recommended Alternative)

POST /traffic/light/change
or
POST /traffic/light/buttonPressed

- This approach simply signals that a state change should occur.
- The microcontroller decides which color comes next based on its internal logic (e.g. traffic light sequence).

## Discussion

The current implementation directly references a color in the URL, which may not align with the actual architecture. Since the backend does not control the target color but merely sends a trigger to the microcontroller, an event-based endpoint may be more appropriate.

This topic is open for discussion and will be addressed in the next team meeting.

## To Do

- [ ] Decide on the final endpoint structure
- [ ] Update controller implementation if needed
- [ ] Adjust API documentation accordingly

# MQTT Server

## Overview

This component connects to a local MQTT broker and publishes or subscribes to messages related to traffic light control.

- **Broker URL:** `tcp://localhost:1883`
- **Topic:** `traffic/light`
- **Payloads:** `red`, `yellow`, `green`

## Topic Details

Messages sent to the topic `traffic/light` are expected to contain a single string payload representing the desired traffic light color:

```plaintext
Payload: "red" | "yellow" | "green"
