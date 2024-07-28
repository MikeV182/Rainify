package com.mikeV182.Rainify.controllers;

import com.mikeV182.Rainify.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/")
    public String homePage() {
        return "homePage";
    }

    @GetMapping("/forecast")
    public String forecastPage(@RequestParam("city") String city, Model model) {
        HashMap<String, String> latLon = weatherService.getGeocodeByCity(city);
        if (latLon == null) {
            return "cityNotFound";
        }
        String latitude = "", longitude = "";
        for (Map.Entry<String, String> entry : latLon.entrySet()) {
            latitude = entry.getKey();
            longitude = entry.getValue();
        }
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        return "forecast";
    }

}
