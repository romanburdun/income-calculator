package co.uk.soletradercalculator.income_calculator.api.v1.model;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StudentLoanDTO {

    private String taxYearStarts;

    private double planOneAnnualThreshold;

    private double planOneRate;

    private double planTwoAnnualThreshold;

    private double planTwoRate;
}
