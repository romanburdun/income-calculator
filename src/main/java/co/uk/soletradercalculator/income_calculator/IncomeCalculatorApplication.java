package co.uk.soletradercalculator.income_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class IncomeCalculatorApplication {



    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }



    public static void main(String[] args) {

        SpringApplication.run(IncomeCalculatorApplication.class, args);
    }




}
