package co.uk.soletradercalculator.income_calculator.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaxDTO {

    private String taxYearStarts;

    private double personalAllowance;

    private double basicThreshold;

    private double basicRate;

    private double higherThreshold;

    private double higherRate;

    private double additionalRate;
}
