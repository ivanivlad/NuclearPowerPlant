package org.javaacademy.NuclearPowerPlant;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReactorDepartment {
    private boolean isWork;
    private long countOfRun;
    @Lazy
    @Autowired
    private SecurityDepartment securityDepartment;

    public int run() {
        countOfRun++;
        if (isWork) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже включен");
        }
        if (countOfRun % 100 == 0) {
            securityDepartment.addAccident();
            throw new NuclearFuelIsEmptyException();
        }
        isWork = true;

        return 10_000_000;
    }

    public void stop() {
        if (!isWork) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже выключен");
        }
        isWork = false;
    }

}
