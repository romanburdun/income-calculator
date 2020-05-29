package co.uk.soletradercalculator.income_calculator.api.v1.model;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class NationalInsuranceDTO {

    private String taxYearStarts;

    private double classTwo;

    private double classTwoThreshold;

    private double lowerProfitsLimit;

    private double lowerProfitsLimitRate;

    private double upperProfitsLimit;

    private double upperProfitsLimitRate;
}
