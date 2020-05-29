package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.api.v1.model.StudentLoanDTO;
import co.uk.soletradercalculator.income_calculator.domain.StudentLoan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentLoanMapper {

    StudentLoanMapper INSTANCE = Mappers.getMapper(StudentLoanMapper.class);

    @Mapping(source = "taxYearStarts", target = "taxYearStarts", dateFormat = ("dd-MM-yyyy"))
    StudentLoan studentLoanDtoToStudentLoan(StudentLoanDTO studentLoanDTO);
    @Mapping(source = "taxYearStarts", target = "taxYearStarts", dateFormat = ("dd-MM-yyyy"))
    StudentLoanDTO studentLoanToStudentLoanDto(StudentLoan studentLoan);
}
