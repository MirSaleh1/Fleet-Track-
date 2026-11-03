package com.example.fleettrack.controller;

import com.example.fleettrack.model.LocationUpdateRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LocationController {

    @MessageMapping("/location")
    @SendTo("/topic/locations")
    public LocationUpdateRequest sendLocation(LocationUpdateRequest location) {
        return location;
    }
}
