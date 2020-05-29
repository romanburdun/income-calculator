package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.api.v1.model.NationalInsuranceDTO;
import co.uk.soletradercalculator.income_calculator.domain.NationalInsurance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NicMapper {

    NicMapper INSTANCE = Mappers.getMapper(NicMapper.class);


    @Mapping(source = "taxYearStarts", target = "taxYearStarts", dateFormat = ("dd-MM-yyyy"))
    NationalInsurance nicDtoToNic(NationalInsuranceDTO nationalInsuranceDTO);
    @Mapping(source = "taxYearStarts", target = "taxYearStarts", dateFormat = ("dd-MM-yyyy"))
    NationalInsuranceDTO nicToNicDto(NationalInsurance nationalInsurance);
}
