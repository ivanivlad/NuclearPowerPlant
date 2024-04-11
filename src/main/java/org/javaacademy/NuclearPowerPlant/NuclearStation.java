package org.javaacademy.NuclearPowerPlant;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NuclearStation {
    private static final String START_MESSAGE = "Атомная станция начала работу";
    private static final  String COUNTRY_MESSAGE = "Действие происходит в стране: %s";
    private static final String FINAL_MESSAGE = "Атомная станция закончила работу. За год Выработано %s киловатт/часов";
    private static final String NOT_WORK_MESSAGE = "Внимание! Происходят работы на атомной станции! Электричества нет!";
    private static final String TOTAL_ACCIDENT_MESSAGE = "Количество инцидентов за всю работу станции: %s";
    private static final String YEAR_ACCIDENT_MESSAGE = "Количество инцидентов за год: %s";
    private static final String INCOME_MESSAGE = "Доход за год составил %s %s";
    @NonNull
    private final ReactorDepartment reactorDepartment;
    @NonNull
    private final EconomicDepartment economicDepartment;
    @Lazy
    @Autowired
    private SecurityDepartment securityDepartment;
    private long amountOfPowerTotal;
    private int accidentCountAllTime;
    @Value("${country.currency_name}")
    private String currencyName;
    @Value("${country.name}")
    private String countryName;

    private void startYear() {
        System.out.println(START_MESSAGE);
        for (int i = 1; i <= 365; i++) {
            try {
                amountOfPowerTotal += reactorDepartment.run();
                reactorDepartment.stop();
            } catch (ReactorWorkException | NuclearFuelIsEmptyException e) {
                System.out.println(NOT_WORK_MESSAGE);
            }
        }
        System.out.println(String.format(FINAL_MESSAGE,
                amountOfPowerTotal));
        System.out.println(String.format(YEAR_ACCIDENT_MESSAGE,
                securityDepartment.getCountAccidents()));
        System.out.println(String.format(INCOME_MESSAGE,
                economicDepartment.computeYearIncomes(amountOfPowerTotal),
                currencyName));
    }

    public void start(int year) {
        if (year <= 0) {
            throw new RuntimeException("Количество лет не может быть меньше 1");
        }
        System.out.println(String.format(COUNTRY_MESSAGE, countryName));
        for (int i = 1; i <= year; i++) {
            amountOfPowerTotal = 0;
            startYear();
            securityDepartment.reset();
        }
        System.out.println(String.format(TOTAL_ACCIDENT_MESSAGE, accidentCountAllTime));
    }

    public void incrementAccident(int count) {
        if (count < 0) {
            throw new RuntimeException("Количество новых инцидентов не может быть меньше 0");
        }
        accidentCountAllTime += count;
    }
}
