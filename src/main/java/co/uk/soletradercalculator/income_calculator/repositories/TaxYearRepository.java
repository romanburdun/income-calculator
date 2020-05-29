package co.uk.soletradercalculator.income_calculator.repositories;

import co.uk.soletradercalculator.income_calculator.domain.TaxYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaxYearRepository extends JpaRepository<TaxYear, Long> {
   TaxYear findByTaxYearStarts(LocalDate date);
   Boolean existsByTaxYearStarts(LocalDate date);
   List<TaxYear> findAllByOrderByTaxYearStartsDesc();
}
