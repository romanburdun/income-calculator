package co.uk.soletradercalculator.income_calculator.repositories;

import co.uk.soletradercalculator.income_calculator.domain.StudentLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface StudentLoanRepository extends JpaRepository<StudentLoan, Long> {
    Boolean existsByTaxYearStarts(LocalDate date);
}
