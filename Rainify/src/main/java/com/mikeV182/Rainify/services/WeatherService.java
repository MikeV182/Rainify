package com.mikeV182.Rainify.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Setter
@Service
@ConfigurationProperties(prefix = "apikey")
@ConfigurationPropertiesScan
public class WeatherService {
    private String geo;
    private String openweather;

    public HashMap<String, String> getGeocodeByCity(String city) {
        String geocode = "https://geocode.maps.co/search?q=%s&api_key=%s";
        try {
            URL url = new URL(String.format(geocode, city, geo));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            Reader streamReader = null;
            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }
            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            HashMap<String, String> latLon = new HashMap<>();
            JsonArray data = new Gson().fromJson(content.toString(), JsonArray.class);
            for (JsonElement element : data) {
                JsonObject object = element.getAsJsonObject();
                if (object.get("class").getAsString().equals("boundary") &&
                        object.get("type").getAsString().equals("administrative")) {
                    latLon.put(object.get("lat").getAsString(), object.get("lon").getAsString());
                    return latLon;
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO: complete this function
    public String getWeatherByCity(String city) {
        String weather = "https://api.openweathermap.org/data/3.0/onecall?lat=%s&lon=%s&appid=%s";
        return "";
    }

}
