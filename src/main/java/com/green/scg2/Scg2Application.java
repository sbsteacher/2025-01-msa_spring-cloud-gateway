package com.green.scg2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableWebFlux
public class Scg2Application {

    public static void main(String[] args) {
        SpringApplication.run(Scg2Application.class, args);
    }

}
