package com.mikeV182.Rainify;

import com.mikeV182.Rainify.services.WeatherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(WeatherService.class)
public class RainifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RainifyApplication.class, args);
	}

}
