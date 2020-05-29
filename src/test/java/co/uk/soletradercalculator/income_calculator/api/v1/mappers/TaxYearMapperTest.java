package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.domain.NationalInsurance;
import co.uk.soletradercalculator.income_calculator.domain.StudentLoan;
import co.uk.soletradercalculator.income_calculator.domain.Tax;
import co.uk.soletradercalculator.income_calculator.domain.TaxYear;
import co.uk.soletradercalculator.income_calculator.api.v1.model.NationalInsuranceDTO;
import co.uk.soletradercalculator.income_calculator.api.v1.model.StudentLoanDTO;
import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxDTO;
import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxYearDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TaxYearMapperTest {

    private final TaxYearMapper taxYearMapper= TaxYearMapper.INSTANCE;



    @Test
    void taxYearDtoToTaxYear() {
        TaxDTO tax = new TaxDTO();
        NationalInsuranceDTO nationalInsurance = new NationalInsuranceDTO();
        StudentLoanDTO studentLoan = new StudentLoanDTO();
        TaxYearDTO taxYearDTO = new TaxYearDTO();
        taxYearDTO.setTaxYearStarts("05-04-2019");
        taxYearDTO.setTax(tax);
        taxYearDTO.setNationalInsurance(nationalInsurance);
        taxYearDTO.setStudentLoan(studentLoan);

        TaxYear testTaxYear = taxYearMapper.taxYearDtoToTaxYear(taxYearDTO);

        Assertions.assertNotNull(testTaxYear.getTaxYearStarts().toString());
        Assertions.assertEquals("2019-04-05", testTaxYear.getTaxYearStarts().toString());


    }

    @Test
    void taxYearToTaxYearDto() {

        Tax tax = new Tax();
        NationalInsurance nationalInsurance = new NationalInsurance();
        StudentLoan studentLoan = new StudentLoan();
        TaxYear taxYear = new TaxYear();
        taxYear.setTax(tax);
        taxYear.setNationalInsurance(nationalInsurance);
        taxYear.setTaxYearStarts(LocalDate.of(2020, 4, 5));

        TaxYearDTO testTaxYearDTO = taxYearMapper.taxYearToTaxYearDto(taxYear);

        Assertions.assertNotNull(testTaxYearDTO);
        Assertions.assertNotNull(testTaxYearDTO.getTaxYearStarts());
        Assertions.assertEquals("05-04-2020", testTaxYearDTO.getTaxYearStarts());


    }
}
