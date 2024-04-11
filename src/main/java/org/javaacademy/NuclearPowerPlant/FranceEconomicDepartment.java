package org.javaacademy.NuclearPowerPlant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
@Profile("france")
public class FranceEconomicDepartment extends EconomicDepartment {
    @Value("${energy.price}")
    private BigDecimal energyPrice;
    @Value("${energy.discount.amountPercent}")
    private int discountPercent;
    @Value("${energy.discount.power_limit}")
    private long powerLimitForDiscount;

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {

        MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);

        long residualAmount = countElectricity;
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal currentPrice = energyPrice;
        BigDecimal discount = BigDecimal.valueOf(100 - discountPercent)
                .divide(BigDecimal.valueOf(100), mathContext);
        while (residualAmount > 0) {
            long amountLessLimit = Math.min(powerLimitForDiscount, residualAmount);
            totalPrice = totalPrice.add(BigDecimal.valueOf(amountLessLimit).multiply(currentPrice),
                    mathContext);
            currentPrice = (amountLessLimit == powerLimitForDiscount)
                    ? currentPrice.multiply(discount, mathContext) : currentPrice;
            residualAmount = residualAmount - amountLessLimit;
        }
        return totalPrice;
    }
}
