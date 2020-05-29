package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxDTO;
import co.uk.soletradercalculator.income_calculator.domain.Tax;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaxMapperTest {

    private final TaxMapper  taxMapper= TaxMapper.INSTANCE;

    @Test
    void taxDtoToTax() {

        TaxDTO taxDTO = new TaxDTO();

        taxDTO.setTaxYearStarts("06-04-2019");
        taxDTO.setBasicRate(20);
        taxDTO.setHigherRate(40);
        taxDTO.setAdditionalRate(45);
        taxDTO.setBasicThreshold(37500);
        taxDTO.setHigherThreshold(150000);
        taxDTO.setPersonalAllowance(12500);

        Tax tax = taxMapper.taxDtoToTax(taxDTO);

        assertNotNull(tax);
        assertEquals(2019, tax.getTaxYearStarts().getYear());
        assertEquals(37500, tax.getBasicThreshold());
    }

    @Test
    void taxToTaxDto() {

        Tax tax = new Tax();

        tax.setTaxYearStarts(LocalDate.of(2019,4, 6));
        tax.setBasicRate(20);
        tax.setHigherRate(40);
        tax.setAdditionalRate(45);
        tax.setBasicThreshold(37500);
        tax.setHigherThreshold(150000);
        tax.setPersonalAllowance(12500);


        TaxDTO taxDTO = taxMapper.taxToTaxDto(tax);

        assertNotNull(taxDTO);
        assertEquals("06-04-2019", taxDTO.getTaxYearStarts());
        assertEquals(37500,taxDTO.getBasicThreshold());
    }
}
