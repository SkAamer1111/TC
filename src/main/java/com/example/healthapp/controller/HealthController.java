package com.example.healthapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {

        Map<String, String> response = new HashMap<>();

        response.put("status", "UP");
        response.put("message", "App is running");

        return response;
    }
}