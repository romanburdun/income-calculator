package co.uk.soletradercalculator.income_calculator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Setter
@Getter
public class TaxYear extends BaseEntity {
    @Column(unique = true)
    private LocalDate taxYearStarts;
    @OneToOne
    private Tax tax;
    @OneToOne
    private NationalInsurance nationalInsurance;
    @OneToOne
    private StudentLoan studentLoan;

}
