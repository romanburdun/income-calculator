package co.uk.soletradercalculator.income_calculator.api.v1.model;


import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class TaxYearDTO {

    private String taxYearStarts;
    private TaxDTO tax;
    private NationalInsuranceDTO nationalInsurance;
    private StudentLoanDTO studentLoan;
}
