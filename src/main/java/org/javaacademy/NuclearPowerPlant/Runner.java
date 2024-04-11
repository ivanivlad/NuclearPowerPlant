package org.javaacademy.NuclearPowerPlant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Runner {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Runner.class, args);
        NuclearStation nuclearStation = context.getBean(NuclearStation.class);
        nuclearStation.start(3);
    }
}
