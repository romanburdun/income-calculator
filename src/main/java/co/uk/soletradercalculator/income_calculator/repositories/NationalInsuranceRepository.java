package co.uk.soletradercalculator.income_calculator.repositories;

import co.uk.soletradercalculator.income_calculator.domain.NationalInsurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface NationalInsuranceRepository extends JpaRepository<NationalInsurance, Long> {
    Boolean existsByTaxYearStarts(LocalDate date);
}
