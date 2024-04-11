package org.javaacademy.NuclearPowerPlant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityDepartment {

    private final NuclearStation nuclearStation;
    private int accidentCountPeriod;

    public void addAccident() {
        accidentCountPeriod++;
    }

    public int getCountAccidents() {
        return accidentCountPeriod;
    }

    public void reset() {
        nuclearStation.incrementAccident(accidentCountPeriod);
        accidentCountPeriod = 0;
    }
}
