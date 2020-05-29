package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.api.v1.model.StudentLoanDTO;
import co.uk.soletradercalculator.income_calculator.domain.StudentLoan;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentLoanMapperTest {

    private final StudentLoanMapper studentLoanMapper = StudentLoanMapper.INSTANCE;

    @Test
    void studentLoanDtoToStudentLoan() {
        StudentLoanDTO studentLoanDTO = new StudentLoanDTO();
        studentLoanDTO.setTaxYearStarts("06-04-2019");
        studentLoanDTO.setPlanOneRate(9);
        studentLoanDTO.setPlanTwoRate(9);
        studentLoanDTO.setPlanOneAnnualThreshold(18935);
        studentLoanDTO.setPlanTwoAnnualThreshold(25725);

        StudentLoan studentLoan = studentLoanMapper.studentLoanDtoToStudentLoan(studentLoanDTO);

        assertNotNull(studentLoan);

        assertEquals(2019, studentLoan.getTaxYearStarts().getYear());
        assertEquals(18935, studentLoan.getPlanOneAnnualThreshold());
        assertEquals(9, studentLoan.getPlanTwoRate());
    }

    @Test
    void studentLoanToStudentLoanDto() {

        StudentLoan studentLoan = new StudentLoan();
        studentLoan.setTaxYearStarts(LocalDate.of(2019,4,6));
        studentLoan.setPlanOneRate(9);
        studentLoan.setPlanTwoRate(9);
        studentLoan.setPlanOneAnnualThreshold(18935);
        studentLoan.setPlanTwoAnnualThreshold(25725);

        StudentLoanDTO studentLoanDTO = studentLoanMapper.studentLoanToStudentLoanDto(studentLoan);

        assertNotNull(studentLoanDTO);

        assertEquals("06-04-2019", studentLoanDTO.getTaxYearStarts());
        assertEquals(18935, studentLoanDTO.getPlanOneAnnualThreshold());
        assertEquals(9, studentLoanDTO.getPlanTwoRate());
    }
}
