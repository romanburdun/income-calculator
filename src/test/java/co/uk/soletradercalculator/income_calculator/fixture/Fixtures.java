package co.uk.soletradercalculator.income_calculator.fixture;

import co.uk.soletradercalculator.income_calculator.api.v1.model.*;
import co.uk.soletradercalculator.income_calculator.domain.NationalInsurance;
import co.uk.soletradercalculator.income_calculator.domain.StudentLoan;
import co.uk.soletradercalculator.income_calculator.domain.Tax;
import co.uk.soletradercalculator.income_calculator.domain.TaxYear;

import java.time.LocalDate;

public abstract class Fixtures {

    public static TaxYearDTO getTaxYearDTO() {
        TaxDTO tax = new TaxDTO();
        tax.setPersonalAllowance(12500);
        tax.setBasicThreshold(37500);
        tax.setBasicRate(20);
        tax.setHigherThreshold(150000);
        tax.setHigherRate(40);
        tax.setAdditionalRate(45);

        NationalInsuranceDTO nationalInsurance = new NationalInsuranceDTO();
        nationalInsurance.setClassTwo(3);
        nationalInsurance.setLowerProfitsLimit(8632);
        nationalInsurance.setLowerProfitsLimitRate(9);
        nationalInsurance.setUpperProfitsLimit(50000);
        nationalInsurance.setUpperProfitsLimitRate(2);

        StudentLoanDTO studentLoan = new StudentLoanDTO();
        studentLoan.setPlanOneAnnualThreshold(25750);
        studentLoan.setPlanOneRate(9);
        studentLoan.setPlanTwoAnnualThreshold(18935);
        studentLoan.setPlanTwoRate(9);


        TaxYearDTO taxYearDTO = new TaxYearDTO();
        taxYearDTO.setTaxYearStarts("06-04-2019");
        taxYearDTO.setTax(tax);
        taxYearDTO.setNationalInsurance(nationalInsurance);
        taxYearDTO.setStudentLoan(studentLoan);

        return taxYearDTO;
    }

    public static Tax getTax() {
        Tax tax = new Tax();
        tax.setId(1L);
        tax.setPersonalAllowance(12500);
        tax.setBasicThreshold(37500);
        tax.setBasicRate(20);
        tax.setHigherThreshold(150000);
        tax.setHigherRate(40);
        tax.setAdditionalRate(45);
        return tax;
    }

    public static NationalInsurance getNationalInsurance() {
        NationalInsurance nationalInsurance = new NationalInsurance();
        nationalInsurance.setId(1L);
        nationalInsurance.setClassTwo(3);
        nationalInsurance.setLowerProfitsLimit(8632);
        nationalInsurance.setLowerProfitsLimitRate(9);
        nationalInsurance.setUpperProfitsLimit(50000);
        nationalInsurance.setUpperProfitsLimitRate(2);

        return nationalInsurance;
    }

    public static StudentLoan getStudentLoan() {
        StudentLoan studentLoan = new StudentLoan();
        studentLoan.setId(1L);
        studentLoan.setPlanOneAnnualThreshold(25750);
        studentLoan.setPlanOneRate(9);
        studentLoan.setPlanTwoAnnualThreshold(18935);
        studentLoan.setPlanTwoRate(9);
        return studentLoan;
    }

    public static TaxYear getTaxYear() {
        TaxYear taxYear = new TaxYear();
        taxYear.setId(1L);
        taxYear.setTaxYearStarts(LocalDate.of(2019, 4,6));
        taxYear.setTax(Fixtures.getTax());
        taxYear.setNationalInsurance(Fixtures.getNationalInsurance());
        taxYear.setStudentLoan(Fixtures.getStudentLoan());

        return taxYear;
    }

    public static ReportDTO getReport() {

        ReportDTO report = new ReportDTO();

        double salary = 29000.0;
        BreakDownDTO salaryDTO = new BreakDownDTO();
        salaryDTO.setAnnual(salary);
        salaryDTO.setMonthly(salary/12);
        salaryDTO.setWeekly(salary/52);
        salaryDTO.setDaily(salary/52/5);

        report.setSalary(salaryDTO);
        return report;
    }
}
