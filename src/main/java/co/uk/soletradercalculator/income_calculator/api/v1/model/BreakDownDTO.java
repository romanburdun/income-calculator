package co.uk.soletradercalculator.income_calculator.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BreakDownDTO {
    private double annual;
    private double monthly;
    private double weekly;
    private double daily;
}
