package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.api.v1.model.NationalInsuranceDTO;
import co.uk.soletradercalculator.income_calculator.domain.NationalInsurance;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NicMapperTest {

    private final NicMapper nicMapper = NicMapper.INSTANCE;

    @Test
    void nicDtoToNic() {
        NationalInsuranceDTO nationalInsuranceDTO = new NationalInsuranceDTO();
        nationalInsuranceDTO.setTaxYearStarts("06-04-2019");
        nationalInsuranceDTO.setClassTwo(3);
        nationalInsuranceDTO.setClassTwoThreshold(6365);
        nationalInsuranceDTO.setLowerProfitsLimitRate(9);
        nationalInsuranceDTO.setUpperProfitsLimitRate(2);
        nationalInsuranceDTO.setLowerProfitsLimit(8632);
        nationalInsuranceDTO.setUpperProfitsLimit(50000);

        NationalInsurance nationalInsurance = nicMapper.nicDtoToNic(nationalInsuranceDTO);

        assertNotNull(nationalInsurance);
        assertEquals(2019, nationalInsurance.getTaxYearStarts().getYear());
        assertEquals(6365, nationalInsurance.getClassTwoThreshold());
        assertEquals(2, nationalInsurance.getUpperProfitsLimitRate());
    }

    @Test
    void nicToNicDto() {

        NationalInsurance nationalInsurance = new NationalInsurance();
        nationalInsurance.setTaxYearStarts(LocalDate.of(2019,4,6));
        nationalInsurance.setClassTwo(3);
        nationalInsurance.setClassTwoThreshold(6365);
        nationalInsurance.setLowerProfitsLimitRate(9);
        nationalInsurance.setUpperProfitsLimitRate(2);
        nationalInsurance.setLowerProfitsLimit(8632);
        nationalInsurance.setUpperProfitsLimit(50000);

        NationalInsuranceDTO nationalInsuranceDTO = nicMapper.nicToNicDto(nationalInsurance);

        assertNotNull(nationalInsuranceDTO);
        assertEquals("06-04-2019", nationalInsuranceDTO.getTaxYearStarts());
        assertEquals(6365, nationalInsuranceDTO.getClassTwoThreshold());
        assertEquals(2, nationalInsuranceDTO.getUpperProfitsLimitRate());
    }
}
