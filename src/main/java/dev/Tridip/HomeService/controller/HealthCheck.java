package dev.Tridip.HomeService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheck {
    @GetMapping("/health")
    public String checkHealth(){
        return "<h1>I am Healthy Bro</h1>";
    }
}
