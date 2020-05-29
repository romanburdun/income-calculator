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
public class StudentLoan extends BaseEntity {
    @Column(unique = true)
    @NotNull
    private LocalDate taxYearStarts;
    private double planOneAnnualThreshold;
    private double planOneRate;
    private double planTwoAnnualThreshold;
    private double planTwoRate;
}
