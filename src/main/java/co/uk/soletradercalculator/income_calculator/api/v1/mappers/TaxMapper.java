package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxDTO;
import co.uk.soletradercalculator.income_calculator.domain.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaxMapper {
    TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

    @Mapping(source = "taxYearStarts", target = "taxYearStarts", dateFormat = ("dd-MM-yyyy"))
    Tax taxDtoToTax(TaxDTO taxDTO);
    @Mapping(source = "taxYearStarts", target = "taxYearStarts", dateFormat = ("dd-MM-yyyy"))
    TaxDTO taxToTaxDto(Tax tax);

}
