package co.uk.soletradercalculator.income_calculator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Setter
@Getter
public class NationalInsurance extends BaseEntity {
    @Column(unique = true)
    @NotNull
    private LocalDate taxYearStarts;
    private double classTwo;
    private double classTwoThreshold;
    private double lowerProfitsLimit;
    private double lowerProfitsLimitRate;
    private double upperProfitsLimit;
    private double upperProfitsLimitRate;
}
