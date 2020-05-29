package co.uk.soletradercalculator.income_calculator.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportDTO {
    private BreakDownDTO tax;
    private BreakDownDTO nicTwo;
    private BreakDownDTO nicFour;
    private BreakDownDTO studentLoan;
    private BreakDownDTO salary;
    private BreakDownDTO takingHome;



}
