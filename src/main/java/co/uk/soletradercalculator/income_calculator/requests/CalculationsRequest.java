package co.uk.soletradercalculator.income_calculator.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CalculationsRequest {
    private double income;
    private double expenses;
    private int studentLoan;
    private Boolean isEmployed;
    private double employedIncome;

}
