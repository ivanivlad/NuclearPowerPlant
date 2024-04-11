package org.javaacademy.NuclearPowerPlant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
@Profile("morocco")
public class MoroccoEconomicDepartment extends EconomicDepartment {
    @Value("${energy.base_tariff.power_limit}")
    private long powerLimitBaseTariff;
    @Value("${energy.base_tariff.price}")
    private BigDecimal priceBaseTariff;
    @Value("${energy.upper_tariff.price}")
    private BigDecimal priceUpperTariff;

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {

        MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);

        long residualAmount = countElectricity;
        BigDecimal totalPrice = BigDecimal.ZERO;

        long amountLessLimit = Math.min(powerLimitBaseTariff, residualAmount);
        totalPrice = totalPrice.add(BigDecimal.valueOf(amountLessLimit).multiply(priceBaseTariff),
                mathContext);
        residualAmount = residualAmount - amountLessLimit;

        if (residualAmount > 0) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(residualAmount).multiply(priceUpperTariff),
                    mathContext);
        }

        return totalPrice;
    }
}
