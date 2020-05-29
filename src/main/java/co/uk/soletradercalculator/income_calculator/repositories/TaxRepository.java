package co.uk.soletradercalculator.income_calculator.repositories;

import co.uk.soletradercalculator.income_calculator.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaxRepository extends JpaRepository<Tax, Long> {
    Tax findByTaxYearStarts(LocalDate date);
    Boolean existsByTaxYearStarts(LocalDate date);
}
