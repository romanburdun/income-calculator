package co.uk.soletradercalculator.income_calculator.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Setter
@Getter

public class Tax extends BaseEntity {
    @Column(unique = true)
    @NotNull
    private LocalDate taxYearStarts;
    private double personalAllowance;
    private double basicThreshold;
    private double basicRate;
    private double higherThreshold;
    private double higherRate;
    private double additionalRate;
}
