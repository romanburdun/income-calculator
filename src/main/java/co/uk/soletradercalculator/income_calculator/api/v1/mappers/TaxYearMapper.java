package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxYearDTO;
import co.uk.soletradercalculator.income_calculator.domain.TaxYear;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaxYearMapper {

    TaxYearMapper INSTANCE = Mappers.getMapper(TaxYearMapper.class);


    @Mapping(source = "taxYearStarts", target = "taxYearStarts", dateFormat = ("dd-MM-yyyy"))
    TaxYear taxYearDtoToTaxYear(TaxYearDTO taxYearDTO);
    @Mapping(source = "taxYearStarts", target = "taxYearStarts", dateFormat = ("dd-MM-yyyy"))
    TaxYearDTO taxYearToTaxYearDto(TaxYear taxYear);

}
